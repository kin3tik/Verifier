package Model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import View.ProfileGraph;
import View.ShapeGraph;

public class Controller implements ActionListener{

	private View.GUI view;
	private static FileHandler file;
	private static FeatureCalc features;

	public Controller() {
		view = new View.GUI(this);
		file = new FileHandler();
		features = new FeatureCalc();
	}

	public static void main(String[] args) {
		Controller controller = new Controller();

		if(args.length == 2) {
			if(args[1].equalsIgnoreCase("-v")) {
				//verify file test
				//usage hsv.jar fileName -v
				String fileName = args[0];
				testVerif(fileName);
			} else {				
				//usage "hsv refSig testSig"
				String refSigFile = args[0];
				String testSigFile = args[1];

				ArrayList<ArrayList<Point>> refSigList = file.readSigFile(refSigFile);
				ArrayList<ArrayList<Point>> testSigList = file.readSigFile(testSigFile);

				System.out.println("Reference signature consists of "+refSigList.size()+" signatures.");
				System.out.println("Test signature consists of "+testSigList.size()+" signature.");

				ArrayList<ArrayList<Double>> refSig = features.calcReferenceSig(refSigList);
				ArrayList<Double> testSig = features.calcTestSig(testSigList.get(0));

				boolean real = features.verifySig(refSig, testSig);

				System.out.println("Reference Signature WED: "+refSig.get(2).get(0));
				System.out.println("Reference Signature WED Standard Deviation: "+refSig.get(2).get(1));
				System.out.println("WED(Reference, Test): "+features.meanWED(refSig, testSigList));
				
				if(real){
					System.out.println("\nGENUINE");
				} else {
					System.out.println("\nFAKE");
				}
			}

		} else if(args.length == 4 && args[1].equalsIgnoreCase("-c")) {
			//show comparison
			String fileName = args[0];
			boolean real = Boolean.parseBoolean(args[2].toLowerCase());
			int n = Integer.parseInt(args[3]);
			featureCompare(fileName, real, n);
		} else if(args.length == 0) {
			//launch GUI
			controller.view.setVisible(true);
		} else {
			System.out.println("Usage: hsv refSig.file testSig.file");
		}

	}

	//*********************************************************
	// ACTION HANDLER
	//*********************************************************

	/**
	 * Handles interaction with the GUI
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("Verify")) {
			String testSigFile = view.tfTestSig.getText();
			String refSigFile = view.tfRefSig.getText();

			ArrayList<ArrayList<Point>> refSigList = file.readSigFile(refSigFile);
			ArrayList<ArrayList<Point>> testSigList = file.readSigFile(testSigFile);

			ArrayList<ArrayList<Double>> refSig = features.calcReferenceSig(refSigList);
			ArrayList<Double> testSig = features.calcTestSig(testSigList.get(0));

			//update stats on view
			updateView(refSig, testSig);
			
			//*****************************************************************************************
			// add charts
			//*****************************************************************************************
			JFreeChart shapeChart = new ShapeGraph(testSigList.get(0)).getChart();
			shapeChart.removeLegend();
			ChartPanel p = new ChartPanel(shapeChart);
			p.setPreferredSize(view.pnlSigShape.getSize());
			view.pnlSigShape.add(p);

			JFreeChart profileChart = new ProfileGraph(testSigList.get(0)).getChart();
			profileChart.removeLegend();
			ChartPanel p2 = new ChartPanel(profileChart);
			p2.setPreferredSize(view.pnlSigProfile.getSize());
			view.pnlSigProfile.add(p2);
			
			//******************************************************************************************
			// logging for testing
			//******************************************************************************************
//			System.out.println("Reference signature consists of "+refSigList.size()+" signatures.");
//			System.out.println("Test signature consists of "+testSigList.size()+" signature.");
//
//			boolean real = verifySig(refSig, testSig);
//			if(real){
//				System.out.println("GENUINE");
//			} else {
//				System.out.println("FAKE");
//			}
//
//			System.out.println("Reference WED: "+refSig.get(2).get(0));
//			System.out.println("Reference WED SD: "+refSig.get(2).get(1));
//			System.out.println("Mean WED: "+meanWED(refSig, testSigList));
			//******************************************************************************************
		} 
	}

	//*********************************************************
	// UTILITIES
	//*********************************************************

	/**
	 * Updates GUI sig stats
	 * @param refSig
	 * @param testSig
	 */
	private void updateView(ArrayList<ArrayList<Double>> refSig, ArrayList<Double> testSig) {
		DecimalFormat df = new DecimalFormat("#.###");		
		ArrayList<Double> refMeans = refSig.get(0);
		ArrayList<Double> refSD = refSig.get(1);
		ArrayList<Double> refWED = refSig.get(2);
		double wed = features.calcWED(refSig, testSig);

		//update basic stats
		view.rTotalTime.setText(df.format(refMeans.get(0))+" ms");
		view.sdTotalTime.setText(df.format(refSD.get(0))+" ms");
		view.trTotalTime.setText(df.format(testSig.get(0))+" ms");

		view.rPenUpTime.setText(df.format(refMeans.get(1))+" ms");
		view.sdPenUpTime.setText(df.format(refSD.get(1))+" ms");
		view.trPenUpTime.setText(df.format(testSig.get(1))+" ms");

		view.rNoPenUps.setText(df.format(refMeans.get(2)));
		view.sdNoPenUps.setText(df.format(refSD.get(2)));
		view.trNumPenUps.setText(df.format(testSig.get(2)));

		view.rTotalLength.setText(df.format(refMeans.get(3)));
		view.sdTotalLength.setText(df.format(refSD.get(3)));
		view.trTotalLength.setText(df.format(testSig.get(3)));

		//update velocities
		view.rAvVelX.setText(df.format(refMeans.get(4)));
		view.sdAvVelX.setText(df.format(refSD.get(4)));
		view.trAvVelX.setText(df.format(testSig.get(4)));

		view.rMinVelX.setText(df.format(refMeans.get(5)));
		view.sdMinVelX.setText(df.format(refSD.get(5)));
		view.trMinVelX.setText(df.format(testSig.get(5)));

		view.rMaxVelX.setText(df.format(refMeans.get(6)));
		view.sdMaxVelX.setText(df.format(refSD.get(6)));
		view.trMaxVelX.setText(df.format(testSig.get(6)));

		view.rAvVelY.setText(df.format(refMeans.get(7)));
		view.sdAvVelY.setText(df.format(refSD.get(7)));
		view.trAvVelY.setText(df.format(testSig.get(7)));

		view.rMinVelY.setText(df.format(refMeans.get(8)));
		view.sdMinVelY.setText(df.format(refSD.get(8)));
		view.trMinVelY.setText(df.format(testSig.get(8)));

		view.rMaxVelY.setText(df.format(refMeans.get(9)));
		view.sdMaxVelY.setText(df.format(refSD.get(9)));
		view.trMaxVelY.setText(df.format(testSig.get(9)));

		view.rAvVelBoth.setText(df.format(refMeans.get(10)));
		view.sdAvVelBoth.setText(df.format(refSD.get(10)));
		view.trAvVelBoth.setText(df.format(testSig.get(10)));

		view.rMinVelBoth.setText(df.format(refMeans.get(11)));
		view.sdMinVelBoth.setText(df.format(refSD.get(11)));
		view.trMinVelBoth.setText(df.format(testSig.get(11)));

		view.rMaxVelBoth.setText(df.format(refMeans.get(12)));
		view.sdMaxVelBoth.setText(df.format(refSD.get(12)));
		view.trMaxVelBoth.setText(df.format(testSig.get(12)));

		//update accelerations
		view.rAvAccelX.setText(df.format(refMeans.get(13)));
		view.sdAvAccelX.setText(df.format(refSD.get(13)));
		view.trAvAccelX.setText(df.format(testSig.get(13)));

		view.rMinAccelX.setText(df.format(refMeans.get(14)));
		view.sdMinAccelX.setText(df.format(refSD.get(14)));
		view.trMinAccelX.setText(df.format(testSig.get(14)));

		view.rMaxAccelX.setText(df.format(refMeans.get(15)));
		view.sdMaxAccelX.setText(df.format(refSD.get(15)));
		view.trMaxAccelX.setText(df.format(testSig.get(15)));

		view.rAvAccelY.setText(df.format(refMeans.get(16)));
		view.sdAvAccelY.setText(df.format(refSD.get(16)));
		view.trAvAccelY.setText(df.format(testSig.get(16)));

		view.rMinAccelY.setText(df.format(refMeans.get(17)));
		view.sdMinAccelY.setText(df.format(refSD.get(17)));
		view.trMinAccelY.setText(df.format(testSig.get(17)));

		view.rMaxAccelY.setText(df.format(refMeans.get(18)));
		view.sdMaxAccelY.setText(df.format(refSD.get(18)));
		view.trMaxAccelY.setText(df.format(testSig.get(18)));

		view.rAvAccelBoth.setText(df.format(refMeans.get(19)));
		view.sdAvAccelBoth.setText(df.format(refSD.get(19)));
		view.trAvAccelBoth.setText(df.format(testSig.get(19)));

		view.rMinAccelBoth.setText(df.format(refMeans.get(20)));
		view.sdMinAccelBoth.setText(df.format(refSD.get(20)));
		view.trMinAccelBoth.setText(df.format(testSig.get(20)));

		view.rMaxAccelBoth.setText(df.format(refMeans.get(21)));
		view.sdMaxAccelBoth.setText(df.format(refSD.get(21)));
		view.trMaxAccelBoth.setText(df.format(testSig.get(21)));

		//update WED
		view.rWED.setText(df.format(refWED.get(0)));
		view.sdWED.setText(df.format(refWED.get(1)));
		view.trWED.setText(df.format(wed));

		//update result
		if(features.verifySig(refSig, testSig)) {
			//sig is genuine
			view.lblResult.setText("GENUINE");
			view.lblResult.setForeground(Color.GREEN);
		} else {
			//sig is forgery
			view.lblResult.setText("FORGERY");
			view.lblResult.setForeground(Color.RED);
		}
	}

	/**
	 * Used for testing. Compares the reference signature from fileName with a test signature
	 * defined by param real (real sig or forgery), and param n (sig no.)
	 * @param fileName
	 * @param real
	 * @param n
	 */
	private static void featureCompare(String fileName, boolean real, int n) {
		FileHandler file = new FileHandler();
		FeatureCalc features = new FeatureCalc();

		ArrayList<ArrayList<Point>> refSigList = file.getTestRefSigList(fileName);
		ArrayList<ArrayList<Point>> realSigList = file.getTestRealSigList(fileName);
		ArrayList<ArrayList<Point>> fakeSigList = file.getTestFakeSigList(fileName);

		ArrayList<ArrayList<Double>> refSig = features.calcReferenceSig(refSigList);
		ArrayList<Double> testSig;

		if(real) {
			//real sig as test
			testSig = features.calcTestSig(realSigList.get(n));
		} else {
			//fake sig as test
			testSig = features.calcTestSig(fakeSigList.get(0));
		}

		ArrayList<Double> refMeans = refSig.get(0);
		ArrayList<Double> refSD = refSig.get(1);
		String[] names = {"Total Time",
				"Pen Up Time",
				"No. Pen Ups",
				"Total Length",
				"Avg. Vel. X",
				"Min. Vel. X",
				"Max. Vel. X",
				"Avg. Vel. Y",
				"Min. Vel. Y",
				"Max. Vel. Y",
				"Avg. Vel. Both",
				"Min. Vel. Both",
				"Max. Vel. Both",
				"Avg. Accel. X",
				"Min. Accel. X",
				"Max. Accel. X",
				"Avg. Accel. Y",
				"Min. Accel. Y",
				"Max. Accel. Y",
				"Avg. Accel. Both",
				"Min. Accel. Both",
				"Max. Accel. Both"
		};
		for(int i=0; i<testSig.size(); i++) {
			System.out.println(names[i]+" || Mean Real: "+refMeans.get(i)+", Mean Test: "+testSig.get(i)+", SD: "+refSD.get(i));
		}

		System.out.println("\nref WED: \t"+refSig.get(2).get(0));
		System.out.println("real mean WED: \t"+features.meanWED(refSig, realSigList));
		System.out.println("fake mean WED: \t"+features.meanWED(refSig, fakeSigList));
	}

	/**
	 * Used for testing. Given a signature file from the database (contains 15+ genuine sigs and 5 forgeries), creates
	 * a reference sig from the first 10 signatures, then compares that to the remaining genuine and forged signatures 
	 * with results.
	 * @param fileName
	 */
	private static void testVerif(String fileName) {
		FileHandler file = new FileHandler();
		FeatureCalc features = new FeatureCalc();

		ArrayList<ArrayList<Point>> refSigList = file.getTestRefSigList(fileName);
		ArrayList<ArrayList<Point>> realSigList = file.getTestRealSigList(fileName);
		ArrayList<ArrayList<Point>> fakeSigList = file.getTestFakeSigList(fileName);

		ArrayList<ArrayList<Double>> refSig = features.calcReferenceSig(refSigList);
		ArrayList<Double> testSig;

		System.out.println("\nref WED: \t"+refSig.get(2).get(0));
		System.out.println("real mean WED: \t"+features.meanWED(refSig, realSigList));
		System.out.println("fake mean WED: \t"+features.meanWED(refSig, fakeSigList));
		
		boolean real;

		System.out.println("----fake----");
		for(ArrayList<Point> sig: fakeSigList) {
			testSig = features.calcTestSig(sig);
			 real = features.verifySig(refSig, testSig);
			 System.out.println(real);
		}
		System.out.println("----genuine----");
		for(ArrayList<Point> sig: realSigList) {
			testSig = features.calcTestSig(sig);
			real = features.verifySig(refSig, testSig);
			System.out.println(real);
		}
	}

}
