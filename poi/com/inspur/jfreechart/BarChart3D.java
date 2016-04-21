package com.inspur.jfreechart;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart3D {
	
	public static DefaultCategoryDataset BarChart3DDataset(){
		final String fait = "FAIT";              
	      final String audi = "AUDI";              
	      final String ford = "FORD";              
	      final String speed = "Speed";              
	      final String popular = "Popular";              
	      final String mailage = "Mailage";              
	      final String userrating = "User Rating";              
	      final String safty = "safty";        
	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( ); 

	      dataset.addValue( 1.0 , fait , speed );              
	      dataset.addValue( 4.0 , fait , popular );              
	      dataset.addValue( 3.0 , fait , userrating );              
	      dataset.addValue( 5.0 , fait , mailage );              
	      dataset.addValue( 5.0 , fait , safty );              
	      dataset.addValue( 5.0 , audi , speed );              
	      dataset.addValue( 7.0 , audi , popular );              
	      dataset.addValue( 6.0 , audi , userrating );              
	      dataset.addValue( 10.0 , audi , mailage );              
	      dataset.addValue( 4.0 , audi , safty ); 
	      dataset.addValue( 4.0 , ford , speed );              
	      dataset.addValue( 3.0 , ford , popular );              
	      dataset.addValue( 2.0 , ford , userrating );              
	      dataset.addValue( 3.0 , ford , mailage );              
	      dataset.addValue( 6.0 , ford , safty ); 
	      return dataset;
	}
	
	public static void BarChart3DJPEG() throws Exception{
		JFreeChart barChart = ChartFactory.createBarChart3D(
		         "Car Usage Statistics",             
		         "Category",             
		         "Score",             
		         BarChart3DDataset(),            
		         PlotOrientation.VERTICAL,             
		         true, true, false);
		         
		      int width = 640; /* Width of the image */              
		      int height = 480; /* Height of the image */                              
		      File barChart3D = new File( "barChart3D.jpeg" );                            
		      ChartUtilities.saveChartAsJPEG( barChart3D, barChart, width, height);
	}
	
	public static void BarChart3DPNG() throws Exception{
		JFreeChart barChart = ChartFactory.createBarChart3D(
		         "Car Usage Statistics",             
		         "Category",             
		         "Score",             
		         BarChart3DDataset(),            
		         PlotOrientation.VERTICAL,             
		         true, true, false);
		         
		      int width = 640; /* Width of the image */              
		      int height = 480; /* Height of the image */                              
		      File barChart3D = new File( "barChart3D.png" );                            
		      ChartUtilities.saveChartAsPNG( barChart3D, barChart, width, height);
	}
}
