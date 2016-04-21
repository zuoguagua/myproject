package com.inspur.jfreechart;

import java.io.File;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYLineChart {
	public static XYDataset XYLineChartDataset(){
		
		final XYSeries firefox = new XYSeries( "Firefox" );
	    firefox.add( 1.0 , 1.0 );
	    firefox.add( 2.0 , 4.0 );
	    firefox.add( 3.0 , 3.0 );
	    final XYSeries chrome = new XYSeries( "Chrome" );
	    chrome.add( 1.0 , 4.0 );
	    chrome.add( 2.0 , 5.0 );
	    chrome.add( 3.0 , 6.0 );
	    final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
	    iexplorer.add( 3.0 , 4.0 );
	    iexplorer.add( 4.0 , 5.0 );
	    iexplorer.add( 5.0 , 4.0 );
	    final XYSeriesCollection dataset = new XYSeriesCollection( );
	    dataset.addSeries( firefox );
	    dataset.addSeries( chrome );
	    dataset.addSeries( iexplorer );
		return dataset;
	}
	
	public static void JFreeChartDemoJPEG() throws Exception{
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
		         "Browser usage statastics", 
		         "Category",
		         "Score", 
		         XYLineChartDataset(),
		         PlotOrientation.VERTICAL, 
		         true, true, false);
		      
		      int width = 640; /* Width of the image */
		      int height = 480; /* Height of the image */ 
		      File XYChart = new File( "XYLineChart.jpeg" ); 
		      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
	}
	
	public static void JFreeChartDemoPng() throws Exception{
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
		         "Browser usage statastics", 
		         "Category",
		         "Score", 
		         XYLineChartDataset(),
		         PlotOrientation.VERTICAL, 
		         true, true, false);
		      
		      int width = 640; /* Width of the image */
		      int height = 480; /* Height of the image */ 
		      File XYChart = new File( "XYLineChart.png" ); 
		      ChartUtilities.saveChartAsPNG( XYChart, xylineChart, width, height);
	}
}
