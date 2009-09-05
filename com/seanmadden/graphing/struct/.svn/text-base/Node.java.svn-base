/*
 * Node.java
 *
 *    Copyright (C) 2008 Sean P Madden
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
package com.seanmadden.graphing.struct;

import java.awt.Container;
import java.awt.Graphics;
import java.util.*;

import javax.swing.*;

import com.seanmadden.graphing.display.Renderer;

/**
 * This class represents the basic information structure, the vertex, of the
 * graph.
 *
 * @author Sean P Madden
 */
public class Node {
        /**
         * The enclosed data of this node structure.
         */
        protected Object data = null;

        /**
         * The point, in X,Y coordinates of the upper-left hand corner of this
         * node.
         */
        protected DPoint point = new DPoint();

        /**
         * The Width (X) and Height (Y) of this node
         */
        protected DPoint size = new DPoint();

        /**
         * Registered index (unique) of this node
         */
        protected int index = 0;

        /**
         * The Renderer associated with this node
         */
        protected Renderer rend = null;

        /**
         * A hash list of the neighbors (hash only because neighbors are unique)
         */
        protected HashSet<Node> neighbors = new HashSet<Node>();

        /**
         * Constructor to create a node based from some
         *
         * @param data
         * @param index
         */
        protected Node(Object data, int index) {
                this.data = data;
                this.index = index;
                this.size.x = 100;
                this.size.y = 50;
        }

        protected Node() {
                this(null, Integer.MAX_VALUE);
        }

        /**
         * Returns the data
         *
         * @return data the data
         */
        public Object getData() {
                return data;
        }

        /**
         * Sets the data
         *
         * @param data
         *                the data to set
         */
        public void setData(Object data) {
                this.data = data;
        }

        /**
         * Returns the point
         *
         * @return point the point
         */
        public DPoint getPoint() {
                return point;
        }

        /**
         * Sets the point
         *
         * @param point
         *                the point to set
         */
        public void setPoint(DPoint point) {
                this.point = point;
        }

        /**
         * [Place method description here]
         *
         * @param g
         * @param c
         */
        public void paint(Graphics g, Container c) {
                JComponent comp = rend.getComponent(this);
                SwingUtilities.paintComponent(g, comp, c,
                                (int) point.x, (int) point.y,
                                (int) size.x, (int) size.y);
        }

        /**
         * Returns the index
         *
         * @return index the index
         */
        public int getIndex() {
                return index;
        }

        /**
         * Sets the index
         *
         * @param index
         *                the index to set
         */
        public void setIndex(int index) {
                this.index = index;
        }

        /**
         * Returns the rend
         *
         * @return rend the rend
         */
        public Renderer getRend() {
                return rend;
        }

        /**
         * Sets the rend
         *
         * @param rend
         *                the rend to set
         */
        public void setRend(Renderer rend) {
                this.rend = rend;
        }

        /**
         * Returns the size
         *
         * @return size the size
         */
        public DPoint getSize() {
                return size;
        }

        /**
         * Sets the size
         *
         * @param size
         *                the size to set
         */
        public void setSize(DPoint size) {
                this.size = size;
        }

        public void addNeighbor(Node nd){
                neighbors.add(nd);
        }

        public void rmNeighbor(Node nd){
                neighbors.remove(nd);
        }

        public String toString(){
                return "Node:{id:"+index+";data:"+data+"}";
        }

}
