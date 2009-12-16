package com.seanmadden.xmlconfiguration.xmlprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
	private String data = "";
	private XMLTokenizer token = null;
	private static String startRex = "<(.*).*>";
	private static Pattern startMatch = Pattern.compile(startRex);
	private static String endRex = "</(.*)>";
	private static Pattern endMatch = Pattern.compile(endRex);
	private static String singleRex = "<(.*)/>";
	private static Pattern singleMatch = Pattern.compile(singleRex);
	
	private XMLParser(String data){
		this.data = data;
		token = new XMLTokenizer(data);
	}
	
	private DOMElement parse(DOMElement dom) throws XMLFormatException{
		if(dom == null)
			dom = new DOMElement();
		while(token.hasNext()){
			String tok = token.nextToken();
			if(tok.matches(startRex)){
				Matcher match = startMatch.matcher(tok);
				match.find();
				String name = match.group(1);
				DOMElement newDom = new DOMElement(name);
				System.out.println("SUB: " + name);
				while((tok != null ) && !tok.matches(endRex)){
					parse(newDom);
					tok = token.nextToken();
				}
				dom.addSubElement(newDom.getName(), newDom);
				
			}else if(tok.matches(singleRex)){
				
			}else{
				// assume textual data.
				System.out.println(tok);
			}
		}
		return dom;
	}
	
	
	public void parseAttributesIntoDom(DOMElement dom, String elements){
		
	}
	
	public static DOMElement parse(String data) throws XMLFormatException{
		return new XMLParser(data).parse((DOMElement)null);
	}
	
	public static void main(String[] args){
		try {
			// http://twitter.com/statuses/user_timeline/75026048.rss
			// this is a test method to pull ThingsLutzSays on twitter to playwith and parse.
			URL url = new URL("HTTP", "twitter.com", 80, "/statuses/user_timeline/75026048.rss");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			InputStream in = connection.getInputStream();
			int chr =  -1;
			StringBuffer str = new StringBuffer();
			while((chr = in.read()) != -1){
				str.append((char)chr);
			}
			connection.disconnect();
			parse(str.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLFormatException e) {
			e.printStackTrace();
		}
	}
}
