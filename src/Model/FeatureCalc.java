package Model;

import java.util.ArrayList;

public class FeatureCalc {
	
	/**
	 * Calculates the weighted euclidean distance between a reference signature and a test signature
	 * @param refSig - list containing a means list and a standard deviations list of the reference signature
	 * @param testSig - list containing means of the test signature
	 * @return wed - weighted euclidean distance
	 */
	public double calcWED(ArrayList<ArrayList<Double>> refSig, ArrayList<Double> testSig) {
		//G(T) = (1/n)*sqrt(sum(((t-r)/s)^2))
		ArrayList<Double> refMeans = refSig.get(0);
		ArrayList<Double> refSD = refSig.get(1);
		ArrayList<Double> testMeans = testSig;
		int features = refMeans.size();
		
		//sum(((t-r)/s)^2)
		double sum = 0;
		for(int i=0; i<features; i++) {
			Double n = Math.pow(((testMeans.get(i)-refMeans.get(i))/refSD.get(i)), 2);
			if(n>0 && !n.isInfinite())
				sum += n;
		}
		
		//(1/n)*sqrt(sum)
		double wed = (1.0/features)*Math.sqrt(sum);
		
		return wed;
	}
	
	/**
	 * Returns a test signature. Given a signature, will return a list of calculated feature values
	 * @param testSig - a single signature to compare to reference signature
	 * @return testSigFeatures - a list containing calculated feature values (of test sig)
	 */
	public ArrayList<Double> calcTestSig(ArrayList<Point> testSig) {
		ArrayList<Double> featureList = new ArrayList<Double>();
		
		//calc features and add to list
		featureList.add((double) this.calcTotalTime(testSig));
		featureList.add((double) this.calcPenUpTime(testSig));
		featureList.add((double) this.calcNoPenUps(testSig));
		featureList.add((double) this.calcTotalLength(testSig));
		//x axis
		featureList.add((double) this.calcAverageVel(testSig, 0));
		featureList.add((double) this.calcMinVel(testSig, 0));
		featureList.add((double) this.calcMaxVel(testSig, 0));
		featureList.add((double) this.calcAverageAccel(testSig, 0));
		featureList.add((double) this.calcMinAccel(testSig, 0));
		featureList.add((double) this.calcMaxAccel(testSig, 0));
		//y axis
		featureList.add((double) this.calcAverageVel(testSig, 1));
		featureList.add((double) this.calcMinVel(testSig, 1));
		featureList.add((double) this.calcMaxVel(testSig, 1));
		featureList.add((double) this.calcAverageAccel(testSig, 1));
		featureList.add((double) this.calcMinAccel(testSig, 1));
		featureList.add((double) this.calcMaxAccel(testSig, 1));
		//both axis
		featureList.add((double) this.calcAverageVel(testSig, 2));
		featureList.add((double) this.calcMinVel(testSig, 2));
		featureList.add((double) this.calcMaxVel(testSig, 2));
		featureList.add((double) this.calcAverageAccel(testSig, 2));
		featureList.add((double) this.calcMinAccel(testSig, 2));
		featureList.add((double) this.calcMaxAccel(testSig, 2));
		
		return featureList;
		
	}
	
	/**
	 * Returns a reference signature. A list of signatures is parsed, calculating the feature means, feature standard
	 * deviations and the weighted euclidean distance of the signatures that make up the reference signature. 
	 * The reference signature consists of a list, containing three separate lists (one for feature means,
	 * one for standard deviations of the means, and one that just contains a wed value).
	 * @param sigList - A list containing multiple signatures (1...*)
	 * @return referenceList - List containing a feature means list, a standard deviations list, wed/wed sd, e.g.
	 * 			List{ Means{45, ..., 56} SD{ 2, ..., 4} WED{ 4.05, 0.94 } }
	 */
	public ArrayList<ArrayList<Double>> calcReferenceSig(ArrayList<ArrayList<Point>> sigList) {
		ArrayList<ArrayList<Double>> refSig = new ArrayList<ArrayList<Double>>();
		
		//returns ArrayList containing: list of means (r1, r2, ..., rn)
		//								list of sd (s1, s2, ..., sn)
		ArrayList<Double> meanList = new ArrayList<Double>();
		ArrayList<Double> sdList = new ArrayList<Double>();
		ArrayList<Double> wed = new ArrayList<Double>();
		
		//create result lists for each feature
		int n = sigList.size();
		int[] ttResults = new int[n];
		int[] putResults = new int[n];
		int[] npuResults = new int[n];
		double[] tlResults = new double[n];
		//x axis
		double[] avxResults = new double[n];
		double[] minvxResults = new double[n];
		double[] maxvxResults = new double[n];
		double[] aaxResults = new double[n];
		double[] minaxResults = new double[n];
		double[] maxaxResults = new double[n];
		//y axis
		double[] avyResults = new double[n];
		double[] minvyResults = new double[n];
		double[] maxvyResults = new double[n];
		double[] aayResults = new double[n];
		double[] minayResults = new double[n];
		double[] maxayResults = new double[n];
		//both axis
		double[] avbResults = new double[n];
		double[] minvbResults = new double[n];
		double[] maxvbResults = new double[n];
		double[] aabResults = new double[n];
		double[] minabResults = new double[n];
		double[] maxabResults = new double[n];
		
		//calculate features for the list of sigs
		for(int i=0; i<sigList.size(); i++) {
			ttResults[i] 	= this.calcTotalTime(sigList.get(i));
			putResults[i] 	= this.calcPenUpTime(sigList.get(i));
			npuResults[i] 	= this.calcNoPenUps(sigList.get(i));
			tlResults[i] 	= this.calcTotalLength(sigList.get(i));
			//x axis
			avxResults[i] 	= this.calcAverageVel(sigList.get(i), 0);
			minvxResults[i] = this.calcMinVel(sigList.get(i), 0);
			maxvxResults[i] = this.calcMaxVel(sigList.get(i), 0);
			aaxResults[i] 	= this.calcAverageAccel(sigList.get(i), 0);
			minaxResults[i] = this.calcMinAccel(sigList.get(i), 0);
			maxaxResults[i] = this.calcMaxAccel(sigList.get(i), 0);
			//y axis
			avyResults[i] 	= this.calcAverageVel(sigList.get(i), 1);
			minvyResults[i] = this.calcMinVel(sigList.get(i), 1);
			maxvyResults[i] = this.calcMaxVel(sigList.get(i), 1);
			aayResults[i] 	= this.calcAverageAccel(sigList.get(i), 1);
			minayResults[i] = this.calcMinAccel(sigList.get(i), 1);
			maxayResults[i] = this.calcMaxAccel(sigList.get(i), 1);
			//both axis
			avbResults[i] 	= this.calcAverageVel(sigList.get(i), 2);
			minvbResults[i] = this.calcMinVel(sigList.get(i), 2);
			maxvbResults[i] = this.calcMaxVel(sigList.get(i), 2);
			aabResults[i] 	= this.calcAverageAccel(sigList.get(i), 2);
			minabResults[i] = this.calcMinAccel(sigList.get(i), 2);
			maxabResults[i] = this.calcMaxAccel(sigList.get(i), 2);
		}
		
		//calculates means for each feature and add to list
		meanList.add(mean(ttResults));
		meanList.add(mean(putResults));
		meanList.add(mean(npuResults));
		meanList.add(mean(tlResults));
		//x axis
		meanList.add(mean(avxResults));
		meanList.add(mean(minvxResults));
		meanList.add(mean(maxvxResults));
		meanList.add(mean(aaxResults));
		meanList.add(mean(minaxResults));
		meanList.add(mean(maxaxResults));
		//y axis
		meanList.add(mean(avyResults));
		meanList.add(mean(minvyResults));
		meanList.add(mean(maxvyResults));
		meanList.add(mean(aayResults));
		meanList.add(mean(minayResults));
		meanList.add(mean(maxayResults));
		//both axis
		meanList.add(mean(avbResults));
		meanList.add(mean(minvbResults));
		meanList.add(mean(maxvbResults));
		meanList.add(mean(aabResults));
		meanList.add(mean(minabResults));
		meanList.add(mean(maxabResults));
		
		//calculates sd for each feature and add to list
		sdList.add(SD(ttResults));
		sdList.add(SD(putResults));
		sdList.add(SD(npuResults));
		sdList.add(SD(tlResults));
		//x axis
		sdList.add(SD(avxResults));
		sdList.add(SD(minvxResults));
		sdList.add(SD(maxvxResults));
		sdList.add(SD(aaxResults));
		sdList.add(SD(minaxResults));
		sdList.add(SD(maxaxResults));
		//y axis
		sdList.add(SD(avyResults));
		sdList.add(SD(minvyResults));
		sdList.add(SD(maxvyResults));
		sdList.add(SD(aayResults));
		sdList.add(SD(minayResults));
		sdList.add(SD(maxayResults));
		//both axis
		sdList.add(SD(avbResults));
		sdList.add(SD(minvbResults));
		sdList.add(SD(maxvbResults));
		sdList.add(SD(aabResults));
		sdList.add(SD(minabResults));
		sdList.add(SD(maxabResults));
		
		refSig.add(meanList);
		refSig.add(sdList);
		
		//wed
		double sum = 0;
		ArrayList<Double> sigFeatures;
		ArrayList<Double> refSigWEDsd = new ArrayList<Double>();
		for(ArrayList<Point> sig: sigList) {
			sigFeatures = this.calcTestSig(sig);
			double newWED = this.calcWED(refSig, sigFeatures);
			sum += newWED;
			refSigWEDsd.add(newWED);
		}
		wed.add(sum/sigList.size());
		wed.add(SD(refSigWEDsd));
		
		refSig.add(wed);
		
		return refSig;
	}
	
	//**********************************************************************************
	//				FEATURES
	//**********************************************************************************
	
	/**
	 * Returns total time taken to write the signature in ms (milliseconds)
	 * @param sig - a signature
	 * @return msTime - time taken in ms
	 */
	public int calcTotalTime(ArrayList<Point> sig) {
		int points = sig.size();
		int msTime = points*5; //assuming 200 points captured per second
		
		return msTime;
	}
	
	/**
	 * Returns total pen up time in ms (milliseconds)
	 * @param sig
	 * @return pen up time
	 */
	public int calcPenUpTime(ArrayList<Point> sig) {
		int count = 0;
		
		for(Point p: sig) {
			if(p.getP() == 0)
				count++;
		}
		
		return count*5;
	}
	
	/**
	 * Returns the number of times the pen was lifted
	 * @param sig
	 * @return times pen was lifted
	 */
	public int calcNoPenUps(ArrayList<Point> sig) {
		int penCount = 0;
		boolean penUp = false;
		
		for(Point p: sig) {
			if(penUp == false && p.getP() == 0) {
				//pen up start
				penUp = true;
				penCount++;
			} else if(penUp == true && p.getP() == 1) {
				//pen up end
				penUp = false;
			}
		}
		
		return penCount;
	}
	
	/**
	 * Returns the total line length of the signature
	 * @param sig
	 * @return length of the line
	 */
	public double calcTotalLength(ArrayList<Point> sig) {
		double distance = 0;
		int x1, x2;
		int y1, y2;
		
		for(int i=0; i<sig.size()-1; i++) {
			x1 = sig.get(i).getX();
			y1 = sig.get(i).getY();
			x2 = sig.get(i+1).getX();
			y2 = sig.get(i+1).getY();
			
			distance += Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
		}
		
		return distance;
	}
	
	/**
	 * Returns the average velocity of movement in the direction specified by param choice.
	 * choice = 0 : velocity in x axis
	 * choice = 1 : velocity in y axis
	 * choice = 2 : velocity in both axis
	 * @param sig, choice
	 * @return average velocity
	 */
	public double calcAverageVel(ArrayList<Point> sig, int choice)  {		
		double velSum = 0;		
		ArrayList<Double> velList = getVelList(sig, choice);
		
		for(Double d: velList) {
			velSum += d;
		}
		
		double velAvg = (velSum/(sig.size()-2));
		
		return Math.abs(velAvg);
	}
	
	/**
	 * Returns the minimum velocity of movement in the direction specified by param choice.
	 * choice = 0 : velocity in x axis
	 * choice = 1 : velocity in y axis
	 * choice = 2 : velocity in both axis
	 * @param sig, choice
	 * @return minimum velocity
	 */
	public double calcMinVel(ArrayList<Point> sig, int choice) {
		double velMin = -1;		
		ArrayList<Double> velList = getVelList(sig, choice);
		
		for(Double vel: velList) {
			//check if velMin needs to be changed
			if(velMin == -1) {
				velMin = vel;
			} else {
				if(vel < velMin)
					velMin = vel;
			}
		}
		
		return Math.abs(velMin);
	}
	
	/**
	 * Returns the maximum velocity of movement in the direction specified by param choice.
	 * choice = 0 : velocity in x axis
	 * choice = 1 : velocity in y axis
	 * choice = 2 : velocity in both axis
	 * @param sig, choice
	 * @return maximum velocity
	 */
	public double calcMaxVel(ArrayList<Point> sig, int choice) {
		double velMax = -1;
		ArrayList<Double> velList = getVelList(sig, choice);

		for(Double vel: velList) {
			if(velMax == -1) {
				velMax = vel;
			} else {
				if(vel > velMax)
					velMax = vel;
			}
		}

		return Math.abs(velMax);
	}
	
	/**
	 * Returns the average acceleration of movement in the direction specified by param choice.
	 * choice = 0 : acceleration in x axis
	 * choice = 1 : acceleration in y axis
	 * choice = 2 : acceleration in both axis
	 * @param sig, choice
	 * @return average acceleration
	 */
	public double calcAverageAccel(ArrayList<Point> sig, int choice)  {		
		double accelSum = 0;		
		ArrayList<Double> accelList = getAccelList(sig, choice);
		
		for(Double d: accelList) {
			accelSum += d;
		}
		
		double accelAvg = (accelSum/(sig.size()-2));
		
		return Math.abs(accelAvg);
	}
	
	/**
	 * Returns the minimum acceleration of movement in the direction specified by param choice.
	 * choice = 0 : acceleration in x axis
	 * choice = 1 : acceleration in y axis
	 * choice = 2 : acceleration in both axis
	 * @param sig, choice
	 * @return minimum acceleration
	 */
	public double calcMinAccel(ArrayList<Point> sig, int choice) {
		double accelMin = -1;		
		ArrayList<Double> accelList = getAccelList(sig, choice);
		
		for(Double accel: accelList) {
			//check if velMin needs to be changed
			if(accelMin == -1) {
				accelMin = accel;
			} else {
				if(accel < accelMin)
					accelMin = accel;
			}
		}
		
		return Math.abs(accelMin);
	}
	
	/**
	 * Returns the maximum acceleration of movement in the direction specified by param choice.
	 * choice = 0 : acceleration in x axis
	 * choice = 1 : acceleration in y axis
	 * choice = 2 : acceleration in both axis
	 * @param sig, choice
	 * @return maximum acceleration
	 */
	public double calcMaxAccel(ArrayList<Point> sig, int choice) {
		double accelMax = -1;
		ArrayList<Double> accelList = getAccelList(sig, choice);

		for(Double accel: accelList) {
			if(accelMax == -1) {
				accelMax = accel;
			} else {
				if(accel > accelMax)
					accelMax = accel;
			}
		}

		return Math.abs(accelMax);
	}
	
	//**********************************************************************************
	//				UTILITY
	//**********************************************************************************
	
	/**
	 * Returns a list of velocity values for the given signature in direction defined by param choice.
	 * Velocities are calculated using central differences (there will be 2 less points of data).
	 * @param sig
	 * @param choice
	 * @return velocity list
	 */
	private ArrayList<Double> getAccelList(ArrayList<Point> sig, int choice) {
		ArrayList<Double> accelList = new ArrayList<Double>();
		ArrayList<Double> velList = getVelList(sig, choice);
		double accel = 0;
		//double timeUnit = 200; //200ms
		
		// accel = vel / time
		for(int i=0; i< velList.size(); i++ ) {
			if(i != 0 && i < velList.size()-2) {
				//accel = veln+1 - veln-1 / tn+1 - tn-1
				if(velList.get(i+1) - velList.get(i-1) == 0) {
					accelList.add(0.0);
				} else {
					accel = (velList.get(i+1) - velList.get(i-1)) / ((i+1) - (i-1));
					accelList.add(accel);
				}
			}
		}
		
		return accelList;
	}
	
	/**
	 * Returns a list of acceleration values for the given signature in direction defined by param choice.
	 * Acceleration values are calculated using central differences (there will be 4 less points of data than sig).
	 * @param sig
	 * @param choice
	 * @return accelerations list
	 */
	private ArrayList<Double> getVelList(ArrayList<Point> sig, int choice) {
		ArrayList<Double> velList = new ArrayList<Double>();
		//double timeUnit = 200; //200ms
		double vel = 0;
		
		for(int i=0; i< sig.size(); i++ ) {
			if(i != 0 && i < sig.size()-2) {
				//velocity = distance/time = [x(n+1) - x(n-1)] / [t(n+1) - t(n-1)]
				switch(choice) {
				case 0:
					// X direction
					vel = ((double)sig.get(i+1).getX() - (double)sig.get(i-1).getX())/(sig.get(i+1).getT() - sig.get(i-1).getT());
				//  vel = xn+1 - xn-1 / tn+1 - tn-1
					break;
				case 1:
					// Y direction
					vel = ((double)sig.get(i+1).getY() - (double)sig.get(i-1).getY())/(sig.get(i+1).getT() - sig.get(i-1).getT());
					break;
				case 2:
					// both directions
					//	(t1, x1, y1), (t2, x2, y2)
					//	c^2 = a^2 + b^2
					//	dist = sqrt( (x2-x1)^2 + (y2-y1)^2 )
					//	vel = sqrt( (x2-x1)^2 + (y2-y1)^2 ) / (t2 - t1)
					double dist = Math.sqrt(Math.pow(sig.get(i+1).getX() - sig.get(i-1).getX(), 2) + Math.pow(sig.get(i+1).getY() - sig.get(i-1).getY(), 2));
					vel = dist / (sig.get(i+1).getT() - sig.get(i-1).getT());
					break;
				}
				velList.add(vel);
			}
		}
		
		return velList;
	}
	
	/**
	 * Returns a boolean (false = forgery, true = genuine) resulting from the comparison between a reference signature 'refSig'
	 * and a test signature 'testSig'.
	 * @param refSig
	 * @param testSig
	 * @return result - true if genuine, false if forgery
	 */
	public boolean verifySig(ArrayList<ArrayList<Double>> refSig, ArrayList<Double> testSig) {
		FeatureCalc features = new FeatureCalc();
		double wed = features.calcWED(refSig, testSig);
		double refwed = refSig.get(2).get(0);
		double refwedsd = refSig.get(2).get(1);
		if(wed > (refwed+(9*refwedsd))) {
			//System.out.println("fake");
			//4SD covers 99.994%
			return false;
		} else {
			//System.out.println("real");
			return true;
		}
	}
	
	/**
	 * Returns the mean WED between the reference signature 'refSig' and the given list of the signatures 'sigList'
	 * @param refSig
	 * @param sigList
	 * @return mean WED
	 */
	public double meanWED(ArrayList<ArrayList<Double>> refSig, ArrayList<ArrayList<Point>> sigList) {
		FeatureCalc features = new FeatureCalc();
		double sum = 0;
		ArrayList<Double> sigFeatures;
		for(ArrayList<Point> sig: sigList) {
			sigFeatures = features.calcTestSig(sig);
			sum += features.calcWED(refSig, sigFeatures);
		}
		return sum/sigList.size();
	}
	
	/**
	 * Calculates the standard deviation of a list of integers
	 * @param resultList - int[]
	 * @return sd - standard deviation
	 */
	public double SD(int[] resultList) {
		//calculate mean
		double mean = this.mean(resultList);
		
		//calculate sum((X-M)^2)
		double xmSum = 0;
		for(int i: resultList) {
			double a = i-mean;
			xmSum += a*a;		
		}
		
		//sd = sqrt((sum((x-m)^2)/n))
		double sd = Math.sqrt(xmSum/(resultList.length));
		
		return sd;
	}
	
	/**
	 * Calculates the standard deviation of a list of doubles
	 * @param resultList - double[]
	 * @return sd - standard deviation
	 */
	public double SD(double[] resultList) {
		//calculate mean
		double mean = this.mean(resultList);
		
		//calculate sum((X-M)^2)
		double xmSum = 0;
		for(double i: resultList) {
			double a = i-mean;
			xmSum += a*a;		
		}
		
		//sd = sqrt((sum((x-m)^2)/n))
		double sd = Math.sqrt(xmSum/(resultList.length));
		
		return sd;
	}

	/**
	 * Calculates the standard deviation of a list of doubles
	 * @param resultList - double[]
	 * @return sd - standard deviation
	 */
	public double SD(ArrayList<Double> resultList) {
		//calculate mean
		double mean = this.mean(resultList);
		
		//calculate sum((X-M)^2)
		double xmSum = 0;
		for(double i: resultList) {
			double a = i-mean;
			xmSum += a*a;		
		}
		
		//sd = sqrt((sum((x-m)^2)/n))
		double sd = Math.sqrt(xmSum/(resultList.size()));
		
		return sd;
	}
	
	/**
	 * Calculates the mean of a list of int values
	 * @param resultList
	 * @return mean - int
	 */
	public double mean(int[] resultList) {
		double mean = 0;
		double sum = 0;
		
		//calculate sum 
		for(int i: resultList) {
			sum += i;
		}
		
		//calculate mean
		mean = (sum/resultList.length);
		
		return mean;
	}
	
	/**
	 * Calculates the mean of a list of double values
	 * @param resultList
	 * @return mean - double
	 */
	public double mean(double[] resultList) {
		double mean = 0;
		double sum = 0;
		
		//calculate sum 
		for(double i: resultList) {
			sum += i;
		}
		
		//calculate mean
		mean = (sum/resultList.length);
		
		return mean;
	}
	
	/**
	 * Calculates the mean of a list of double values
	 * @param resultList
	 * @return mean - double
	 */
	public double mean(ArrayList<Double> resultList) {
		double mean = 0;
		double sum = 0;
		
		//calculate sum 
		for(double i: resultList) {
			sum += i;
		}
		
		//calculate mean
		mean = (sum/resultList.size());
		
		return mean;
	}
}
