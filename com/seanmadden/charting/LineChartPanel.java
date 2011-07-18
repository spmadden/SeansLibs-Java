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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * This represents a panel for a basic line chart.
 * 
 * @author sean.madden
 */
public class LineChartPanel extends JPanel implements SeriesChangedListener {

	/**
	 * Default gen'd
	 */
	private static final long serialVersionUID = -362327042823080934L;

	/**
	 * The list of series to display on this chart.
	 */
	private ChartSeries[] series = new ChartSeries[] {};

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
	 * Finds and returns the bounds of the series S
	 * 
	 * @param s
	 *            The series to get bounds for.
	 * @return The minimum and maximum X and Y values in the series.
	 */
	private static Rectangle2D.Double getBounds(ChartSeries s) {
		Rectangle2D.Double ret = new Rectangle2D.Double(
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		for (Point2D.Double point : s.getValues()) {
			if (point.x < ret.x) {
				ret.x = point.x;
			}
			if (point.y < ret.y) {
				ret.y = point.y;
			}
			if (point.x > ret.width) {
				ret.width = point.x;
			}
			if (point.y > ret.height) {
				ret.height = point.y;
			}
		}
		return ret;
	}

	/**
	 * @see com.seanmadden.charting.SeriesChangedListener#elementChanged()
	 */
	@Override
	public void elementChanged() {
		repaint();
		System.out.println("changed called.");
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
	public void paint(Graphics g) {
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

		for (ChartSeries s : series) {
			Point2D.Double[] points = s.getValues();
			Rectangle2D.Double bounds = getBounds(s);
			g.setColor(s.getColor());
			double xRange = bounds.width - bounds.x;
			double xScale = .8 * getWidth() / xRange;
			double yRange = bounds.height - bounds.y;
			double yScale = .8 * getHeight() / yRange;
			double xShift = bounds.x * xScale + percentOfWidth;
			double yShift = bounds.y * yScale;

			for (int i = 1; i < points.length; i++) {
				Point2D.Double prevPoint = points[i - 1];
				Point2D.Double point = points[i];
				g.drawLine((int) prevPoint.x, (int) prevPoint.y, (int) point.x,
						(int) point.y);
				g.drawLine((int) ((prevPoint.x + xShift) * xScale),
						(int) ((prevPoint.y + yShift) * yScale),
						(int) ((point.x + xShift) * xScale),
						(int) ((point.y + yShift) * yScale));
			}
		}
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
	public void setSeries(ChartSeries... s) {
		series = s;
		for (ChartSeries ser : s) {
			((DefaultChartSeries) ser).addChangedListener(this);
		}
	}

}
