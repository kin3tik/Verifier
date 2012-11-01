package View;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

import Model.Point;

public class ProfileGraph extends JFrame{

	private static final long serialVersionUID = -3186565017479769303L;
	private ArrayList<Point> dataPoints;
	private JFreeChart chart;
	private int timeUnit;

	public ProfileGraph(ArrayList<Point> dataPoints) {
		super("Signature Profile");
		this.dataPoints = dataPoints;
		this.timeUnit = 5;

		this.chart = createChart(generateDataSet());
	}

	public JFreeChart getChart() {
		return this.chart;
	}

	private XYSeriesCollection generateDataSet() {
		XYSeries xSeries = new XYSeries("X location", false);
		XYSeries ySeries = new XYSeries("Y location", false);

		for(Point p: dataPoints) {
//			if(p.getP() != 0) {
				xSeries.add(p.getT()*timeUnit, p.getX());
				ySeries.add(p.getT()*timeUnit, p.getY());
//			}
		}

		XYSeriesCollection dataSet = new XYSeriesCollection();
		dataSet.addSeries(xSeries);
		dataSet.addSeries(ySeries);	

		return dataSet;
	}

	private JFreeChart createChart(XYSeriesCollection dataSet) {
		
		JFreeChart chart = ChartFactory.createXYLineChart(
//		JFreeChart chart = ChartFactory.createScatterPlot(
				"Signature Profile", 		// title
				"Time (ms)",						// x axis label
				"Pixel Position",			// y axis label
				dataSet,					// data
				PlotOrientation.VERTICAL,	// orientation
				true,						// legend
				true,						// tooltip
				false);						// url

		//A renderer that connects data points with natural cubic splines and/or draws shapes at each data point.
		//chart.getXYPlot().setRenderer(new XYSplineRenderer());
		
		return chart;
	}

}
