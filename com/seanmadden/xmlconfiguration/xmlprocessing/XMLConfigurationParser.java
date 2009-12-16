/**
 * 
 */
package com.seanmadden.xmlconfiguration.xmlprocessing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seanmadden.xmlconfiguration.XMLConfiguration;
import com.seanmadden.xmlconfiguration.XMLDataValue;
import com.seanmadden.xmlconfiguration.XMLValueTypeNotFoundException;

/**
 * @author spm2732
 *
 */
public class XMLParser {

	private XMLTokenizer token = null;
	private static String stElementRex = ".*<(.*)>.*";
	private static Pattern stElementPattern = Pattern.compile(stElementRex); 
	private static String eElementStRex = ".*<\\/";
	private static String eElementERex = ">.*";
	private XMLConfiguration config = null;
	
	public XMLParser(String xml, XMLConfiguration config){
		token = new XMLTokenizer(xml);
		this.config = config;
	}
	
	public boolean parse() throws XMLFormatException, XMLValueTypeNotFoundException{
		while(token.hasNext()){
			String element = token.nextToken();
			if(element.matches(stElementRex)){
				Matcher match = stElementPattern.matcher(element);
				match.find();
				XMLDataValue<?> valueParser = null;
				String name = match.group(1);
				if((valueParser = config.findParser(name)) != null){
					StringBuffer buf = new StringBuffer();
					Matcher matcher = null;
					matcher = Pattern.compile(eElementStRex + name + eElementERex).matcher(element);
					match.find();
					while(!matcher.matches()){
						buf.append(element);
						if(!token.hasNext()){
							throw new XMLFormatException("Unexpected end of Stream: Unmatched " + valueParser.acceptedDataType() + " end directive.");
						}
						element = token.nextToken();
						matcher.reset(element);
						matcher.find();
					}
					buf.append(element);
					System.out.println(buf.toString());
					XMLDataValue<?> data = valueParser.processXML(buf.toString());
					System.out.println(data);
					if(data != null)
						config.addValue(data.getName(), data.getValue(), data.getDescription());
				}
			}
		}
		return false;
	}
	
}
