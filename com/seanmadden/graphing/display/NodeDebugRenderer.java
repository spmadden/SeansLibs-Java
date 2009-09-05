/*
 * NodeDebugRenderer.java
 * 
 * Copyright (C) 2008 Sean P Madden
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * If you would like to license this code under the GNU LGPL, please see
 * http://www.seanmadden.net/licensing for details.
 * 
 */
package com.seanmadden.graphing.display;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


import com.seanmadden.graphing.struct.Node;

/**
 * This is an implementation of the Renderer interface to give debug information
 * about the node in question.
 *
 * @author Sean P Madden
 */
public class NodeDebugRenderer implements Renderer {

    /**
     * 
     */
    private JPanel jp = new JPanel(new BorderLayout());

    /**
     * 
     */
    private JLabel lblID = new JLabel("ID: ");

    /**
     * 
     */
    private JLabel lblData = new JLabel("Data: ");

    /**
     * 
     */
    private JLabel lblIDData = new JLabel();

    /**
     * 
     */
    private JLabel lblDataData = new JLabel();

    /**
     * 
     */
    private Border border = new EtchedBorder(EtchedBorder.RAISED);

    /**
     * 
     */
    private boolean changed = true;

    /**
     * [Insert Comment Here]
     *
     */
    public NodeDebugRenderer() {
        jp.setLayout(new GridLayout(2, 2));
        jp.add(lblID);
        jp.add(lblIDData);
        jp.add(lblData);
        jp.add(lblDataData);
        jp.setBorder(border);
    }

    /**
     * [Place method description here]
     *
     * @param nd the node to draw
     * @return the component representing this node
     */
    public final JComponent getComponent(final Node nd) {
        if (changed) {
            lblDataData.setText(nd.getData() + "");
            lblIDData.setText(nd.getIndex() + "");
            jp.setSize((int) nd.getSize().x, (int) nd.getSize().y);
            jp.doLayout();
        }
        return jp;
    }

}
