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

import com.seanmadden.xmlconfiguration.bixtypes.XMLBooleanValue;
import com.seanmadden.xmlconfiguration.bixtypes.XMLIntegerValue;
import com.seanmadden.xmlconfiguration.bixtypes.XMLStringValue;
import com.seanmadden.xmlconfiguration.xmlprocessing.XMLFormatException;
import com.seanmadden.xmlconfiguration.xmlprocessing.XMLConfigurationParser;

/**
 * This class represents the xml configuration manager. It acts as a central
 * access point for all xml configuration read/write operations.
 * 
 * @author Sean P Madden
 */
public class XMLConfiguration {

	/**
	 * Basic name for the configuration. This is used as the main document XML
	 * Tag. IE, All values are elements of this tag.
	 */
	private String name = "XMLConfiguration";

	Hashtable<String, XMLDataValue<Object>> dataTable = new Hashtable<String, XMLDataValue<Object>>();

	/**
	 * Hashtable for change callbacks.
	 */
	Hashtable<String, XMLEditCallback> callbacks = new Hashtable<String, XMLEditCallback>();

	Vector<XMLDataValue<?>> valueTypes = new Vector<XMLDataValue<?>>();

	/**
	 * Default constructor.
	 * 
	 */
	public XMLConfiguration() {
		this.name = "XMLConfiguration";
		resetConfiguration();
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
		resetConfiguration();
		this.name = name;
	}

	/**
	 * Resets and clears out ALL values in this data object
	 * 
	 */
	public void resetConfiguration() {
		dataTable.clear();
		callbacks.clear();
		valueTypes.clear();

		valueTypes.add(new XMLIntegerValue());
		valueTypes.add(new XMLBooleanValue());
		valueTypes.add(new XMLStringValue());
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

		StringBuffer strings = new StringBuffer();
		for (Map.Entry<String, XMLDataValue<Object>> val : dataTable.entrySet()) {
			strings.append(val.getValue().getXML());
		}
		Scanner scan = new Scanner(strings.toString());
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			str.append("\t" + line + "\r\n");
		}

		str.append("</" + this.name + ">\r\n");

		return str.toString();

	}

	public void addValueType(XMLDataValue<Object> newValType) {
		valueTypes.add(newValType);
	}

	/**
	 * References a value to a processor directive
	 * 
	 * @param value
	 * @return null if not found
	 */
	public XMLDataValue<Object> findValueType(Object value) {
		for (XMLDataValue<?> val : valueTypes) {
			if (val.getProcessType().toString().equals(
					value.getClass().toString())) {
				return (XMLDataValue<Object>) val;
			}
		}
		return null;
	}

	public XMLDataValue<Object> findParser(String value) {
		for (XMLDataValue<?> val : valueTypes) {
			if (val.acceptedDataType().equals(value)) {
				return (XMLDataValue<Object>) val;
			}
		}
		return null;
	}

	/**
	 * Adds a String configuration directive
	 * 
	 * @param name
	 *            The name of the directive to be referenced by
	 * @param value
	 *            The value of the directive
	 * @throws XMLValueTypeNotFoundException
	 */
	public void addValue(String name, Object value)
			throws XMLValueTypeNotFoundException {
		addValue(name, value, null);
	}

	/**
	 * Updates a value in the current system.
	 * 
	 * @param name
	 *            The key to retrieve
	 * @param value
	 *            - The value to set it to.
	 * @throws XMLValueTypeNotFoundException
	 */
	public void updateValue(String name, Object value)
			throws XMLValueTypeNotFoundException {
		XMLDataValue<Object> val = dataTable.get(name);
		if (val == null) {
			addValue(name, value);
		} else {
			val.setValue(value);
		}

		XMLEditCallback cb = callbacks.get(name);
		if (cb != null) {
			cb.update(this, name);
		}
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
	 * @throws XMLValueTypeNotFoundException
	 */
	public void addValue(String name, Object value, String desc)
			throws XMLValueTypeNotFoundException {
		XMLDataValue<Object> processor = findValueType(value);
		if (processor == null) {
			throw new XMLValueTypeNotFoundException();
		}
		XMLDataValue<Object> newObj = null;
		try {
			newObj = processor.getClass().newInstance();
			newObj.setDescription(desc);
			newObj.setName(name);
			newObj.setValue(value);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		dataTable.put(name, newObj);
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
	 * @throws IOException
	 */
	public boolean parseXMLFile(String filename) throws IOException {
		FileReader reader = new FileReader(filename);
		StringBuffer file = new StringBuffer();
		while (reader.ready()) {
			file.append((char) reader.read());
		}
		reader.close();

		XMLConfigurationParser parse = new XMLConfigurationParser(file.toString(), this);
		try {
			return parse.parse();
		} catch (XMLFormatException e) {
			e.printStackTrace();
		} catch (XMLValueTypeNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Spawn off a GUI to edit me.
	 * 
	 */
	public void editUsingGui() {
		new XMLEditGui(this).displayGUI();
	}

	/**
	 * Registers a callback for the key specified by name.
	 * 
	 * @param name
	 * @param callbackPointer
	 */
	public void registerCallback(String name, XMLEditCallback callbackPointer) {
		callbacks.put(name, callbackPointer);
	}

	/**
	 * Standard main method to excersize this object.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		XMLConfiguration xml = new XMLConfiguration();

		try {
			xml.addValue("TestInt1", 15);
			xml.addValue("TestInt2", 20, "This is a test Integer");
			xml.addValue("TestString1", "Test String One");
			xml.addValue("TestString2", "Test String Two",
					"This is a Test string, index two");
			xml.addValue("TestBool1", true);
			xml.addValue("TestBool2", false,
					"This is a test boolean value.  NOT.");

		} catch (XMLValueTypeNotFoundException e1) {
			e1.printStackTrace();
		}
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
		try {
			xml2.parseXMLFile("configuration.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xml2.generateXML());

	}
}
