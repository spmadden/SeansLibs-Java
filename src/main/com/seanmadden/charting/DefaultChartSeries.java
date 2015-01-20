/*
 * DefaultChartSeries.java
 * 
 * Copyright (C) 2011 Sean P Madden (sean.madden)
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
package com.seanmadden.charting;

import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * @author sean.madden
 * 
 */
public class DefaultChartSeries implements ChartSeries {

	/**
	 * The series of points.
	 */
	private Double[] values = new Double[0];

	/**
	 * The name!
	 */
	private String name;

	/**
	 * The listeners to notify upon changes.
	 */
	private final List<SeriesChangedListener> listeners = new Vector<SeriesChangedListener>();

	/**
	 * The color of this series - defaults to red.
	 */
	private Color color = Color.red;

	/**
	 * Make me a DefaultChartSeries
	 * 
	 * @param n
	 *            The name of this series.
	 */
	public DefaultChartSeries(String n) {
		name = n;
	}

	/**
	 * Adds a point to the series.
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 */
	public void addPoint(double x, double y) {
		Double[] array = new Double[values.length + 1];
		System.arraycopy(values, 0, array, 0, values.length);
		array[values.length] = new Double(x, y);
		values = array;
		triggerChange();
	}

	/**
	 * @see com.seanmadden.charting.ChartSeries#getColor()
	 * @return A color!
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * @see com.seanmadden.charting.ChartSeries#getName()
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see com.seanmadden.charting.ChartSeries#getValues()
	 * @return A list of the y values.
	 */
	@Override
	public Double[] getValues() {
		return values;
	}

	/**
	 * Sets the color
	 * 
	 * @param c
	 *            the color to set
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Sets the name
	 * 
	 * @param n
	 *            the name to set
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * Adds a listener to receive events when this series is changed.
	 * 
	 * @param listen
	 *            The element to notify.
	 */
	protected void addChangedListener(SeriesChangedListener... listen) {
		listeners.addAll(Arrays.asList(listen));
		triggerChange();
	}

	/**
	 * Called to notify the listeners that a change happened.
	 * 
	 */
	protected void triggerChange() {
		for (SeriesChangedListener l : listeners) {
			l.elementChanged();
		}
	}

}
