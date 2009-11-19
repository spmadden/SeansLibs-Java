/*
 * XMLEditGui.java
 * 
 *    Copyright (C) 2009 Sean P Madden
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
package com.seanmadden.xmlconfiguration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is designed as a dynamically changing GUI that allows the user to
 * edit configuration settings live. It provides minimal error checking.
 * 
 * I've designed this class to use a ghetto implementation of the command
 * pattern by creating action listeners that don't actually listen for actions.
 * They are going to be used as processing callbacks for the different types of
 * variables we're storing and editing here.
 * 
 * @author Sean P Madden
 */
public class XMLEditGui {
	/**
	 * A reference to the XML Configuration object we're editing.
	 */
	private XMLConfiguration config;

	/**
	 * This basic class is used simply to maintain state for two JComponenets,
	 * it's label and value editor. It is essentially used as a single row of
	 * the table we're going to build.
	 * 
	 * @author Sean P Madden
	 */
	private class TableRow {
		JComponent label;
		JComponent edit;
	}

	/**
	 * This is the containers of JComponents that the GUI will be using.
	 */
	private Vector<TableRow> components = new Vector<TableRow>();

	/**
	 * This is the ActionListener we're going to use to process the Strings when
	 * they're changed.
	 */
	private ActionListener stringListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {

		}
	};

	/**
	 * This is the ActionListener we're going to use to process the Integers
	 * when they're changed
	 */
	private ActionListener intsListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		}
	};

	/**
	 * This is the ActionListener we're going to use to process the Booleans
	 * when they're done.
	 */
	private ActionListener boolsListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		}
	};

	/**
	 * Can't have a GUI to edit NULL!
	 * 
	 * @param config
	 */
	public XMLEditGui(XMLConfiguration config) {
		this.config = config;
	}

	protected void displayGUI() {
		for(Entry<String, XMLDataValue<String>> ent : config.stringsTable.entrySet()){
			JTextField comp = new JTextField();
			comp.setText(ent.getValue().getValue());
			comp.setActionCommand(ent.getKey());
			comp.addActionListener(stringListener);
			JLabel label = new JLabel(ent.getValue().getDescription());
			
			TableRow row = new TableRow();
			row.label = label;
			row.edit = comp;
			
			components.add(row);
		}
		
		for(Entry<String, XMLDataValue<Integer>> ent : config.intsTable.entrySet()){
			JTextField comp = new JTextField();
			comp.setText(ent.getValue().getValue().toString());
			comp.setActionCommand(ent.getKey());
			comp.addActionListener(intsListener);
			JLabel label = new JLabel(ent.getValue().getDescription());
			
			TableRow row = new TableRow();
			row.edit = comp;
			row.label = label;
			
			components.add(row);
			
		}
		
		JFrame panel = new JFrame("Edit Configuration");
		
		
	}
}
