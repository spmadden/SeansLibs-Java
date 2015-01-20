/*
 * JavaRetardedTests.java
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
package com.seanmadden.json.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

/**
 * @author sean.madden
 * 
 */
public class JavaRetardedTests {

	/**
	 * Make me a JavaRetardedTests
	 */
	public JavaRetardedTests() {
	}

	/**
	 * Verifies that java is still retarded.
	 */
	@Test
	public void testJava1() {
		assertTrue(010 < 9);
	}

	/**
	 * Checks against Java's Double implementation.
	 * 
	 * @throws IOException for when shit goes down.
	 */
	@Test
	public void javaDoubleTest() throws IOException {
		String ff = File.separator;
		File f = new File("com" + ff + "seanmadden" + ff + "json" + ff
				+ "tests" + ff + "exampleJsons.txt");
		BufferedReader read = new BufferedReader(new FileReader(f));
		String line = "";
		while ((line = read.readLine()) != null) {
			Double.valueOf(line);
		}
	}
}
