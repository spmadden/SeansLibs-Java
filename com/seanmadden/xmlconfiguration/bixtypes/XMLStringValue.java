/*
* XMLStringValue.java
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
package com.seanmadden.xmlconfiguration.bixtypes;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.seanmadden.xmlconfiguration.XMLDataValue;
import com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor;


/**
* This class both processes and generates XML for a String data type
*
* @author Sean P Madden
*/
public class XMLStringValue extends XMLDataValue<String> {
	
	/**
	* This class is the base XML Processor using the SAX framework.
	*
	* @author Sean P Madden
	*/
	private class XMLStringProcessor extends XMLProcessor{
		XMLStringValue strVal = null;
		
		/**
		 * Creates a StringXMLProcessor with a reference to it's parent object
		 *
		 * @param value
		 */
		public XMLStringProcessor(XMLStringValue value){
			strVal = value;
		}

		/**
		 * Processes the string value of the object.
		 *
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#processValue(java.lang.String)
		 * @param value
		 */
		protected void processValue(String value) {
			strVal.setValue(value);
		}

		/**
		 * Determines whether this is the end of the string XML directive
		 *
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#isXMLComplete(java.lang.String)
		 * @param position
		 * @return
		 */
		protected boolean isXMLComplete(String position) {
			if(position.endsWith("String]")){
				return true;
			}
			return false;
		}
		
		
	}
	
	/**
	 * Our happy string value
	 */
	private String value = "";
	
	/**
	 * Just pass it to the supermethod
	 *
	 * @param dataName
	 * @param dataDesc
	 */
	public XMLStringValue(String dataName, String dataDesc) {
		super(dataName, dataDesc);
	}

	public XMLStringValue() {
	}

	/**
	 * Returns the string value
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getValue()
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Creates an XML directive based upon a string
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getXML()
	 * @return
	 */
	public String getXML() {
		StringBuilder str = new StringBuilder();
		str.append("<String>\r\n");
		str.append("\t<Name>" + getName() + "</Name>\r\n");
		str
				.append("\t<Value>" + value
						+ "</Value>\r\n");
		str.append("\t<Description>" + getDescription()
				+ "</Description>\r\n");
		str.append("</String>\r\n");
		return str.toString();
	}

	/**
	 * Reads a base XML string directive
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#processXML(java.lang.String)
	 * @param xml
	 * @return
	 */
	public XMLStringValue processXML(String xml) {
		XMLStringValue val = new XMLStringValue();
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			XMLProcessor parser = new XMLStringProcessor(val);
			xr.setContentHandler(parser);
			xr.setErrorHandler(parser);
			xr.parse(new InputSource(xml));
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return val;
	}

	/**
	 * Sets the string value
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#setValue(java.lang.Object)
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
		
	}

	/**
	 * [Place method description here]
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#acceptsDataType(java.lang.String)
	 * @param type
	 * @return
	 */
	public String acceptedDataType() {
		return "String";
	}

	/**
	 * [Place method description here]
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getProcessType()
	 * @return
	 */
	public Class<?> getProcessType() {
		return String.class;
	}
	
}
