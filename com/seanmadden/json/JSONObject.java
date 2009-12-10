/**
 * 
 */
package com.seanmadden.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author spm2732
 * 
 */
public class JSONObject {

	private JSONTokenizer tok = null;
	private JSONObjectType type = null;

	private Hashtable<String, JSONObject> subObjects = new Hashtable<String, JSONObject>();
	private Hashtable<String, String> values = new Hashtable<String, String>();
	private Vector<String> array = new Vector<String>();

	public JSONObject() {

	}

	private JSONObject(JSONObjectType type) {
		this.type = type;
	}

	public JSONObject(String jsonData) {
		this(JSONObjectType.OBJECT);
		tok = new JSONTokenizer(jsonData);
		JSONObject object = parse(null);

		this.values = object.values;
		this.array = object.array;
		this.type = object.type;
		this.subObjects = object.subObjects;
	}

	public JSONObject(JSONObject object) {
		this.values = object.values;
		this.array = object.array;
		this.type = object.type;
		this.subObjects = object.subObjects;
	}

	public void addSubObject(String name, JSONObject obj) {
		subObjects.put(name, obj);
	}

	public void addValue(String name, String value) {
		values.put(name, value);
	}

	public String getString(String name) throws JSONException {
		if (values.get(name) == null) {
			throw new JSONException("Key not found.");
		}
		return values.get(name);
	}

	public Integer getInt(String name) throws JSONException {
		if (values.get(name) == null) {
			throw new JSONException("Key not found.");
		}
		Integer in = 0;
		try {
			in = Integer.parseInt(values.get(name));
		} catch (NumberFormatException e) {
			throw new JSONException("Value does not represent an integer: ("
					+ name + " : " + values.get(name) + ")");
		}
		return in;
	}

	protected JSONObject parse(JSONObject in) {
		String name = null;
		while (tok.hasNext()) {
			String item = tok.nextToken();
			if (item.equals(":")) {
				// yay nothing. absorb the token.
			} else if (item.equals("{")) {
				JSONObject newObj = parseSubObject();
				if (name != null) {
					in.addSubObject(name, newObj);
					name = null;
				} else
					return newObj;
			} else if (item.equals("[")) {
				JSONObject newObj = parseArray();
				in.addSubObject(name, newObj);
				name = null;
			} else if (item.equals(",")) {
				// yay nothing. absorb the token
			} else if (item.equals("}")) {
				break;
			} else {
				if (name == null) {
					name = item;
				} else {
					in.addValue(name, item);
					name = null;
				}
			}

		}
		return in;
	}

	private JSONObject parseSubObject() {
		JSONObject obj = new JSONObject(JSONObjectType.OBJECT);
		return parse(obj);
	}

	private JSONObject parseArray() {
		JSONObject obj = new JSONObject(JSONObjectType.ARRAY);
		String token = null;
		Vector<String> list = new Vector<String>();
		while (!(token = tok.nextToken()).equals("]")) {
			if (!token.equals(",")) {
				list.add(token);
			}
		}
		obj.array = list;
		return obj;
	}

	public String toJSON() {
		return toJSON(true);
	}

	public String toJSON(boolean addWhitespace) {
		if (type == JSONObjectType.ARRAY) {
			return array.toString();
		}
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		if (addWhitespace) {
			buf.append("\n");
		}
		Entry<String, String>[] set = (Entry<String, String>[]) values
				.entrySet().toArray(new Entry[] {});
		for (int i = 0; i < set.length; i++) {
			if (addWhitespace) {
				buf.append("\t");
			}
			buf.append(set[i].getKey() + ":" + set[i].getValue());
			if (i < set.length - 1 || subObjects.size() > 0) {
				buf.append(",");
			}
			if (addWhitespace) {
				buf.append("\n");
			}
		}
		Entry<String, JSONObject>[] setObj = (Entry<String, JSONObject>[]) subObjects
				.entrySet().toArray(new Entry[] {});
		for (int i = 0; i < setObj.length; i++) {
			if (addWhitespace) {
				buf.append("\t");
			}
			String json = setObj[i].getKey() + ":"
					+ setObj[i].getValue().toJSON(addWhitespace);
			if (i < setObj.length - 1) {
				json += ",";
			}
			if (addWhitespace) {
				Scanner scan = new Scanner(json);
				buf.append(scan.nextLine() + "\n");
				while (scan.hasNextLine()) {
					buf.append("\t" + scan.nextLine() + "\n");
				}
			} else {
				buf.append(json);
			}
		}

		buf.append("}");

		return buf.toString();

	}

	public static void main(String[] args) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("TestJson.json"));
			String line = "", json = "";
			while ((line = reader.readLine()) != null) {
				json += line;
			}

			JSONObject jsonObj = new JSONObject(json);
			System.out.println(jsonObj.toJSON(true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
