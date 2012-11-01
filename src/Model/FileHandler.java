package Model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileHandler {

	/**
	 * Returns an ArrayList containing the data points of each signature contained in fileName
	 * @param fileName
	 * @return sigList - list of a list (of signatures)
	 */
	public ArrayList<ArrayList<Point>> readSigFile(String fileName) {
		ArrayList<ArrayList<Point>> sigList = new ArrayList<ArrayList<Point>>();

		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			int sigLength;

			//-----------------------------------------------------
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				ArrayList<Point> sigPoints = new ArrayList<Point>();

				//split line by whitespace
				String[] strLineArray = strLine.split("\\s+");
				sigLength = Integer.parseInt(strLineArray[3]);
				
				//this point contains user ID and number of points in the sig
				//sigPoints.add(new Point(-1, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));

				//read in all points of the sig
				for(int i = 0; i < sigLength; i++) {
					strLineArray = br.readLine().split("\\s+");
					sigPoints.add(new Point(i, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));
				}

				// add the sig data to the list of sigs
				sigList.add(sigPoints);
			}
			in.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return sigList;
	}
	
	/**
	 * Used for testing. Returns the first 10 signatures in a file (to be used as a reference signature).
	 * @param fileName
	 * @return
	 */
	public ArrayList<ArrayList<Point>> getTestRefSigList(String fileName) {
		ArrayList<ArrayList<Point>> sigList = new ArrayList<ArrayList<Point>>();

		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			int sigLength;
			int counter = 0;

			//-----------------------------------------------------
			//Read File Line By Line
			while ((strLine = br.readLine()) != null && counter < 10)   {
				ArrayList<Point> sigPoints = new ArrayList<Point>();

				//split line by whitespace
				String[] strLineArray = strLine.split("\\s+");
				sigLength = Integer.parseInt(strLineArray[3]);
				
				//this point contains user ID and number of points in the sig
				//sigPoints.add(new Point(-1, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));

				//read in all points of the sig
				for(int i = 0; i < sigLength; i++) {
					strLineArray = br.readLine().split("\\s+");
					sigPoints.add(new Point(i, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));
				}

				// add the sig data to the list of sigs
				sigList.add(sigPoints);
				counter++;
			}
			in.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return sigList;
	}
	
	/**
	 * Used for testing. Returns a list of genuine signatures from a file (after the first 10 used for ref).
	 * @param fileName
	 * @return
	 */
	public ArrayList<ArrayList<Point>> getTestRealSigList(String fileName) {
		ArrayList<ArrayList<Point>> sigList = new ArrayList<ArrayList<Point>>();

		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			int sigLength;
			int counter = 0;

			//-----------------------------------------------------
			//Read File Line By Line
			while ((strLine = br.readLine()) != null && counter < 15)   {
				ArrayList<Point> sigPoints = new ArrayList<Point>();

				//split line by whitespace
				String[] strLineArray = strLine.split("\\s+");
				sigLength = Integer.parseInt(strLineArray[3]);
				
				//determine if current sig is real or forged
				boolean real;
				if (Integer.parseInt(strLineArray[2]) >> 63 != 0) {
					real = false; 
				} else {
					real = true;
				}

				//read in all points of the sig
				for(int i = 0; i < sigLength; i++) {
					strLineArray = br.readLine().split("\\s+");
					sigPoints.add(new Point(i, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));
				}

				// add the sig data to the list of sigs
				if(counter>=10 && real)
					sigList.add(sigPoints);
				counter++;
			}
			in.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return sigList;
	}
	
	/**
	 * Used for testing. Returns a list of forged signatures from a file.
	 * (Determined by the first line of a signature, second integer. if that int is -ve, then forged.
	 * @param fileName
	 * @return
	 */
	public ArrayList<ArrayList<Point>> getTestFakeSigList(String fileName) {
		ArrayList<ArrayList<Point>> sigList = new ArrayList<ArrayList<Point>>();

		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			int sigLength;

			//-----------------------------------------------------
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				ArrayList<Point> sigPoints = new ArrayList<Point>();

				//split line by whitespace
				String[] strLineArray = strLine.split("\\s+");
				sigLength = Integer.parseInt(strLineArray[3]);
				
				//determine if sig is real or forged
				boolean real;
				if (Integer.parseInt(strLineArray[2]) >> 63 != 0) {
					real = false; 
				} else {
					real = true;
				}

				//read in all points of the sig
				for(int i = 0; i < sigLength; i++) {
					strLineArray = br.readLine().split("\\s+");
					//if(counter>=15)
						sigPoints.add(new Point(i, Integer.parseInt(strLineArray[1]), Integer.parseInt(strLineArray[2]), Integer.parseInt(strLineArray[3])));
				}

				// add the sig data to the list of sigs
				if(!real)
					sigList.add(sigPoints);
			}
			in.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return sigList;
	}

}
