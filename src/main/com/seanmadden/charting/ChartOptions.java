/*
 * ChartOptions.java
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

/**
 * This class represents a unique set of options available for application to a
 * chart.
 * 
 * @author sean.madden
 */
public class ChartOptions {
	/**
	 * the title of this chart.
	 */
	private String chartTitle;
	/**
	 * the label of the x axis
	 */
	private String xAxisLabel;
	/**
	 * the label of the y axis
	 */
	private String yAxisLabel;
	/**
	 * the base value (defaults to zero) of the x axis.
	 */
	private double xAxisBase;
	/**
	 * the base value (defaults to zero) of the y axis.
	 */
	private double yAxisBase;
	/**
	 * the dynamic range of the x axis (defaults to autoscale (0))
	 */
	private double xAxisRange;
	/**
	 * The dynamic range of the y axis (defaults to autoscale (0))
	 */
	private double yAxisRange;

	/**
	 * Make me a ChartOptions
	 */
	public ChartOptions() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets and returns chartTitle
	 * 
	 * @return the chartTitle
	 */
	public String getChartTitle() {
		return chartTitle;
	}

	/**
	 * Gets and returns xAxisBase
	 * 
	 * @return the xAxisBase
	 */
	public double getxAxisBase() {
		return xAxisBase;
	}

	/**
	 * Gets and returns xAxisLabel
	 * 
	 * @return the xAxisLabel
	 */
	public String getxAxisLabel() {
		return xAxisLabel;
	}

	/**
	 * Gets and returns xAxisRange
	 * 
	 * @return the xAxisRange
	 */
	public double getxAxisRange() {
		return xAxisRange;
	}

	/**
	 * Gets and returns yAxisBase
	 * 
	 * @return the yAxisBase
	 */
	public double getyAxisBase() {
		return yAxisBase;
	}

	/**
	 * Gets and returns yAxisLabel
	 * 
	 * @return the yAxisLabel
	 */
	public String getyAxisLabel() {
		return yAxisLabel;
	}

	/**
	 * Gets and returns yAxisRange
	 * 
	 * @return the yAxisRange
	 */
	public double getyAxisRange() {
		return yAxisRange;
	}

	/**
	 * Sets the chartTitle
	 * 
	 * @param t
	 *            the chartTitle to set
	 */
	public void setChartTitle(String t) {
		chartTitle = t;
	}

	/**
	 * Sets the xAxisBase
	 * 
	 * @param x
	 *            the xAxisBase to set
	 */
	public void setxAxisBase(double x) {
		xAxisBase = x;
	}

	/**
	 * Sets the xAxisLabel
	 * 
	 * @param x
	 *            the xAxisLabel to set
	 */
	public void setxAxisLabel(String x) {
		xAxisLabel = x;
	}

	/**
	 * Sets the xAxisRange
	 * 
	 * @param x
	 *            the xAxisRange to set
	 */
	public void setxAxisRange(double x) {
		xAxisRange = x;
	}

	/**
	 * Sets the yAxisBase
	 * 
	 * @param y
	 *            the yAxisBase to set
	 */
	public void setyAxisBase(double y) {
		yAxisBase = y;
	}

	/**
	 * Sets the yAxisLabel
	 * 
	 * @param y
	 *            the yAxisLabel to set
	 */
	public void setyAxisLabel(String y) {
		yAxisLabel = y;
	}

	/**
	 * Sets the yAxisRange
	 * 
	 * @param y
	 *            the yAxisRange to set
	 */
	public void setyAxisRange(double y) {
		yAxisRange = y;
	}
}
