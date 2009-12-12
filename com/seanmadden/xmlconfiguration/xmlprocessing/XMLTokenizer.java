/**
 * 
 */
package com.seanmadden.xmlconfiguration.xmlprocessing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author spm2732
 * 
 */
public class XMLTokenizer {

	private static final char[] SPECIAL_CHARS = new char[] { '<', '>', '=' };

	private int position = 0;
	private int lastPosition = 0;

	private char[] xml = null;

	public XMLTokenizer(String xml) {
		this.xml = xml.toCharArray();
	}

	public String nextToken() throws XMLFormatException {
		lastPosition = position;
		if (position == xml.length) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		while (position != xml.length) {
			if (xml[position] == '<') {
				if (buf.toString().trim().length() != 0) {
					return buf.toString().trim();
				}
				buf.append(xml[position++]);
				while (position != xml.length && xml[position] != '>') {
					if (xml[position] == '<') {
						throw new XMLFormatException(
								"Unexpected character '<'.");
					}
					buf.append(xml[position++]);
				}
				buf.append(xml[position++]);
//				if (position == xml.length) {
//					throw new XMLFormatException(
//							"Unexpected end of file reached.");
//				}
				return buf.toString().trim();
			}
			buf.append(xml[position++]);
		}
		if (buf.length() == 0) {
			return null;
		}
		return buf.toString().trim();
	}

	public boolean hasNext() throws XMLFormatException {
		if (nextToken() != null) {
			backToken();
			return true;
		}
		return false;
	}

	public void backToken() {
		position = lastPosition;
	}

	protected static boolean isSpecialChar(char chr) {
		for (int i = 0; i < SPECIAL_CHARS.length; ++i) {
			if (chr == SPECIAL_CHARS[i]) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isWhitespace(char chr) {
		return (new String("" + chr).trim().equals(""));
	}

	public static void main(String[] args) {
		try {
			FileReader reader = new FileReader("configuration.xml");
			StringBuffer xml = new StringBuffer();
			while (reader.ready()) {
				xml.append((char) reader.read());
			}
			reader.close();

			XMLTokenizer token = new XMLTokenizer(xml.toString());
			while (token.hasNext()) {
				System.out.println(token.nextToken());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLFormatException e) {
			e.printStackTrace();
		}
	}

}
