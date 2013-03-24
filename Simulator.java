package edu.tamu.dwang089;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the simulator. It contains file setup
 * and statistics collection.
 * 
 * @author Daoqi Wang
 *
 */
public class Simulator {
	
	public static void main(String[] args) {
		collectStatistics();
	}
	
	//grid size 3, 4, 5
	//num nodes 50, 100, 200
	//neighbor distance 20, 30
	//level 2, 3
	public static List<List<Integer>> run() {
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		
		Network network = new Network();
		network.setWidth(100.0);
		network.setHeight(100.0);
		
		network.generateRandomNodes(50);
		network.generateGrids(4, 4);
		network.initializeGrids();
		network.initializeNeighbors();
		network.initializeTables();
		
		//setUpFile();
		
		network.printGrids();
		network.printNodes();
		
		Routing routing = new Routing(network);
		int source = 0;
		int dest = 49;
		
		List<Integer> gf = routing.GFRouting(source, dest);
		//List<Integer> grid = routing.GridRouting(source, dest);
		List<Integer> depth = routing.GFDepthRouting(source, dest, 2);
		
		//System.out.println(gf);
		//System.out.println(grid);
		//System.out.println(depth);
		
		results.add(gf);
		//results.add(grid);
		results.add(depth);
		
		return results;
	}
	
	public static void setUpFile() {
		File file = new File("test.txt");  
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
		PrintStream out = new PrintStream(stream);  
		System.setOut(out); 	
	}
	
	private void collectStatistics() {
		//collecting statistics
		int grid = 0;
		int multiHop = 0;
		int numValid = 0; // number of valid solutions
		int firstSuccess = 0;
		int secondSuccess = 0;
				
		//comparing two schemes ex: GF vs grid
		for (int i = 0; i < 100; i++) {
			List<List<Integer>> results = run();
			List<Integer> one = results.get(0);
			List<Integer> two = results.get(1);
					
			if (one.get(0) != 0 && two.get(0) != 0) {
				//0: numHops
				//1: totalDistance
				//2: numGeoCalculations
				//3: numBroadcastMessages
				first += one.get(0);
				second += two.get(0);
				num++;
							
				firstSuccess++;
				secondSuccess++;
			}
					
			else if (one.get(0) != 0 && two.get(0) == 0)
				firstSuccess++;
					
			else if (one.get(0) == 0 && two.get(0) != 0)
				secondSuccess++;
		}
				
		System.out.println();
		System.out.println("first: " + (double) first / num);
		System.out.println("second: " + (double) second / num);
		System.out.println("first success: " + firstSuccess);
		System.out.println("second success: " + secondSuccess);
				
		//for (List<Integer> list : results) 
		//	System.out.println(list);						
	}
}
