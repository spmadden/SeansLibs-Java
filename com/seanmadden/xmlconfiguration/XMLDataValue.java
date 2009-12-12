/*
* XMLDataValue.java
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

import com.seanmadden.xmlconfiguration.xmlprocessing.XMLFormatException;

public abstract class XMLDataValue<T extends Object> {
	private String name;
	private String description;
	
	public XMLDataValue(){
		
	}
	
	public XMLDataValue(String name){
		setName(name);
	}
	
	public XMLDataValue(String name, String desc){
		setName(name);
		setDescription(desc);
	}
	
	public abstract T getValue();
	public abstract void setValue(T value);
	public abstract String getXML();
	public abstract XMLDataValue<T> processXML(String xml) throws XMLFormatException;
	public abstract String acceptedDataType();
	public abstract Class<?> getProcessType();
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String desc){
		this.description = desc;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
}
