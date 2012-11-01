package Test;

import java.util.ArrayList;

import Model.FeatureCalc;
import Model.Point;

import junit.framework.TestCase;

public class FeatureCalcTest extends TestCase {

	FeatureCalc feature;
	ArrayList<Point> sig;
	ArrayList<Point> sig2;
	
	protected void setUp() throws Exception {
		feature = new FeatureCalc();
		
		//test sig 1
		sig = new ArrayList<Point>();
		sig.add(new Point(0, 500, 500, 1));
		sig.add(new Point(1, 520, 485, 1));
		sig.add(new Point(2, 545, 460, 0));
		sig.add(new Point(3, 570, 440, 0));
		sig.add(new Point(4, 600, 405, 1));
		sig.add(new Point(5, 650, 375, 1));
		sig.add(new Point(6, 680, 340, 1));
		sig.add(new Point(7, 750, 300, 1));
		sig.add(new Point(8, 760, 270, 0));
		sig.add(new Point(9, 800, 220, 1));
		sig.add(new Point(10, 840, 200, 1));
		
		//test sig 2
		sig2 = new ArrayList<Point>();
		sig2.add(new Point(0, 500, 500, 1));
		sig2.add(new Point(1, 520, 485, 1));
		sig2.add(new Point(2, 545, 460, 0));
		sig2.add(new Point(3, 570, 440, 0));
		sig2.add(new Point(4, 600, 405, 1));
		sig2.add(new Point(5, 520, 375, 1));
		sig2.add(new Point(6, 455, 340, 1));
		sig2.add(new Point(7, 400, 300, 0));
		sig2.add(new Point(8, 345, 390, 1));
		sig2.add(new Point(9, 290, 520, 0));
		sig2.add(new Point(10, 110, 700, 1));
	}

	public void testCalcSD() {
		double[] test = {265.44, 2295.221, 93.994};
		double x = feature.SD(test);
		
		assertTrue(x > 999.7 && x < 999.8);
	}

	public void testCalcMean() {
		double[] test = {265.44, 2295.221, 93.994};
		double x = feature.mean(test);
		
		assertTrue(x > 884.88 && x < 884.89);
	}

	public void testCalcTotalTime() {
		int s1 = feature.calcTotalTime(sig);
		int s2 = feature.calcTotalTime(sig2);
		
		assertTrue(s1 == 55);
		assertTrue(s2 == 55);
	}

	public void testCalcPenUpTime() {
		double s1 = feature.calcPenUpTime(sig);
		double s2 = feature.calcPenUpTime(sig2);
		
		assertTrue(s1 == 15);
		assertTrue(s2 == 20);
	}

	public void testCalcNoPenUps() {
		int s1 = feature.calcNoPenUps(sig);
		int s2 = feature.calcNoPenUps(sig2);
		
		assertTrue(s1 == 2);
		assertTrue(s2 == 3);
	}
	
	public void testCalcTotalDistance() {
		double s1 = feature.calcTotalLength(sig);
		double s2 = feature.calcTotalLength(sig2);
		
		assertTrue(s1 > 463.8 && s1 < 463.9);
		assertTrue(s2 > 866.9 && s2 < 867.0);
	}
	
	public void testCalcAverageVel() {
		double s1 = feature.calcAverageVel(sig, 2);
		double s2 = feature.calcAverageVel(sig2, 2);
		
		assertTrue(s1 > 40 && s1 < 41);
		assertTrue(s2 > 53 && s2 < 54);
	}
	
	public void testCalcMinimumVel() {
		double s1 = feature.calcMinVel(sig, 2);
		double s2 = feature.calcMinVel(sig2, 2);
		
		assertTrue(s1 > 30 && s1 < 31);
		assertTrue(s2 > 30 && s2 < 31);
	}
	
	public void testCalcMaximumVel() {
		double s1 = feature.calcMaxVel(sig, 2);
		double s2 = feature.calcMaxVel(sig2, 2);
		
		assertTrue(s1 > 62 && s1 < 63);
		assertTrue(s2 > 122 && s2 < 132);
	}
	
	public void testCalcAverageAccel() {
		double s1 = feature.calcAverageAccel(sig, 2);
		double s2 = feature.calcAverageAccel(sig2, 2);
		
		assertTrue(s1 > 2 && s1 < 3);
		assertTrue(s2 > 3 && s2 < 4);
	}
	
	public void testCalcMinimumAccel() {
		double s1 = feature.calcMinAccel(sig, 2);
		double s2 = feature.calcMinAccel(sig2, 2);
		
		assertTrue(s1 > 0.8 && s1 < 0.9);
		assertTrue(s2 > 9.5 && s2 < 9.6);
	}
	
	public void testCalcMaximumAccel() {
		double s1 = feature.calcMaxVel(sig, 2);
		double s2 = feature.calcMaxVel(sig2, 2);
		
		assertTrue(s1 > 62.4 && s1 < 62.6);
		assertTrue(s2 > 122.9 && s2 < 123.1);
	}

}
