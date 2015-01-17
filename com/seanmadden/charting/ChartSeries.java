/*
 * ChartSeries.java
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
import java.awt.geom.Point2D;

/**
 * This interface represents a series of data points for display on a chart.
 * 
 * @author sean.madden
 * 
 */
public interface ChartSeries {

	/**
	 * Returns the specified color of this series.
	 * 
	 * @return Color, the color to draw.
	 */
	public Color getColor();

	/**
	 * Returns the name of this series.
	 * 
	 * @return String name.
	 */
	public String getName();

	/**
	 * Returns the values in this series
	 * 
	 * @return Point2D.Double[] of points.
	 */
	public Point2D.Double[] getValues();
}
