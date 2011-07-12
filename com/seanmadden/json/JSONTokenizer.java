/*
 * JSONTokenizer.java
 * 
 * Copyright (C) 2008 Sean P Madden
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
 * 
 */
package com.seanmadden.json;

/**
 * @author spm2732
 * 
 */
public class JSONTokenizer { 
	/**
	 * The list of special charaters to tokenize. 
	 */
	private static final char[] SPECIAL_CHARS = new char[] { '{', '}', ':',
		',', '[', ']', };

	/**
	 * The internal representation of the data.
	 */
	private char[] data;
	
	/**
	 * The curent position in data.
	 */
	private int position;
	
	/**
	 * Position of the previous special character.
	 */
	private int lastPosition;
	

	/**
	 * Constructs a JSON Tokenizer.
	 * @param json the String of JSON to tokenize.
	 */
	public JSONTokenizer(String json) {
		String cleaned = json.replace("\n", "");
		cleaned = cleaned.replace("\r", "");
		this.data = cleaned.toCharArray();
	}

	/**
	 * Retrieves and returns the next "token" in the json string.
	 *
	 * @return The next token.
	 */
	public String nextToken() {
		lastPosition = position;
		if (position == data.length) { return null; }
		StringBuffer buf = new StringBuffer();
		while (position != data.length) {
			if (isWhitespace(data[position])) {
				position++;
				continue;
			}
			if (isSpecialChar(data[position])) {
				if (buf.length() == 0) {
					buf.append(data[position++]);
				}
				return buf.toString().trim();
			} else if (data[position] == '"') {
				buf.append(data[position++]);
				while (data[position] != '"') {
					if (data[position] == '\\') {
						buf.append(data[position++]);
						buf.append(data[position++]);
					}
					buf.append(data[position++]);
				}
				buf.append(data[position++]);
			} else {
				buf.append(data[position++]);
			}
		}
		return buf.toString().trim();
	}

	/**
	 * Is 'chr' a special character?
	 * Special characters include '{', '}', '[', ']', ',', and ':'
	 *
	 * @param chr The character under test
	 * @return true if a special character.
	 */
	protected static boolean isSpecialChar(char chr) {
		for (int i = 0; i < SPECIAL_CHARS.length; ++i) {
			if (chr == SPECIAL_CHARS[i]) { return true; }
		}
		return false;
	}

	/**
	 * Is 'chr' a whitespace character?
	 * Whitespace is defined as .trim() returning ""
	 *
	 * @param chr The character under test
	 * @return true if whitespace
	 */
	protected static boolean isWhitespace(char chr) {
		return "".equals(new String("" + chr).trim());
	}

	/**
	 * Is there an additional token in the stream?
	 *
	 * @return true if additional token.
	 */
	public boolean hasNext() {
		int pos = position;
		if (nextToken() != null) {
			position = pos;
			return true;
		}
		return false;
	}

	/**
	 * Back up to the previous token.
	 *
	 */
	public void backToken() {
		position = lastPosition;
	}

}
