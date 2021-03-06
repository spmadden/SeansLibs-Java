/*
 * LineChartTest.java
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
package com.seanmadden.charting.tests;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JFrame;

import com.seanmadden.charting.DefaultChartSeries;
import com.seanmadden.charting.LineChartPanel;

/**
 * @author sean.madden
 * 
 */
public final class LineChartTest {

	/**
	 * Make me a LineChartTest
	 */
	private LineChartTest() {
	}

	/**
	 * YAY MAIN
	 * 
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Frame!");
		frame.setLayout(new BorderLayout());
		final LineChartPanel pan = new LineChartPanel();

		frame.add(pan, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);

		final DefaultChartSeries series = new DefaultChartSeries("Test Stream");
		pan.setSeries(series);
		final Thread thread = new Thread() {
			@Override
			public void run() {
				Random rnd = new Random();
				boolean run = true;
				int xpos = 0;
				while (run) {
					double val = rnd.nextDouble() * rnd.nextInt(100);
					series.addPoint(xpos++, val);
					System.out.println("Adding " + val);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						run = false;
					}
				}
			}

		};
		thread.start();

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {
				thread.interrupt();
			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowOpened(WindowEvent e) {

			}
		});

	}
}
