/*
 * XMLConfiguration.java
 * 
 *    Copyright (C) 2009 Sean P Madden
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *    If you would like to license this code under the GNU LGPL, please see
 *    http://www.seanmadden.net/licensing for details.
 *
 */
package com.seanmadden.xmlconfiguration;

import java.io.*;
import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLConfiguration extends DefaultHandler {
	private String name = "XMLConfiguration";
	Hashtable<String, String> stringsTable = new Hashtable<String, String>();
	Hashtable<String, Integer> intsTable = new Hashtable<String, Integer>();
	Hashtable<String, Boolean> boolsTable = new Hashtable<String, Boolean>();

	Stack<String> position = new Stack<String>();
	String data = "";
	String dataName = "";

	public XMLConfiguration() {
		this.name = "XMLConfiguration";
	}

	public XMLConfiguration(String name) {
		this.name = name;
	}

	public void resetConfiguration() {
		stringsTable.clear();
		intsTable.clear();
		boolsTable.clear();
	}

	public String generateXML() {

		StringBuilder str = new StringBuilder();
		str.append("<?xml version=\"1.0\"?>\r\n");
		str.append("<" + this.name + ">\r\n");

		String strings = generateStringsXML();
		strings += generateIntsXML();
		strings += generateBoolsXML();
		Scanner scan = new Scanner(strings);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			str.append("\t" + line + "\r\n");
		}

		str.append("</" + this.name + ">\r\n");

		return str.toString();
	}

	private String generateStringsXML() {
		StringBuilder str = new StringBuilder();
		str.append("<Strings>\r\n");
		for (Map.Entry<String, String> ent : stringsTable.entrySet()) {
			str.append("\t<Value name=\"" + ent.getKey() + "\">"
					+ ent.getValue() + "</Value>\r\n");
		}
		str.append("</Strings>\r\n");
		return str.toString();
	}

	private String generateIntsXML() {
		StringBuilder str = new StringBuilder();
		str.append("<Integers>\r\n");
		for (Map.Entry<String, Integer> ent : intsTable.entrySet()) {
			str.append("\t<Value name=\"" + ent.getKey() + "\">"
					+ ent.getValue() + "</Value>\r\n");
		}
		str.append("</Integers>\r\n");
		return str.toString();
	}

	private String generateBoolsXML() {
		StringBuilder str = new StringBuilder();
		str.append("<Booleans>\r\n");
		for (Map.Entry<String, Boolean> ent : boolsTable.entrySet()) {
			str.append("\t<Value name=\"" + ent.getKey() + "\">"
					+ ent.getValue() + "</Value>\r\n");
		}
		str.append("</Booleans>\r\n");
		return str.toString();
	}

	public void addValue(String name, String value) {
		stringsTable.put(name, value);
	}
	
	public String getStringValue(String name){
		return stringsTable.get(name);
	}

	public void addValue(String name, Integer value) {
		intsTable.put(name, value);
	}

	public Integer getIntegerValue(String name){
		return intsTable.get(name);
	}
	
	public void addValue(String name, Boolean value) {
		boolsTable.put(name, value);
	}
	
	public Boolean getBooleanValue(String name){
		return boolsTable.get(name);
	}

	public boolean writeToXMLFile(String filename){
		FileWriter file;
		try {
			file = new FileWriter(new File(filename));
			file.write(generateXML());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean parseXMLFile(String filename) {
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(this);
			xr.setErrorHandler(this);
			FileReader r = new FileReader(filename);
			xr.parse(new InputSource(r));
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void startDocument() {
		position = new Stack<String>();
	}

	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		position.push(name);
		System.out.println(position);
		for (int i = 0; i < atts.getLength(); i++) {
			if (atts.getQName(i).equals("name")) {
				dataName = atts.getValue(i);
			}

		}
	}

	public void endElement(String uri, String name, String qName) {
		position.pop();

		if (dataName.equals("")) {
			return;
		}
		if (position.toString().endsWith("Integers]")) {
			intsTable.put(dataName, Integer.parseInt(data.trim()));
		} else if (position.toString().endsWith("Strings]")) {
			stringsTable.put(dataName, data.trim());
		} else if (position.toString().endsWith("Booleans]")) {
			boolsTable.put(dataName, Boolean.parseBoolean(data.trim()));
		}

		dataName = "";
		data = "";
	}

	public void characters(char ch[], int start, int length) {
		StringBuilder str = new StringBuilder();
		str.append(data);
		str.append(ch, start, length);
		data = str.toString();
	}

	public static void main(String args[]) {
		XMLConfiguration xml = new XMLConfiguration();

		xml.addValue("TestInt1", 15);
		xml.addValue("TestInt2", 20);
		xml.addValue("TestString1", "Test String One");
		xml.addValue("TestString2", "Test String Two");
		xml.addValue("TestBool1", true);
		xml.addValue("TestBool2", false);

		System.out.println(xml.generateXML());
		FileWriter file;
		try {
			file = new FileWriter(new File("configuration.xml"));
			file.write(xml.generateXML());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		XMLConfiguration xml2 = new XMLConfiguration();
		xml2.parseXMLFile("configuration.xml");
		System.out.println(xml2.generateXML());

	}
}
