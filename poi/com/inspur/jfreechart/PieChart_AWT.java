package com.inspur.jfreechart;


import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

public class PieChart_AWT extends ApplicationFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PieChart_AWT(String title) {
		super(title);
		// TODO Auto-generated constructor stub
		setContentPane(createDemoPanel());
	}

	public static JPanel createDemoPanel() {
		// TODO Auto-generated method stub
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	private static JFreeChart createChart(PieDataset dataset) {
		// TODO Auto-generated method stub
		JFreeChart chart = ChartFactory.createPieChart(      
		         "Mobile Sales",  // chart title 
		         dataset,        // data    
		         true,           // include legend   
		         true, 
		         false);

		      return chart;

	}

	private static PieDataset createDataset() {
		// TODO Auto-generated method stub
		DefaultPieDataset dataset = new DefaultPieDataset( );
	      dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
	      dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
	      dataset.setValue( "MotoG" , new Double( 40 ) );    
	      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
	      return dataset; 
	}

}
