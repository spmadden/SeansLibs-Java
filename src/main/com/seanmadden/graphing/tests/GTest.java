/*
* GTest.java
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
package com.seanmadden.graphing.tests;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.seanmadden.graphing.display.GraphPanel;
import com.seanmadden.graphing.layouts.SpringLayouter;
import com.seanmadden.graphing.struct.Graph;
import com.seanmadden.graphing.struct.NullDataException;
public class GTest {


        public static void main(String args[]){
                JFrame jf = new JFrame("TEST");

                jf.setLayout(new BorderLayout());
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                final Graph g = new Graph();

                try {
                    g.createNode(2);
                    g.createNode(2);
//                    g.createNode(2);
//                    g.createNode(2);
                } catch (NullDataException e) {
                    e.printStackTrace();
                }

                g.createEdge(0, 1);
//                g.createEdge(2, 3);
//                g.createEdge(2, 1);

                
                final SpringLayouter sl = new SpringLayouter();
                final GraphPanel gp = new GraphPanel(g);
                
                JToolBar jt = new JToolBar();
                JButton jb = new JButton("Run");
                jb.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent arg0) {
                        sl.doLayout(g);
                        gp.repaint();
                        gp.validate();
                    }
                    
                });
                jt.add(jb);
                
                jf.add(jb, BorderLayout.NORTH);
                jf.add(gp, BorderLayout.CENTER);
                jf.validate();
                jf.doLayout();
                jf.setSize(400, 400);
                jf.setVisible(true);
        }
}
