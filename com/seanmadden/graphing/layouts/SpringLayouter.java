/*
 * SpringLayouter.java
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
package com.seanmadden.graphing.layouts;

import java.util.*;

import com.seanmadden.graphing.struct.*;

public class SpringLayouter implements Layout, GraphListener {

	private Hashtable<Node, Double> xForces = new Hashtable<Node, Double>();

	private Hashtable<Node, Double> yForces = new Hashtable<Node, Double>();

	private double idealEdgeLength = 100;

	private double maxMove = 10;

	private boolean graphChanged = true;

	private Graph lastLayout = null;

	/*
	 * k = sqrt(area / #nodes) * .6 * 1 / canvas scale.
	 */
	private double k = .5;

	public void doLayout(Graph g) {
		// if(!graphChanged && g == lastLayout) return;
		// lastLayout = g;
		double temp = 20;

		Collection<Node> nodes = g.getAllNodes();

		HashSet<Node> hit = new HashSet<Node>();

		for (Node src : nodes) {
			hit.add(src);
			for (Node dst : nodes) {
				if (hit.contains(dst))
					continue;
				repulsiveForce(src, dst);
			}
		}

		for (Edge ed : g.getAllEdges()) {
			attractiveForce(ed.getSource(), ed.getDestination());
		}
		double fx, fy;
		for (Node nd : g.getAllNodes()) {
			fx = Math.min(temp, Math.max(xForces.get(nd), -temp));
			fy = Math.min(temp, Math.max(yForces.get(nd), -temp));
			
			fx += nd.getPoint().x;
			fy += nd.getPoint().y;
			nd.getPoint().setLocation(fx, fy);

			// System.out.println(nd.getPoint());
		}
		temp *= .995;
		xForces.clear();
		yForces.clear();
		System.out.println("emptied.");

	}

	public boolean isLayoutable(Graph g) {
		return false;
	}

	private double distance2(Node a, Node b) {
		return (b.getPoint().x - a.getPoint().x)
				+ +(b.getPoint().y - a.getPoint().y);
	}

	private double distance3(Node a, Node b) {
		return distance2(a, b) * distance(a, b);
	}

	private double distance(Node a, Node b) {
		return Math.sqrt(distance2(a, b));
	}

	private void attractiveForce(Node a, Node b) {
		double dx = b.getPoint().x - a.getPoint().x;
		double dy = b.getPoint().y - a.getPoint().y;
		double x = Math.sqrt(dx * dx + dy * dy);

		x = (x - k) / x;

		xForceAdd(a, dx);
		yForceAdd(a, dy);
		xForceAdd(b, -dx);
		xForceAdd(b, -dy);

	}

	private void repulsiveForce(Node a, Node b) {
		double dx = b.getPoint().x - a.getPoint().x;
		double dy = b.getPoint().y - a.getPoint().y;

		double x = 2 * (k) / ((dx * dx) + (dy * dy));

		dx *= x;
		dy *= x;
		xForceAdd(a, -dx);
		yForceAdd(a, -dy);
		xForceAdd(b, dx);
		yForceAdd(b, dy);
	}

	private double xForceAdd(Node a, double x) {
		if (xForces.contains(a)) {
			x += xForces.get(a);
		}
		xForces.put(a, x);
		return x;
	}

	private double yForceAdd(Node a, double y) {
		if (yForces.contains(a)) {
			y += yForces.get(a);
		}
		yForces.put(a, y);
		return y;
	}

	public void triggerChange(Graph g, Change change) {
		if (g == lastLayout) {
			graphChanged = true;
		} else {
			lastLayout = null;
			graphChanged = true;
		}
	}

}
