package View;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import Model.Point;

public class ShapeGraph extends JFrame{

	private static final long serialVersionUID = -6453293392949754320L;
	private ArrayList<Point> dataPoints;
	private JFreeChart chart;

	public ShapeGraph(ArrayList<Point> dataPoints) {
		super("Signature Shape");
		this.dataPoints = dataPoints;

		this.chart = createChart(generateDataSet());
	}

	public JFreeChart getChart() {
		return this.chart;
	}

	private XYSeriesCollection generateDataSet() {
		XYSeries dataSeries = new XYSeries("Pen Points", false);

		for(Point p: dataPoints) {
			if(p.getP() != 0) {
			dataSeries.add(p.getX(), p.getY());
			}
		}

		XYSeriesCollection dataSet = new XYSeriesCollection();
		dataSet.addSeries(dataSeries);	

		return dataSet;
	}

	private JFreeChart createChart(XYSeriesCollection dataSet) {

		JFreeChart chart = ChartFactory.createScatterPlot(
//		JFreeChart chart = ChartFactory.createXYLineChart(
				"Signature Shape",	 		// title
				"X Pen Position",			// x axis label
				"Y Pen Position",			// y axis label
				dataSet,					// data
				PlotOrientation.VERTICAL,	// orientation
				true,						// legend
				true,						// tooltip
				false);						// url
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		chart.getXYPlot().setRenderer(renderer);
		chart.getXYPlot().getRangeAxis().setInverted(true);
		//chart.getXYPlot().getDomainAxis().setInverted(true);

		return chart;
	}

}
