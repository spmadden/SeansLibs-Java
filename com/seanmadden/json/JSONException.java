/**
 * 
 */
package com.seanmadden.json;

/**
 * @author spmfa
 *
 */
public class JSONException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3767917602038980618L;

	/**
	 * @param arg0
	 */
	public JSONException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public JSONException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JSONException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
