/*
 * Base64.java
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
package com.seanmadden.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * [Insert class description here]
 * 
 * @author Sean P Madden
 */
public class Base64 {

	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	public static String encode(String toEncode) {
		StringBuffer buf = new StringBuffer();
		int numSolidBlocks = toEncode.length() / 3;
		for (int i = 0; i < numSolidBlocks; i++) {
			char first = toEncode.charAt(i * 3);
			char second = toEncode.charAt(i * 3 + 1);
			char third = toEncode.charAt(i * 3 + 2);
			int firstPos = (first & 0xFC) >> 2;
			int secondPos = ((first & 0x3) << 4) | ((second & 0xF0) >> 4);
			int thirdPos = ((second & 0xF) << 2) | ((third & 0xC0) >> 6);
			buf.append(alphabet.charAt(firstPos));
			buf.append(alphabet.charAt(secondPos));
			buf.append(alphabet.charAt(thirdPos));
			buf.append(alphabet.charAt(third & 0x3F));
		}
		int numLeft = toEncode.length() - (numSolidBlocks * 3);
		if (numLeft == 2) {
			char first = toEncode.charAt(numSolidBlocks * 3);
			char second = toEncode.charAt(numSolidBlocks * 3 + 1);
			int firstPos = (first & 0xFC) >> 2;
			int secondPos = ((first & 0x3) << 4) | ((second & 0xF0) >> 4);
			int thirdPos = ((second & 0xF) << 2);
			buf.append(alphabet.charAt(firstPos));
			buf.append(alphabet.charAt(secondPos));
			buf.append(alphabet.charAt(thirdPos));
			buf.append("=");
		} else if (numLeft == 1) {
			char first = toEncode.charAt(numSolidBlocks * 3);
			int firstPos = (first & 0xFC) >> 2;
			int secondPos = ((first & 0x3) << 4);
			buf.append(alphabet.charAt(firstPos));
			buf.append(alphabet.charAt(secondPos));
			buf.append("==");
		}

		return buf.toString();
	}

	public static String decode(String toDecode) {
		StringBuffer buf = new StringBuffer();
		int numSolidBlocks = toDecode.length() / 4;
		for (int i = 0; i < numSolidBlocks; i++) {
			char first = toDecode.charAt(i * 4);
			char second = toDecode.charAt(i * 4 + 1);
			char third = toDecode.charAt(i * 4 + 2);
			char fourth = toDecode.charAt(i * 4 + 3);
			char newFirst = (char) ((alphabet.indexOf(first) << 2) | ((alphabet
					.indexOf(second) & 0x30) >> 4));
			buf.append(newFirst);
			if (third != '=') {
				char newSecond = (char) ((((alphabet.indexOf(second) & 0xF) << 4)) | ((alphabet
						.indexOf(third) & 0x3C) >> 2));
				buf.append(newSecond);
			}
			if (fourth != '=') {
				char newThird = (char) (((alphabet.indexOf(third) & 0x3) << 6) | alphabet
						.indexOf(fourth));
				buf.append(newThird);
			}
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		BufferedReader reader;
		String line = "";
		String test = "this is a test and it works well.";
		try {
			reader = new BufferedReader(new FileReader(args[1]));
			while ((line = reader.readLine()) != null) {
				test += line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(encode(test));
		System.out.println(decode(encode(test)));
	}
}
