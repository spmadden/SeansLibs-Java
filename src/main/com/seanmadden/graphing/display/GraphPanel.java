/*
 * GraphPanel.java
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

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.seanmadden.graphing.struct.Edge;
import com.seanmadden.graphing.struct.Graph;
import com.seanmadden.graphing.struct.Node;

/**
 * This class represents a single viewport for the graph.
 *
 * @author Sean P Madden
 */
public class GraphPanel extends JPanel {
    /**
     * 
     */
    private Graph graph = null;

    /**
     *
     */
    private static final long serialVersionUID = -3758077864360180187L;

    /**
     * [Insert Comment Here]
     *
     * @param g The graph to copy
     */
    public GraphPanel(final Graph g) {
        this.setBackground(Color.white);
        graph = g;
        this.setSize(200, 200);
        this.validate();
    }

    /**
     * [Place method description here]
     *
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     * @param g the graphics to paint on.
     */
    @Override
    public final void paint(final Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        for (Object ed : graph.getAllEdges()) {
            ((Edge) ed).draw(g);
        }
        for (Object nd : graph.getAllNodes()) {
            ((Node) nd).paint(g, this);
        }
    }
}
