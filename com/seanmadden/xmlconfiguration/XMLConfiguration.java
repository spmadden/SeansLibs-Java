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

/**
 * This class represents the xml configuration manager. It acts as a central
 * access point for all xml configuration read/write operations.
 * 
 * @author Sean P Madden
 */
public class XMLConfiguration extends DefaultHandler {
	/**
	 * Basic name for the configuration. This is used as the main document XML
	 * Tag. IE, All values are elements of this tag.
	 */
	private String name = "XMLConfiguration";
	/**
	 * A Table of all of our string configuration values, indexed by name.
	 */
	Hashtable<String, XMLDataValue<String>> stringsTable = new Hashtable<String, XMLDataValue<String>>();
	/**
	 * A Table of all of our Integer configuration values, again, indexed by
	 * name
	 */
	Hashtable<String, XMLDataValue<Integer>> intsTable = new Hashtable<String, XMLDataValue<Integer>>();
	/**
	 * A table of all of our Boolean values, shockingly indexed by name.
	 */
	Hashtable<String, XMLDataValue<Boolean>> boolsTable = new Hashtable<String, XMLDataValue<Boolean>>();

	/**
	 * This stack is used to maintain the state of the xml tree stack when
	 * processing an XML file. Subsequent new tags are pushed onto the stack
	 * while processed and as tags are closed, they're popped off the stack. We
	 * use this to verify and validate our position as we process the file.
	 */
	Stack<String> position = new Stack<String>();

	/**
	 * The following are variables used exclusively when processing a new XML
	 * file into this object.
	 */
	String data = "";
	String dataName = "";
	String dataValue = "";
	String dataDesc = "";

	/**
	 * Default constructor.
	 * 
	 */
	public XMLConfiguration() {
		this.name = "XMLConfiguration";
	}

	/**
	 * This constructor accepts a name parameter and uses the name for the
	 * global name of this XML Configuration. See the comments under the Name
	 * varbl above.
	 * 
	 * @param name
	 *            - String representing this XML Configuration
	 */
	public XMLConfiguration(String name) {
		this.name = name;
	}

	/**
	 * Resets and clears out ALL values in this data object
	 * 
	 */
	public void resetConfiguration() {
		stringsTable.clear();
		intsTable.clear();
		boolsTable.clear();
	}

	/**
	 * Iterates over all of the values in this data type and generates an XML
	 * representation of it. It's broken into three parts, The name, the Value
	 * and the Description of the directive
	 * 
	 * @return String representation of the current state of this object, in XML
	 *         format.
	 */
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

	/**
	 * Submethod to generate the XML representation of all string directives
	 * 
	 * @return String representation of the Strings, in XML format
	 */
	private String generateStringsXML() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, XMLDataValue<String>> ent : stringsTable
				.entrySet()) {
			str.append("<String>\r\n");
			str.append("\t<Name>" + ent.getKey() + "</Name>\r\n");
			str
					.append("\t<Value>" + ent.getValue().getValue()
							+ "</Value>\r\n");
			str.append("\t<Description>" + ent.getValue().getDescription()
					+ "</Description>\r\n");
			str.append("</String>\r\n");
		}
		return str.toString();
	}

	/**
	 * Generates an XML representation of the Integer directives contained
	 * within
	 * 
	 * @return String representation of the Integer directives, in XML Format
	 */
	private String generateIntsXML() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, XMLDataValue<Integer>> ent : intsTable
				.entrySet()) {
			str.append("<Integer>\r\n");
			str.append("\t<Name>" + ent.getKey() + "</Name>\r\n");
			str
					.append("\t<Value>" + ent.getValue().getValue()
							+ "</Value>\r\n");
			str.append("\t<Description>" + ent.getValue().getDescription()
					+ "</Description>\r\n");
			str.append("</Integer>\r\n");
		}
		return str.toString();
	}

	/**
	 * Generates the string representation of all of the Boolean values
	 * contained within this object.... In XML Format
	 * 
	 * @return String representatino of the Booleans, In XML Format.
	 */
	private String generateBoolsXML() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<String, XMLDataValue<Boolean>> ent : boolsTable
				.entrySet()) {
			str.append("<Boolean>\r\n");
			str.append("\t<Name>" + ent.getKey() + "</Name>\r\n");
			str
					.append("\t<Value>" + ent.getValue().getValue()
							+ "</Value>\r\n");
			str.append("\t<Description>" + ent.getValue().getDescription()
					+ "</Description>\r\n");
			str.append("</Boolean>\r\n");
		}
		return str.toString();
	}

	/**
	 * Adds a String configuration directive
	 * 
	 * @param name
	 *            The name of the directive to be referenced by
	 * @param value
	 *            The value of the directive
	 */
	public void addValue(String name, String value) {
		addValue(name, value, null);
	}

	/**
	 * Adds a String configuration directive
	 * 
	 * @param name
	 *            The name of the directive to be referenced by
	 * @param value
	 *            The value of the directive
	 * @param desc
	 *            The description of the directive
	 */
	public void addValue(String name, String value, String desc) {
		stringsTable.put(name, new XMLDataValue<String>(name, value, desc));
	}

	/**
	 * Retrieve and return A string directive by name
	 * 
	 * @param name
	 *            The name of the directive to return
	 * @return The string value of the directive, NULL if non-existant
	 */
	public String getStringValue(String name) {
		XMLDataValue<String> val = stringsTable.get(name);
		if (val == null)
			return null;
		return val.getValue();
	}

	/**
	 * Retrieves and returns the description of the string identified by Name
	 * 
	 * @param name
	 *            the name of the directive to retrieve
	 * @return the description of the directive
	 */
	public String getStringDesc(String name) {
		XMLDataValue<String> val = stringsTable.get(name);
		if (val == null)
			return null;
		return val.getDescription();
	}

	/**
	 * Adds a new Integer directive to this object
	 * 
	 * @param name
	 *            The name of the directive to be referenced by
	 * @param value
	 *            The value of the directive
	 */
	public void addValue(String name, Integer value) {
		addValue(name, value, null);
	}

	/**
	* Adds a new Integer directive to this object
	* 
	* @param name The name of the directive to be referenced by
	* @param value The value of the directive
	* @param desc The description of the directive
	*/
	public void addValue(String name, Integer value, String desc){
		intsTable.put(name, new XMLDataValue<Integer>(name, value, desc));
	}
	
	/**
	 * Retrieves and returns the value of the Integer represented by name
	 * 
	 * @param name the name of the directive to retrieve
	 * @return the value of the directive, null if non-existent
	 */
	public Integer getIntegerValue(String name) {
		XMLDataValue<Integer> val = intsTable.get(name);
		if (val == null)
			return null;
		return val.getValue();
	}

	/**
	 * Retrieves and returns description of the Integer directive represented by
	 * name
	 * 
	 * @param name
	 *            the name of the directive to retrieve
	 * @return the description of the integer, null if non-existent
	 */
	public String getIntegerDesc(String name) {
		XMLDataValue<Integer> val = intsTable.get(name);
		if (val == null)
			return null;
		return val.getDescription();
	}

	/**
	 * Adds a new Boolean directive to this object
	 * 
	 * @param name
	 *            The name the directive will be referenced by
	 * @param value
	 *            The value of the directive
	 */
	public void addValue(String name, Boolean value) {
		addValue(name, value, null);
	}
	
	/**
	* Adds a new Boolean directive to this object
	* 
	* @param name The name of the directive will be referenced by
	* @param value The value of the directive
	* @param desc The description of the directive.
	*/
	public void addValue(String name, Boolean value, String desc){
		boolsTable.put(name, new XMLDataValue<Boolean>(name, value, desc));
	}

	/**
	 * Retrieve and return the boolean value represented by a directive
	 * 
	 * @param name
	 *            The name of the directive to retrieve
	 * @return The Boolean value of the directive represented by Name, NULL if
	 *         non-existent
	 */
	public Boolean getBooleanValue(String name) {
		XMLDataValue<Boolean> val = boolsTable.get(name);
		if (val == null)
			return null;
		return val.getValue();
	}

	/**
	 * Retrieves and returns the description of the boolean directive
	 * represented by name
	 * 
	 * @param name
	 *            the name of the directive to retrieve.
	 * @return the description of the directive, null if non-existent.
	 */
	public String getBooleanDescription(String name) {
		XMLDataValue<Boolean> val = boolsTable.get(name);
		if (val == null)
			return null;
		return val.getDescription();
	}

	/**
	 * Sets the name of this XML Configuration object. See the comment above on
	 * the 'name' variable for explanation.
	 * 
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Generates the String representation of the current state of this object,
	 * then writes it to the file specified by 'filename'. If successful,
	 * returns true; false on any errors
	 * 
	 * @param filename
	 *            The file to open
	 * @return success - true if successful, false otherwise
	 */
	public boolean writeToXMLFile(String filename) {
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

	/**
	 * This method accepts a file and populates itself with the values contained
	 * within it. IT DOES NOT CLEAR OUT THE CURRENT STATE OF THIS OBJECT. It
	 * WILL append any data (overwriting if necessary) to this object
	 * 
	 * @param filename
	 *            The filename to open.
	 * @return success - true if successful, false otherwise
	 */
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

	/**
	 * This method is not to be called. It is used by the XML Parser.
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	public void startDocument() {
		position = new Stack<String>();
	}

	/**
	 * This method is not to be called. IT is used by the XML Parser.
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 * @param uri
	 * @param name
	 * @param qName
	 * @param atts
	 */
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		position.push(name);
	}

	/**
	 * This method is not to be called. It is used by the XML Parser
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 * @param uri
	 * @param name
	 * @param qName
	 */
	public void endElement(String uri, String name, String qName) {
		String lastPos = position.pop();

		if (lastPos.equals("Name")) {
			dataName = data.trim();
		} else if (lastPos.equals("Value")) {
			dataValue = data.trim();
		} else if (lastPos.equals("Description")) {
			dataDesc = data.trim();
			;
		}
		data = "";

		if (dataName.equals("") || dataValue.equals("") || dataDesc.equals("")) {
			return;
		}

		if (position.toString().endsWith("Integer]")) {
			Integer value = Integer.parseInt(dataValue);
			XMLDataValue<Integer> theInt = new XMLDataValue<Integer>(dataName,
					value, dataDesc);
			intsTable.put(dataName, theInt);
		} else if (position.toString().endsWith("String]")) {
			XMLDataValue<String> theString = new XMLDataValue<String>(dataName,
					dataValue, dataDesc);
			stringsTable.put(dataName, theString);
		} else if (position.toString().endsWith("Boolean]")) {
			Boolean value = Boolean.parseBoolean(dataValue);
			XMLDataValue<Boolean> theBool = new XMLDataValue<Boolean>(dataName,
					value, dataDesc);
			boolsTable.put(dataName, theBool);
		}

		data = "";
		dataName = "";
		dataValue = "";
		dataDesc = "";
	}

	/**
	 * This method is not to be called. It is used by the XML Parser.
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 * @param ch
	 * @param start
	 * @param length
	 */
	public void characters(char ch[], int start, int length) {
		StringBuilder str = new StringBuilder();
		str.append(data);
		str.append(ch, start, length);
		data = str.toString();
	}

	/**
	 * Standard main method to excersize this object.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		XMLConfiguration xml = new XMLConfiguration();

		xml.addValue("TestInt1", 15);
		xml.addValue("TestInt2", 20, "This is a test Integer");
		xml.addValue("TestString1", "Test String One");
		xml.addValue("TestString2", "Test String Two", "This is a Test string, index two");
		xml.addValue("TestBool1", true);
		xml.addValue("TestBool2", false, "This is a test boolean value.  NOT.");

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
