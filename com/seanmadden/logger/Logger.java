/**
 * 
 */
package com.seanmadden.logger;

import java.util.Hashtable;

/**
 * @author spmfa
 *
 */
public class Logger {

	private static Hashtable<Object, Logger> loggers = new Hashtable<Object, Logger>();
	
	/**
	 * 
	 */
	private Logger() {
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLogger(Object toLog){
		if(loggers.get(toLog) == null){
			Logger log = new Logger();
			loggers.put(toLog, log);
		}
		return loggers.get(toLog);
	}
	
	public void debug(LogEvent ev, String message){
		
	}
	
	public void setOptions(){
		
	}
	
}
