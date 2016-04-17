package com.inspur.jfreechart;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart {
	public static PieDataset pieDataSet(){
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double( 20 ) );
	    dataset.setValue("SamSung Grand", new Double( 20 ) );
	    dataset.setValue("MotoG", new Double( 40 ) );
	    dataset.setValue("Nokia Lumia", new Double( 10 ) );
	    return dataset;
	}
	
	public static void JFreeChartDemoJPEG() throws Exception{
		JFreeChart chart = ChartFactory.createPieChart("Mobile Sales", pieDataSet(), true, true, false);
		int width = 640;
		int height = 480;
		File pieChart = new File("PieChart.jpeg");
		ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
	}
	
	public static void JFreeChartDemoPNG() throws Exception{
		JFreeChart chart = ChartFactory.createPieChart("Mobile Sales", pieDataSet(), true, true, false);
		int width = 640;
		int height = 480;
		File pieChart = new File("pieChart.png");
		ChartUtilities.saveChartAsPNG(pieChart, chart, width, height);
	}
}
