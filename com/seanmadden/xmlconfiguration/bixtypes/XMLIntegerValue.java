/*
 * XMLIntegerValue.java
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
 * This class is an interpreter for an integer xml structure.
 * 
 * @author Sean P Madden
 */
public class XMLIntegerValue extends XMLDataValue<Integer> {

	private int value = 0;

	/**
	 * Processes XML for an integer
	 * 
	 * @author Sean P Madden
	 */
	private class XMLIntegerProcessor extends XMLProcessor {
		private XMLIntegerValue intVal = null;

		/**
		 * Maintains a reference to the XMLIntegerValue superobject
		 * 
		 * @param value
		 */
		public XMLIntegerProcessor(XMLIntegerValue value) {
			intVal = value;
		}

		/**
		 * Returns true if the end of the XML directive
		 * 
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#isXMLComplete(java.lang.String)
		 * @param position
		 * @return
		 */
		protected boolean isXMLComplete(String position) {
			if (position.endsWith("Integer]")) {
				return true;
			}
			return false;
		}

		/**
		 * Process the string representation of an integer
		 * 
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#processValue(java.lang.String)
		 * @param value
		 */
		protected void processValue(String value) {
			intVal.setValue(Integer.valueOf(value));
		}

	}

	/**
	 * Returns the value for this object
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getValue()
	 * @return
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Returns a string (in XML representation) of this object.
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getXML()
	 * @return
	 */
	public String getXML() {
		StringBuilder str = new StringBuilder();
		str.append("<Integer>\r\n");
		str.append("\t<Name>" + getName() + "</Name>\r\n");
		str.append("\t<Value>" + getValue() + "</Value>\r\n");
		str.append("\t<Description>" + getDescription() + "</Description>\r\n");
		str.append("</Integer>\r\n");
		return str.toString();
	}

	public XMLIntegerValue processXML(String xml) {
		XMLIntegerValue newVal = new XMLIntegerValue();
		
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			XMLIntegerProcessor parser = new XMLIntegerProcessor(newVal);
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
		return newVal;
	}

	/**
	 * Sets the value for this object
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#setValue(java.lang.Object)
	 * @param value
	 */
	public void setValue(Integer value) {
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
		return "Integer";
	}

	/**
	 * [Place method description here]
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getProcessType()
	 * @return
	 */
	public Class<?> getProcessType() {
		return Integer.class;
	}

}
