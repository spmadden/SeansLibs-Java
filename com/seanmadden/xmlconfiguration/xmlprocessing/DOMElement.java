package com.seanmadden.xmlconfiguration.xmlprocessing;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class DOMElement {
	private Hashtable<String, DOMElement> subelements = new Hashtable<String, DOMElement>();
	private Hashtable<String, String> attributes = new Hashtable<String, String>();
	private String internalData = "";
	private String name = "";
	
	protected DOMElement(String name){
		this.name = name;
	}
	
	protected DOMElement(){
		
	}
	
	protected DOMElement(DOMElement other){
		this.name = other.name;
		this.subelements = (Hashtable<String, DOMElement>)other.subelements.clone();
		this.attributes = (Hashtable<String,String>)other.attributes.clone();
		this.internalData = other.internalData;
	}
	
	public String getName(){
		return name;
	}
	
	public String getData(){
		return internalData;
	}
	
	public DOMElement getSubElement(String name){
		return subelements.get(name);
	}
	
	protected void addSubElement(String name, DOMElement elem){
		subelements.put(name, elem);
	}
	
	protected void setdata(String data){
		this.internalData = data;
	}
	
	public String getAttribute(String name){
		return attributes.get(name);
	}
	
	protected void addAttribute(String name, String value){
		attributes.put(name, value);
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("<" + name  );
		for(Map.Entry<String, String> ent : attributes.entrySet()){
			buf.append(" " + ent.getKey() + "=\""+ ent.getValue() + "\"");
		}
		buf.append(" >\n");
		for(Map.Entry<String, DOMElement> ent : subelements.entrySet()){
			Scanner scan = new Scanner(ent.getValue().toString());
			while(scan.hasNextLine()){
				buf.append("\t" + scan.nextLine() + "\n");
			}
		}
		buf.append("\t" + internalData + "\n");
		buf.append("</" + name + ">\n");
		return buf.toString();
	}
	
	public static void main(String[] args){
		DOMElement dom = new DOMElement("TestDomElement");
		dom.addAttribute("TestAttr", "TestValue");
		dom.addAttribute("TestElem", "TestVal2");
		dom.setdata("This is some dumb text within the element.");
		dom.addSubElement("TestSubElement", new DOMElement(dom));
		System.out.println(dom.toString());
		
	}
}
