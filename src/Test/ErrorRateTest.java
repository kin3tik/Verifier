/**
 * Used to quickly test the FAR and FRR of the system.
 * Only works when given a directory that contains (only) the sig files from the database. 
 */

package Test;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Model.FileHandler;
import Model.FeatureCalc;
import Model.Point;

public class ErrorRateTest {
	
	//directory containing sig files
	File testDir = new File("C:/Users/James/Desktop/sigs/");
	
	public static void main(String[] args) {
		ErrorRateTest er = new ErrorRateTest();
		FileHandler file = new FileHandler();
		FeatureCalc feature = new FeatureCalc();
		DecimalFormat df = new DecimalFormat("#.##");
		int filesTested;
		int totalSigs;
		int falseFalse = 0, falseTrue = 0, trueTrue = 0, trueFalse = 0;
		double far;
		double frr;
		
		String[] fileNames = er.testDir.list();
		filesTested = fileNames.length;
		
		for(String fileName: fileNames) {
			System.out.println(er.testDir+"/"+fileName);
			
			ArrayList<ArrayList<Point>> refSigList = file.getTestRefSigList(er.testDir+"/"+fileName);
			ArrayList<ArrayList<Point>> realSigList = file.getTestRealSigList(er.testDir+"/"+fileName);
			ArrayList<ArrayList<Point>> fakeSigList = file.getTestFakeSigList(er.testDir+"/"+fileName);

			ArrayList<ArrayList<Double>> refSig = feature.calcReferenceSig(refSigList);
			ArrayList<Double> testSig;
			
//			System.out.println("\nref WED: \t"+refSig.get(2).get(0));
//			System.out.println("real mean WED: \t"+feature.meanWED(refSig, realSigList));
//			System.out.println("fake mean WED: \t"+feature.meanWED(refSig, fakeSigList));

//			System.out.println("----fake----");
			for(ArrayList<Point> sig: fakeSigList) {
				testSig = feature.calcTestSig(sig);
				boolean real = feature.verifySig(refSig, testSig);
				if(real) {
					//sig is real but should be fake. falseTrue++
					falseTrue++;
				} else {
					//sig is fake and should be fake. falseFalse++
					falseFalse++;
				}
			}
//			System.out.println("----genuine----");
			for(ArrayList<Point> sig: realSigList) {
				testSig = feature.calcTestSig(sig);
				boolean real = feature.verifySig(refSig, testSig);
				if(real) {
					//sig is real and should be real. trueTrue++
					trueTrue++;
				} else {
					//sig is fake but should be real. trueFalse++
					trueFalse++;
				}
			}
		}
		
		//calculate rates
		//FAR = False Acceptance Rate = Is forged but accepted as real
		far = ((double)falseTrue/(falseTrue+falseFalse));
		//FRR = False Rejection Rate = Is genuine but declined as forged
		frr = ((double)trueFalse/(trueFalse+trueTrue));
		totalSigs = falseTrue + falseFalse + trueFalse + trueTrue;
		
		System.out.println("*******************************");
		System.out.println("Total Files Tested: "+filesTested);
		System.out.println("Total Signatures: "+totalSigs);
		System.out.println("Total Falsely Accepted: "+falseTrue+" / "+(falseTrue+falseFalse));
		System.out.println("Total Falsely Declined: "+trueFalse+" / "+(trueFalse+trueTrue));
		System.out.println("FAR: "+df.format(far*100)+"%");
		System.out.println("FRR: "+df.format(frr*100)+"%");
		
	}

}
