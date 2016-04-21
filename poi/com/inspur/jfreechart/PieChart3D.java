package com.inspur.jfreechart;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart3D {
	public static DefaultPieDataset PieChart3DDataset(){
		DefaultPieDataset dataset = new DefaultPieDataset( );             
	      dataset.setValue( "IPhone 5s" , new Double( 20 ) );             
	      dataset.setValue( "SamSung Grand" , new Double( 20 ) );             
	      dataset.setValue( "MotoG" , new Double( 40 ) );             
	      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );
	      return dataset;
	}
	      public static void JFreeChartDemoJPEG() throws Exception{
	    	  
	    	  JFreeChart chart = ChartFactory.createPieChart3D( 
	    		         "Mobile Sales" ,  // chart title                   
	    		         PieChart3DDataset() ,         // data 
	    		         true ,            // include legend                   
	    		         true, 
	    		         false);

	    	final PiePlot3D plot = ( PiePlot3D ) chart.getPlot( );             
	    	plot.setStartAngle( 270 );             
	    	plot.setForegroundAlpha( 0.60f );             
	    	plot.setInteriorGap( 0.02 );             
	    	int width = 640; /* Width of the image */             
	    	int height = 480; /* Height of the image */                             
	    	File pieChart3D = new File( "pie_Chart3D.jpeg" );                           
	    	ChartUtilities.saveChartAsJPEG( pieChart3D , chart , width , height );
	    	
	      }
	      public static void JFreeChartDemoPNG() throws Exception{
	    	  JFreeChart chart = ChartFactory.createPieChart3D( 
	    		         "Mobile Sales" ,  // chart title                   
	    		         PieChart3DDataset() ,         // data 
	    		         true ,            // include legend                   
	    		         true, 
	    		         false);

	    	final PiePlot3D plot = ( PiePlot3D ) chart.getPlot( );             
	    	plot.setStartAngle( 270 );             
	    	plot.setForegroundAlpha( 0.60f );             
	    	plot.setInteriorGap( 0.02 );             
	    	int width = 640; /* Width of the image */             
	    	int height = 480; /* Height of the image */                             
	    	File pieChart3D = new File( "pie_Chart3D.png" );                           
	    	ChartUtilities.saveChartAsPNG(pieChart3D, chart, width, height);
	      }
	
}
