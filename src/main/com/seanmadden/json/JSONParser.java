/*
 * JSONParser.java
 * 
 * Copyright (C) 2011 Sean P Madden (sean.madden)
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * If you would like to license this code under the GNU LGPL, please see
 * http://www.seanmadden.net/licensing for details.
 */
package com.seanmadden.json;

import java.util.Vector;

/**
 * Implementation and helper utilities for a JSON parser.
 * 
 * @author sean.madden
 */
public class JSONParser {

	/**
	 * The Rex to determine numbers
	 */
	private static final String NUMBER_REGEX = "[-]?[\\d]+[\\.]?([eE][+-]?[\\d]+)?";
	
	/**
	 * Make me a JSONParser
	 * 
	 * @param json
	 *            the string to parse.
	 */
	public JSONParser(String json) {

	}

	/**
	 * Is 'text' representative of a boolean value, either 'true' or 'false'.
	 * This is not case sensitive.
	 * 
	 * @param text
	 *            The text to evaluate
	 * @return true if boolean.
	 */
	public static boolean isBoolean(String text) {
		if (text == null) { return false; }
		String txt = text.toLowerCase().trim();
		return "true".equals(txt) || "false".equals(txt);
	}

	/**
	 * Is 'text' a valid 'json' number?
	 *
	 * @param text The string to check
	 * @return True if a valid number.
	 */
	public static boolean isNumber(String text) {
		if (text == null) { return false; }
		return text.toLowerCase().trim().matches(NUMBER_REGEX);
	}
	
	/**
	 * Parses a number
	 *
	 * @param text text
	 * @return A number!
	 */
	public static double toNumber(String text){
		return Double.valueOf(text);
	}
	
	/**
	 * Consumes an entire "String" from the tokenizer.
	 *
	 * @param token the tokenizer
	 * @return An entire string.
	 */
	public static String consumeString(JSONTokenizer token){
		if(!token.hasNext()){
			return "";
		}
		if(!"\"".equals(token.nextToken())){
			token.backToken();
			return "";
		}
		String curToken = "";
		StringBuilder buf = new StringBuilder();
		while(token.hasNext()){
			curToken = token.nextToken();
			 if(!"\"".equals(curToken)){
				 buf.append(curToken);
			 }else{
				 return buf.toString();
			 }
		}
		return "";
	}
	
	/**
	 * Consumes and returns a json Object.
	 *
	 * @param token the tokenizer
	 * @return a JSONObject.
	 */
	public static JSONObject consumeObject(JSONTokenizer token){
		if (token.hasNext()) {
			String item = token.nextToken();
			if ("{".equals(item)) {
				return consumeObject(token);
			} else if ("[".equals(item)) {
				return consumeArray(token);
			}
		}
		return null;
	}
	
	/**
	 * Parses out a JSON Object.
	 *
	 * @param json String to parse.
	 * @return an object!
	 */
	public JSONObject parse(String json) {
		JSONTokenizer tok = new JSONTokenizer(json);
		JSONObject in = new JSONObject(JSONObjectType.OBJECT);
		String name = null;
		
		return in;
	}
	
	public static JSONObject consumeArray(JSONTokenizer tok) {
		JSONObject obj = new JSONObject(JSONObjectType.ARRAY);
		String token = null;
		Vector<String> list = new Vector<String>();
		while (!(token = tok.nextToken()).equals("]")) {
			if (!token.equals(",")) {
				list.add(token);
			}
		}
//		obj.array = list;
		return obj;
	}
}
