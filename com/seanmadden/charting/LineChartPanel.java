/*
 * LineChart.java
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
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This represents a panel for a basic line chart.
 * 
 * @author sean.madden
 */
public class LineChartPanel extends JPanel {

	/**
	 * Default gen'd
	 */
	private static final long serialVersionUID = -362327042823080934L;

	/**
	 * The list of series to display on this chart.
	 */
	private ChartSeries[] series;

	/**
	 * Internal options for this chart.
	 */
	private ChartOptions options;

	/**
	 * Make me a LineChartPanel
	 */
	public LineChartPanel() {
		this(new ChartOptions());
	}

	/**
	 * Make me a LineChartPanel
	 * 
	 * @param o
	 *            Options
	 */
	public LineChartPanel(ChartOptions o) {
		super();
		options = o;
	}

	/**
	 * Make me a LineChartPanel
	 * 
	 * @param s
	 *            The series to include
	 * @param o
	 *            Any additional options.
	 */
	public LineChartPanel(ChartOptions o, ChartSeries... s) {
		super();
		series = s;
		options = o;
	}

	/**
	 * Make me a LineChartPanel
	 * 
	 * @param s
	 *            The series to include.
	 */
	public LineChartPanel(ChartSeries... s) {
		this(new ChartOptions(), s);
	}

	/**
	 * Gets and returns options
	 * 
	 * @return the options
	 */
	public ChartOptions getOptions() {
		return options;
	}

	/**
	 * Gets and returns series
	 * 
	 * @return the series
	 */
	public ChartSeries[] getSeries() {
		return series;
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * @param g
	 *            Graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {

		// Paint background.
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw Axis
		g.setColor(Color.yellow);
		int percentOfWidth = (int) (getWidth() * .1);
		int percentOfHeight = (int) (getHeight() * .1);
		g.drawLine(percentOfWidth, percentOfHeight, percentOfWidth, getHeight()
				- percentOfHeight);
		g.drawLine(percentOfWidth, getHeight() - percentOfHeight, getWidth()
				- percentOfWidth, getHeight() - percentOfHeight);

	}

	/**
	 * Sets the options
	 * 
	 * @param o
	 *            the options to set
	 */
	public void setOptions(ChartOptions o) {
		options = o;
	}

	/**
	 * Sets the series
	 * 
	 * @param s
	 *            the series to set
	 */
	public void setSeries(ChartSeries[] s) {
		series = s;
	}
}
