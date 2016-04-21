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
		
		
		LineChart_AWT lineChart = new LineChart_AWT("School Vs Year", "Number of Schools vs years");
		lineChart.pack();
		RefineryUtilities.centerFrameOnScreen(lineChart);
		lineChart.setVisible(true);
		
		
		
		XYLineChart_AWT xylineChart = new XYLineChart_AWT("Browser Usage Statistcs","Which Browser are you using?");
		xylineChart.pack();
		RefineryUtilities.centerFrameOnScreen(xylineChart);
		xylineChart.setVisible(true);
		
		
		
		//PieChart jpegDemo = new PieChart();
		//jpegDemo.JFreeChartDemoJPEG();
		
		//PieChart pngDemo = new PieChart();
		//pngDemo.JFreeChartDemoPNG();
		
		//BarChart jpegDemo = new BarChart();
		//jpegDemo.JFreeChartBarJPEG();
		
		//BarChart pngDemo = new BarChart();
		//pngDemo.JFreeChartBarPNG();
		
		//LineChart jpegDemo = new LineChart();
		//jpegDemo.JFreeChartLineJPEG();
		
		//LineChart pngDemo = new LineChart();
		//pngDemo.JFreeChartLinePNG();
		
		//XYLineChart jpegDemo = new XYLineChart();
		//jpegDemo.JFreeChartDemoJPEG();
		
		//XYLineChart pngDemo = new XYLineChart();
		//pngDemo.JFreeChartDemoPng();
		
		//PieChart3D jpegDemo = new PieChart3D();
		//jpegDemo.JFreeChartDemoJPEG();
		
		//PieChart3D pngDemo = new PieChart3D();
		//pngDemo.JFreeChartDemoPNG();
		
		
		BarChart3D jpegDemo = new BarChart3D();
		jpegDemo.BarChart3DJPEG();
		
		BarChart3D pngDemo = new BarChart3D();
		pngDemo.BarChart3DPNG();
		
		
		
	}
	
	

}
