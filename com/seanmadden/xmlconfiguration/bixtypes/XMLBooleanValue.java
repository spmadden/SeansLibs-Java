/*
 * XMLBooleanValue.java
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
 * [Insert class description here]
 * 
 * @author Sean P Madden
 */
public class XMLBooleanValue extends XMLDataValue<Boolean> {

	private Boolean value = null;

	/**
	 * A class to process the XML data structure of a boolean varbl.
	 * 
	 * @author Sean P Madden
	 */
	private class XMLBooleanProcessor extends XMLProcessor {

		private XMLBooleanValue boolVal = null;

		/**
		 * Makes me a boolean parser with a reference to the parent value
		 * 
		 * @param value
		 */
		public XMLBooleanProcessor(XMLBooleanValue value) {
			boolVal = value;
		}

		/**
		 * Returns if the XML data type is complete
		 * 
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#isXMLComplete(java.lang.String)
		 * @param position
		 * @return
		 */
		protected boolean isXMLComplete(String position) {
			return position.equals("Boolean]");
		}

		/**
		 * Processes the value.
		 * 
		 * @see com.seanmadden.xmlconfiguration.xmlprocessing.XMLProcessor#processValue(java.lang.String)
		 * @param value
		 */
		protected void processValue(String value) {
			boolVal.setValue(Boolean.valueOf(value));
		}

	}

	/**
	 * Returns the value of this object
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getValue()
	 * @return
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * Returns the XML representation of this boolean type
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getXML()
	 * @return
	 */
	public String getXML() {
		StringBuilder str = new StringBuilder();
		str.append("<Boolean>\r\n");
		str.append("\t<Name>" + getName() + "</Name>\r\n");
		str.append("\t<Value>" + getValue() + "</Value>\r\n");
		str.append("\t<Description>" + getDescription() + "</Description>\r\n");
		str.append("</Boolean>\r\n");
		return str.toString();
	}

	/**
	 * [Place method description here]
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#processXML(java.lang.String)
	 * @param xml
	 * @return
	 */
	public XMLBooleanValue processXML(String xml) {
		XMLBooleanValue val = new XMLBooleanValue();
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			XMLProcessor parser = new XMLBooleanProcessor(val);
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
	 * Sets the internal value to value
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#setValue(java.lang.Object)
	 * @param value
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

	/**
	 * This method is designed to verify if this object will accept a data type
	 * from the XML parser
	 * 
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#acceptsDataType(java.lang.String)
	 * @param type
	 * @return
	 */
	public String acceptedDataType() {
		return "Boolean";
	}

	/**
	 * Returns the class of this processor
	 *
	 * @see com.seanmadden.xmlconfiguration.XMLDataValue#getProcessType()
	 * @return
	 */
	public Class<?> getProcessType() {
		return Boolean.class;
	}

}
