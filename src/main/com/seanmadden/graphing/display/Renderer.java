/*
* Renderer.java
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
package com.seanmadden.graphing.display;

import javax.swing.JComponent;

import com.seanmadden.graphing.struct.Node;

/**
 * This interface defines the basic properties of the renderer.
 *
 * @author Sean P Madden
 */
public interface Renderer {
        /**
        * [Place method description here]
        * 
        * @param nd the node we represent
        * @return the component that represents this node
        */
        JComponent getComponent(Node nd);
}
