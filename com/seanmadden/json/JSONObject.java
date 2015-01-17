/**
 * 
 */
package com.seanmadden.json;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author spm2732
 * 
 */
public class JSONObject { 

	/**
	 * The Type of this object.
	 */
	private JSONObjectType type;

	/**
	 * A String-Value mapping of the contained objects.
	 */
	private Map<String, JSONObject> subObjects = new Hashtable<String, JSONObject>();
	/**
	 * A string-string mapping of the values contained within.
	 */
	private Map<String, String> values = new Hashtable<String, String>();
	
	/**
	 * A list of the inner values (if we're a string array)
	 */
	private List<JSONObject> array = new Vector<JSONObject>();

	/**
	 * Make me a JSONObject
	 */
	public JSONObject() {

	}

	/**
	 * Make me a JSONObject
	 * @param t the type.
	 */
	protected JSONObject(JSONObjectType t) {
		this.type = t;
	}

	/**
	 * Make me a JSONObject
	 * @param object copying this object.
	 */
	public JSONObject(JSONObject object) {
		this.values = new Hashtable<String,String>(object.values);
		this.array = new Vector<JSONObject>(object.array);
		this.type = object.type;
		this.subObjects = new Hashtable<String, JSONObject>();
		for(Entry<String, JSONObject>ent : object.subObjects.entrySet()){
			subObjects.put(ent.getKey(), new JSONObject(ent.getValue()));
		}
	}

	/**
	 * Adds a subObject
	 *
	 * @param name the key of this sub object
	 * @param obj the object.
	 */
	public void addSubObject(String name, JSONObject obj) {
		subObjects.put(name, obj);
	}

	/**
	 * Adds a string value to this object.
	 *
	 * @param name the key
	 * @param value the value
	 */
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

}
