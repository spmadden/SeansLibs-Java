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

import com.seanmadden.xmlconfiguration.XMLDataValue;
import com.seanmadden.xmlconfiguration.xmlprocessing.XMLFormatException;
import com.seanmadden.xmlconfiguration.xmlprocessing.XMLTokenizer;

/**
 * [Insert class description here]
 * 
 * @author Sean P Madden
 */
public class XMLBooleanValue extends XMLDataValue<Boolean> {

	private Boolean value = null;

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
	 * @throws XMLFormatException 
	 */
	public XMLBooleanValue processXML(String xml) throws XMLFormatException {
		XMLTokenizer token = new XMLTokenizer(xml);
		XMLBooleanValue str = new XMLBooleanValue();
		while(token.hasNext()){
			String next = token.nextToken();
			if(next.toLowerCase().matches(".*<name>.*")){
				str.setName(token.nextToken());
			}else if(next.toLowerCase().matches(".*<description>.*")){
				str.setDescription(token.nextToken());
			}else if(next.toLowerCase().matches(".*<value>.*")){
				str.setValue(Boolean.valueOf(token.nextToken()));
			}
		}
		return str;
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
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("[XMLBooleanValue name=\"" + this.getName() + "\" value=\"" + this.getValue() +"\" description=\""+this.getDescription()+"\"]");
		return buf.toString();
	}

}
