/*
 * Graph.java
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
 */
package com.seanmadden.graphing.struct;

import java.util.*;

import com.seanmadden.graphing.display.NodeDebugRenderer;

/*
 * Holds and manages the structure of the graph.
 * 
 * @author Sean P Madden
 */
public class Graph {

        /**
         * List of GraphListeners
         */
        private Vector<GraphListener> changeListeners = new Vector<GraphListener>();

        private HashSet<Edge> edges = new HashSet<Edge>();

        private Integer lastIndex = 0;

        private Hashtable<Integer, Node> nodeIndices = new Hashtable<Integer, Node>();

        private Hashtable<Object, Node> nodeData = new Hashtable<Object, Node>();

        /**
         * Duplicated another graph
         *
         * @param g The Graph to Duplicate
         */
        public Graph(Graph g) {
                changeListeners.addAll(g.getListeners());
        }

        /**
         * Creates a new empty graph
         *
         */
        public Graph() {

        }

        /**
        * Returns a list of the current graph listeners
        * 
        * @return a list of the currently associated graph listeners
        */
        public Collection<GraphListener> getListeners() {
                return changeListeners;
        }

        /**
        * Adds a GraphListener
        * 
        * @param gl GraphListener to add
        */
        public void addListener(GraphListener gl) {
                this.changeListeners.add(gl);
        }

        /**
        * Triggers a change down to the listeners
        * 
        * @param ct the change type we're triggering.
        */
        protected void triggerChange(ChangeType ct, Object data) {

                for (GraphListener gl : changeListeners) {

                        gl.triggerChange(this, new Change(
                                        (data instanceof Edge) ? (Edge) data
                                                        : null,
                                        (data instanceof Node) ? (Node) data
                                                        : null, ct));
                }
        }

        /**
        * 
        * 
        * @param data
        * @return
        */
        public Node getNodeByData(Object data) {
                return nodeData.get(data);
        }

        public Node createNode(Object data) throws NullDataException {
                Random random = new Random();
                Node nd = new Node(data, lastIndex++);
                nd.setRend(new NodeDebugRenderer());
                nd.point.x = random.nextInt(300);
                nd.point.y = random.nextInt(300);
                addNode(nd);
                return nd;
        }

        public void addNode(Node nd) throws NullDataException {
                if (nd.getData() == null) { throw new NullDataException(); }
                this.nodeData.put(nd.getData(), nd);
                this.nodeIndices.put(nd.getIndex(), nd);
                triggerChange(ChangeType.NODEADD, nd);
        }

        public Collection<Node> getAllNodes() {
                return nodeIndices.values();
        }

        public Edge createEdge(Node src, Node dst) {
                Edge ed = new Edge(src, dst);
                src.addNeighbor(dst);
                dst.addNeighbor(src);
                addEdge(ed);
                return ed;
        }

        public Edge createEdge(int src, int dst) {
                return createEdge(nodeIndices.get(src), nodeIndices.get(dst));
        }

        public void addEdge(Edge ed) {
                edges.add(ed);
                triggerChange(ChangeType.EDGEADD, ed);
        }

        public Collection<Edge> getAllEdges() {
                return edges;
        }

}
