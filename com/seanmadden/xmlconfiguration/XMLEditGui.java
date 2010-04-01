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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public class XMLEditGui implements ActionListener {
	private JFrame panel = new JFrame("Edit Configuration");

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
		String name;
		String type;
	}

	/**
	 * This is the containers of JComponents that the GUI will be using.
	 */
	private Vector<TableRow> components = new Vector<TableRow>();

	/**
	 * Can't have a GUI to edit NULL!
	 * 
	 * @param config
	 */
	public XMLEditGui(XMLConfiguration config) {
		this.config = config;
	}

	/**
	 * Dynamically generates and displays a gui the user can interface with and
	 * edit this system.
	 * 
	 */
	protected void displayGUI() {
		for (Entry<String, XMLDataValue<Object>> ent : config.dataTable
				.entrySet()) {
			
			JComponent comp = null;
			String desc = ent.getValue().getDescription();
			if(desc == null){
				desc = ent.getKey();
			}
			JLabel label = new JLabel(desc);
			String dataType = ent.getValue().acceptedDataType();
			
			if(dataType.equals("Boolean")){
				comp = new JComboBox(new String[] { "Yes", "No" });
				((JComboBox)comp).setActionCommand(ent.getKey());
			}else if(dataType.equals("String") || dataType.equals("Integer")){
				comp = new JTextField();
				JTextField tf = (JTextField) comp;
				tf.setText(ent.getValue().getValue()+"");
				tf.setActionCommand(ent.getKey());
			}

			TableRow row = new TableRow();
			row.label = label;
			row.edit = comp;
			row.name = ent.getKey();
			row.type = ent.getValue().acceptedDataType();
			
			components.add(row);
		}

		panel.setLayout(new GridLayout(components.size() + 1, 2));
		for (TableRow comp : components) {
			panel.add(comp.label);
			panel.add(comp.edit);
		}
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setActionCommand("SaveXML");
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("CancelClose");

		panel.add(saveButton);
		panel.add(cancelButton);

		panel.pack();
		panel.setVisible(true);
		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public static void main(String[] args) {
		XMLConfiguration xml = new XMLConfiguration();

		try {
			xml.addValue("TestInt1", 15);
			xml.addValue("TestInt2", 20, "This is a test Integer");
			xml.addValue("TestString1", "Test String One");
			xml.addValue("TestString2", "Test String Two",
					"This is a Test string, index two");
			xml.addValue("TestBool1", true);
			xml.addValue("TestBool2", false,
					"This is a test boolean value.  NOT.");
			xml.addValue("Showing off 1", "I am showing off", "This is showing off");
		} catch (XMLValueTypeNotFoundException e) {
			e.printStackTrace();
		}

		XMLEditGui gui = new XMLEditGui(xml);
		gui.displayGUI();
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("SaveXML")) {
			for (TableRow row : components) {
				if (row.type.equals("Integer")) {
					JTextField field = (JTextField) row.edit;
					JLabel label = (JLabel) row.label;
					try {
						try {
							config.addValue(row.name, Integer.valueOf(field
									.getText()), label.getText());
						} catch (XMLValueTypeNotFoundException e1) {
							e1.printStackTrace();
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, field.getText()
								+ " is not an integer.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else if (row.type.equals("String")) {
					JTextField field = (JTextField) row.edit;
					JLabel label = (JLabel) row.label;
					try {
						config.addValue(row.name, field.getText(), label
								.getText());
					} catch (XMLValueTypeNotFoundException e1) {
						e1.printStackTrace();
					}
				} else if (row.type.equals("Boolean")) {
					JComboBox field = (JComboBox) row.edit;
					JLabel label = (JLabel) row.label;
					Boolean value = field.getSelectedItem().equals("Yes");
					try {
						config.addValue(row.name, value, label.getText());
					} catch (XMLValueTypeNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
			System.out.println(config.generateXML());
			panel.dispose();
		} else if (cmd.equals("CancelClose")) {
			panel.dispose();
		}
	}
}
