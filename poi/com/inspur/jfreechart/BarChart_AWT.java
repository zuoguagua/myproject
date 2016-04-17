package com.inspur.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChart_AWT extends ApplicationFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BarChart_AWT(String applicationTitle,String chartTitle) {
		super(applicationTitle);
		// TODO Auto-generated constructor stub
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Category", "Score", createDataset(), PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset() {
		// TODO Auto-generated method stub
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
	
}
