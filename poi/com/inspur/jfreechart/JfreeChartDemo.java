package com.inspur.jfreechart;

import org.jfree.ui.RefineryUtilities;

public class JfreeChartDemo {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		PieChart_AWT demo = new PieChart_AWT("Mobile Sales");
		demo.setSize(560, 367);
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
		
		
		BarChart_AWT chart = new BarChart_AWT("Car Usage Statistics","Which car do you like?");
		
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		
		
		//PieChart jpegDemo = new PieChart();
		//jpegDemo.JFreeChartDemoJPEG();
		
		//PieChart pngDemo = new PieChart();
		//pngDemo.JFreeChartDemoPNG();
		
		BarChart jpegDemo = new BarChart();
		jpegDemo.JFreeChartBarJPEG();
		
		BarChart pngDemo = new BarChart();
		pngDemo.JFreeChartBarPNG();
	}
	
	

}
