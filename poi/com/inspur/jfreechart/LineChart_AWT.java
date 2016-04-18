package com.inspur.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class LineChart_AWT extends ApplicationFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	public LineChart_AWT(String applicationTitle,String chartTitle) {
		super(applicationTitle);
		// TODO Auto-generated constructor stub
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle,"Years","Number of Schools",createDataset(),PlotOrientation.VERTICAL,true,true,false);
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
		setContentPane(chartPanel);
	}

	private CategoryDataset createDataset() {
		// TODO Auto-generated method stub
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	    dataset.addValue( 15 , "schools" , "1970" );
	    dataset.addValue( 30 , "schools" , "1980" );
	    dataset.addValue( 60 , "schools" ,  "1990" );
	    dataset.addValue( 120 , "schools" , "2000" );
	    dataset.addValue( 240 , "schools" , "2010" );
	    dataset.addValue( 300 , "schools" , "2014" );
	    return dataset;
	}
	
	
}
