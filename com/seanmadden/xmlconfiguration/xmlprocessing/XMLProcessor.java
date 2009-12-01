/*
 * XMLProcessor.java
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
package com.seanmadden.xmlconfiguration.xmlprocessing;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This is an abstract implementation of an XML processor.
 * 
 * @author Sean P Madden
 */
public abstract class XMLProcessor extends DefaultHandler {

	String data = "";
	String dataName = "";
	String dataValue = "";
	String dataDesc = "";

	/**
	 * This stack is used to maintain the state of the xml tree stack when
	 * processing an XML file. Subsequent new tags are pushed onto the stack
	 * while processed and as tags are closed, they're popped off the stack. We
	 * use this to verify and validate our position as we process the file.
	 */
	Stack<String> position;

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
		System.out.println(position);
		if (lastPos.equals("Name")) {
			dataName = data.trim();
		} else if (lastPos.equals("Value")) {
			dataValue = data.trim();
		} else if (lastPos.equals("Description")) {
			dataDesc = data.trim();
		}
		data = "";

		if (dataName.equals("") || dataValue.equals("") || dataDesc.equals("")) {
			return;
		}
		
		if(isXMLComplete(position.toString())){
			processValue(dataValue);
			
			data = "";
			dataName = "";
			dataValue = "";
			dataDesc = "";
		}

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
		System.out.println("chars: ");
		System.out.println(ch);
		StringBuilder str = new StringBuilder();
		str.append(data);
		str.append(ch, start, length);
		data = str.toString();
	}

	/**
	 * This is implemented by the delegate object to validate/process the string
	 * value.
	 * 
	 * @param value the string we're processing.
	 */
	protected abstract void processValue(String value);

	/**
	 * This function determines whether or not the position is the end of the
	 * processing for the directive.
	 * 
	 * @param position
	 * @return boolean whether or not to stop.
	 */
	protected abstract boolean isXMLComplete(String position);

}
