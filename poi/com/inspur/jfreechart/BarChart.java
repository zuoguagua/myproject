package com.inspur.jfreechart;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class BarChart {
	public static  CategoryDataset barDataSet(){
		final String fiat = "FIAT";
	    final String audi = "AUDI";
	    final String ford = "FORD";
	    final String speed = "Speed";
	    final String millage = "Millage";
	    final String userrating = "User Rating";
	    final String safety = "safety";
	    final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

	    dataset.addValue( 1.0 , fiat , speed );
	    dataset.addValue( 3.0 , fiat , userrating );
	    dataset.addValue( 5.0 , fiat , millage );
	    dataset.addValue( 5.0 , fiat , safety );

	    dataset.addValue( 5.0 , audi , speed );
	    dataset.addValue( 6.0 , audi , userrating );
	    dataset.addValue( 10.0 , audi , millage );
	    dataset.addValue( 4.0 , audi , safety );

	    dataset.addValue( 4.0 , ford , speed );
	    dataset.addValue( 2.0 , ford , userrating );
	    dataset.addValue( 3.0 , ford , millage );
	    dataset.addValue( 6.0 , ford , safety );
	      
	    return dataset;
	}
	
	public static void JFreeChartBarJPEG() throws Exception{
		JFreeChart barChart = ChartFactory.createBarChart("CAR USAGE STATIStICS", "Category", "Score",barDataSet(), PlotOrientation.VERTICAL, true, true, false);
		int width = 640; /* Width of the image */
	    int height = 480; /* Height of the image */ 
	    File BarChart = new File( "BarChart.jpeg" );
	    ChartUtilities.saveChartAsJPEG(BarChart, barChart, width, height);
	}
	
	public static void JFreeChartBarPNG() throws Exception{
		JFreeChart barChart = ChartFactory.createBarChart("CAR USAGE STATIStICS", "Category", "Score",barDataSet(), PlotOrientation.VERTICAL, true, true, false);
		int width = 640; /* Width of the image */
	    int height = 480; /* Height of the image */ 
	    File BarChart = new File( "BarChart.png" );
	    ChartUtilities.saveChartAsPNG(BarChart, barChart, width, height);
	}
}
