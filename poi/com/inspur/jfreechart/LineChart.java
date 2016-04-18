package com.inspur.jfreechart;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart {
	public static DefaultCategoryDataset lineChartDataset(){
		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
	    line_chart_dataset.addValue( 15 , "schools" , "1970" );
	    line_chart_dataset.addValue( 30 , "schools" , "1980" );
	    line_chart_dataset.addValue( 60 , "schools" , "1990" );
	    line_chart_dataset.addValue( 120 , "schools" , "2000" );
	    line_chart_dataset.addValue( 240 , "schools" , "2010" ); 
	    line_chart_dataset.addValue( 300 , "schools" , "2014" );
	    return line_chart_dataset;
	}
	
	public static void JFreeChartLineJPEG() throws Exception{
		JFreeChart lineChartObject = ChartFactory.createLineChart("Schools Vs Years","Year","Schools Count",
		         lineChartDataset(),PlotOrientation.VERTICAL,true,true,false);
		int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      File lineChart = new File( "LineChart.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
	}
	
	public static void JFreeChartLinePNG() throws Exception{
		JFreeChart lineChartObject = ChartFactory.createLineChart("Schools Vs Years","Year","Schools Count",
		         lineChartDataset(),PlotOrientation.VERTICAL,true,true,false);
		int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      File lineChart = new File( "LineChart.png" ); 
	      ChartUtilities.saveChartAsPNG(lineChart ,lineChartObject, width ,height);
	}
	
}
