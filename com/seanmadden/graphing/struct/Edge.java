/*
 * Edge.java
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
package com.seanmadden.graphing.struct;

import java.awt.Graphics;

import com.seanmadden.graphing.display.Renderer;
/**
 * Provides the structure for the connection between two endpoints.
 *
 * @author Sean P Madden
 */
public class Edge {
    
    /**
     * Source Node 
     */
    private Node src = null;
    
    /**
     * Destination Node
     */
    private Node dst = null;
    
    /**
     * The renderer associated with this data type.
     */
    private Renderer renderer = null;

    public Edge(Node src, Node dst){
        this.src = src;
        this.dst = dst;
    }
    
    /**
     * Returns the source
     *
     * @return source the source
     */
    public Node getSource() {
        return src;
    }

    /**
     * Sets the source
     *
     * @param source the source to set
     */
    public void setSource(Node source) {
        this.src = source;
    }

    /**
     * Returns the destination
     *
     * @return destination the destination
     */
    public Node getDestination() {
        return dst;
    }

    /**
     * Sets the destination
     *
     * @param destination the destination to set
     */
    public void setDestination(Node destination) {
        this.dst = destination;
    }

    /**
     * Returns the renderer
     *
     * @return renderer the renderer
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the renderer
     *
     * @param renderer the renderer to set
     */
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
    
    public void draw(Graphics g) {
        double x1 = src.point.x;
        double y1 = src.point.y;
        double x2 = dst.point.x;
        double y2 = dst.point.y;
//        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
}
    
}
