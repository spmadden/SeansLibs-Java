/*
 * BasicTokenizerTests.java
 *
 *    Copyright (C) 2011 Sean P Madden (sean.madden)
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
package com.seanmadden.json.tests;


import static org.junit.Assert.*;

import org.junit.Test;

import com.seanmadden.json.JSONTokenizer;

/**
 * We'll go through some basic tests of the tokenizer here.
 * 
 * @author sean.madden
 *
 */
public class BasicTokenizerTests {

	/**
	 * The tokenizer!
	 */
	private JSONTokenizer tokenizer;
	
	/**
	 * Tests a blank object.
	 *
	 */
	@Test
	public void testBlankObject(){
		String json = "{}";
		tokenizer = new JSONTokenizer(json);
		
		assertTrue(tokenizer.hasNext());
		assertTrue("{".equals(tokenizer.nextToken()) );
		assertTrue(tokenizer.hasNext());
		assertTrue("}".equals(tokenizer.nextToken()));
		assertFalse(tokenizer.hasNext());
	}
	
	/** 
	 * Tests for an blank array.
	 */
	@Test
	public void testBlankArray(){
		String json = "[]";
		tokenizer = new JSONTokenizer(json);
		
		assertTrue(tokenizer.hasNext());
		assertTrue("[".equals(tokenizer.nextToken()) );
		assertTrue(tokenizer.hasNext());
		assertTrue("]".equals(tokenizer.nextToken()));
		assertFalse(tokenizer.hasNext());
	}
	
	/**
	 * Tests for a basic object with two string values.
	 */
	@Test
	public void testBasicObjectStrings(){
		String json = "{\"derp\":\"value\"}";
		tokenizer = new JSONTokenizer(json);
		
		assertTrue(tokenizer.hasNext());
		assertTrue("{".equals(tokenizer.nextToken()));
		assertTrue("\"derp\"".equals(tokenizer.nextToken()));
		assertTrue(":".equals(tokenizer.nextToken()));
		assertTrue("\"value\"".equals(tokenizer.nextToken()));
		assertTrue("}".equals(tokenizer.nextToken()));
		assertFalse(tokenizer.hasNext());
	}
}
