/**
 * 
 */
package com.seanmadden.json;

import java.io.*;

/**
 * @author spm2732
 * 
 */
public class JSONTokenizer {

	private char[] data = null;
	private int position = 0;
	private int lastPosition = 0;
	private static final char[] SPECIAL_CHARS = new char[] { '{', '}', ':',
			',', '[', ']' };

	/**
	 * 
	 */
	public JSONTokenizer(String json) {
		json = json.replace("\n", "");
		json = json.replace("\r", "");
		this.data = json.toCharArray();
	}

	public String nextToken(){
		lastPosition = position;
		if(position == data.length){
			return null;
		}
		StringBuffer buf = new StringBuffer();
		while(position != data.length){
			if(isWhitespace(data[position])){
				position++;
				continue;
			}
			if(isSpecialChar(data[position])){
				if(buf.length() == 0){
					buf.append(data[position++]);
				}
				return buf.toString().trim();
			}else if(data[position] == '"'){
				buf.append(data[position++]);
				while(data[position] != '"'){
					if(data[position] == '\\'){
						buf.append(data[position++]);
						buf.append(data[position++]);
					}
					buf.append(data[position++]);
				}
				buf.append(data[position++]);
			}else{
				buf.append(data[position++]);
			}
		}
		return buf.toString().trim();
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

	public boolean hasNext() {
		int pos = position;
		if (nextToken() != null) {
			position = pos;
			return true;
		}
		return false;
	}

	public void backToken() {
		position = lastPosition;
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"TestJson.json"));
			String line = "", json = "";
			while ((line = reader.readLine()) != null) {
				json += line;
			}

			JSONTokenizer tok = new JSONTokenizer(json);
			String token = "";// tok.nextToken();
			while ((token = tok.nextToken()) != null) {
				System.out.println(token);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
