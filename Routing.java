package edu.tamu.dwang089;

import java.util.ArrayList;
import java.util.List;

public class Routing {
	private Network network;
	
	public Routing(Network n) {
		network = n;
	}
	
	public List<Integer> GFRouting(int source, int dest) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("GFRouting...");
		
		//for statistics collection
		List<Integer> results = new ArrayList<Integer>();
		
		int i = 0;
		Node sourceNode = network.getNode(source);
		Node destNode = network.getNode(dest);
		
		if (sourceNode == null || destNode == null) {
			System.out.println("invalid source or dest node");
			results.clear();
			results.add(0);
			return results;
		}
		
		//performance metrics
		int numHops = 0;
		double totalDistance = 0;
		int numGeoCalculations = 0;
		int numBroadcastMessages = 0;
		
		Node currentNode = sourceNode;
		double currentDistance = currentNode.getLocation().distance(destNode.getLocation());
		
		while (currentNode != destNode) {
			System.out.println("Current node: " + currentNode);
			System.out.println("Current distance: " + currentDistance);
			
			double tempDistance = currentDistance;
			Node tempNode = currentNode;
			List<Node> neighbors = currentNode.getNeighbors();
			
			for (Node node : neighbors) {
				double distance = node.getLocation().distance(destNode.getLocation());
				numBroadcastMessages += 2;
				numGeoCalculations++;
				if (distance < tempDistance) {
					tempDistance = distance;
					tempNode = node;
				}
			}
			
			if (tempNode == currentNode) {
				System.out.println("Can't reach destination");
				results.clear();
				results.add(0);
				return results;
			}
			
			
			System.out.println("here " + i);
			i++;
			numHops++;
			totalDistance += tempNode.getLocation().distance(currentNode.getLocation());
			
			currentNode = tempNode;
			currentDistance = tempDistance;
			
			System.out.println("Next node: " + currentNode);
			System.out.println("Next distance: " + currentDistance);
			System.out.println();
			
		}
		
		System.out.println();
		System.out.println("number of hops: " + numHops);
		System.out.println("total distance: " + totalDistance);
		System.out.println("number of geo calculations: " + numGeoCalculations);
		System.out.println("number of broadcast messages: " + numBroadcastMessages);
		
		results.add(numHops);
		results.add((int) totalDistance);
		results.add(numGeoCalculations);
		results.add(numBroadcastMessages);
		
		return results;
	}
	
	public List<Integer> GridRouting(int source, int dest) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Grid Routing...");
		
		List<Integer> results = new ArrayList<Integer>();
		
		Node sourceNode = network.getNode(source);
		Node destNode = network.getNode(dest);
		
		if (sourceNode == null || destNode == null) {
			System.out.println("invalid source or dest node");
			results.clear();
			results.add(0);
			return results;
		}
		
		double totalDistance = 0;
		int numHops = 0;
		int numGeoCalculations = 0;
		int numBroadcastMessages = 0;
		
		Node currentNode = sourceNode;
		Grid currentGrid = currentNode.getGrid();
		Grid destGrid = destNode.getGrid();
		
		if (currentGrid == null || destGrid == null) {
			System.out.println("invalid grid id");
			results.clear();
			results.add(0);
			return results;
		}
		
		double currentGridDistance = currentGrid.getCenter().distance(destGrid.getCenter());
		
		while (currentGrid != destGrid) {
			System.out.println("Current node: " + currentNode);
			System.out.println("Current grid: " + currentGrid.getId());
			
			double tempDistance = currentGridDistance;
			Grid tempGrid = currentGrid;
			List<Grid> neighbors = currentGrid.getNeighbors();
			
			for (Grid nextGrid : neighbors) {
				double distance = nextGrid.getCenter().distance(destGrid.getCenter());
				numBroadcastMessages += 2;
				numGeoCalculations++;
				if (distance < tempDistance) {
					tempDistance = distance;
					tempGrid = nextGrid;
				}
			}
			
			if (tempGrid == currentGrid) {
				System.out.println("Can't reach destination with grids");
				results.clear();
				results.add(0);
				return results;
			}
			
			currentGrid = tempGrid;
			currentGridDistance = tempDistance;
			
			while (currentNode.getGrid() != currentGrid) {
				Node node = currentNode.getTable().getNextNode(currentGrid.getId());
				
				if (currentNode == node) {
					System.out.println("Can't reach destination with nodes");
					results.clear();
					results.add(0);
					return results;
				}
				
				totalDistance += node.getLocation().distance(currentNode.getLocation());
				numHops++;
				currentNode = node;
				System.out.println("Next node: " + currentNode);
			}
			
			System.out.println("Next grid: " + currentGrid.getId());
			System.out.println();
		}
		
		System.out.println();
		System.out.println("number of hops: " + numHops);
		System.out.println("total distance: " + totalDistance);
		System.out.println("number of geo calculations: " + numGeoCalculations);
		System.out.println("number of broadcast messages: " + numBroadcastMessages);
		
		results.add(numHops);
		results.add((int) totalDistance);
		results.add(numGeoCalculations);
		results.add(numBroadcastMessages);
		
		List<Integer> tempResults = GFRouting(currentNode.getId(), destNode.getId());
		for (int i = 0; i < tempResults.size(); i++) {
			int temp = results.get(i);
			temp += tempResults.get(i);
			results.set(i, temp);
		}
		
		return results;
	}
	
	public List<Integer> GFDepthRouting(int source, int dest, int depth) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("GFDepthRouting...");
		
		List<Integer> results = new ArrayList<Integer>();
		
		Node sourceNode = network.getNode(source);
		Node destNode = network.getNode(dest);
		
		if (sourceNode == null || destNode == null) {
			System.out.println("invalid source or dest node");
			results.clear();
			results.add(0);
			return results;
		}
		
		double totalDistance = 0;
		int numHops = 0;
		int numGeoCalculations = 0;
		int numBroadcastMessages = 0;
		
		Node currentNode = sourceNode;
		double currentDistance = currentNode.getLocation().distance(destNode.getLocation());
		
		while (currentNode != destNode) {
			System.out.println("Current node: " + currentNode);
			System.out.println("Current distance: " + currentDistance);
			
			int n = depth - 1;
			
			List<Node> tempList = currentNode.getNeighbors();
			List<Node> neighbors = new ArrayList<Node>();
			for (Node node : tempList)
				neighbors.add(node);

			int start = 0;
			int end = neighbors.size();
			
			for (Node node : neighbors)
				System.out.println("n = " + n + ", " + node);
			System.out.println();
			
			while (n > 0) {
				n--;
				List<Node> next = new ArrayList<Node>();
				
				for (int i = start; i < end; i++) {
					Node temp = neighbors.get(i);
					System.out.println("temp: " + temp);
					next.add(temp);
					
					for (Node node : next) {
						List<Node> nextNodes = node.getNeighbors();
						for (Node nextNode : nextNodes) {
							numBroadcastMessages += 2;
							if (!neighbors.contains(nextNode) && currentNode != nextNode) {
								neighbors.add(nextNode);
								System.out.println("n = " + n + ", " + nextNode);
							}
						}
					}
				}
				
				System.out.println();
				
				start = end;
				end = neighbors.size();
			}
			
			double tempDistance = currentDistance;
			Node tempNode = currentNode;
			
			for (Node node : neighbors) {
				System.out.println("list: " + node);
				double distance = node.getLocation().distance(destNode.getLocation());
				numGeoCalculations++;
				if (distance < tempDistance) {
					tempDistance = distance;
					tempNode = node;
				}
			}
			
			if (tempNode == currentNode) {
				System.out.println("Can't reach destination");
				results.clear();
				results.add(0);
				return results;
			}
			
			System.out.println("tempNode: " + tempNode);
			System.out.println("currentNode: " + currentNode);
			
			List<Integer> tempResults = GFRouting(currentNode.getId(), tempNode.getId());
			if (tempResults.get(0) == 0) {
				results.clear();
				results.add(0);
				return results;
			}
			
			numHops += tempResults.get(0);
			totalDistance += tempResults.get(1);
			numGeoCalculations += tempResults.get(2);
			numBroadcastMessages += tempResults.get(3);
			
			System.out.println();
			
			currentNode = tempNode;
			currentDistance = tempDistance;
			
			System.out.println("Next node: " + currentNode);
			System.out.println("Next distance: " + currentDistance);
			System.out.println();
			
		}
		
		results.add(numHops);
		results.add((int) totalDistance);
		results.add(numGeoCalculations);
		results.add(numBroadcastMessages);
		
		System.out.println();
		System.out.println("number of nodes: " + numHops);
		System.out.println("total distance: " + totalDistance);
		System.out.println("number of geo calculations: " + numGeoCalculations);
		System.out.println("number of broadcast messages: " + numBroadcastMessages);
		
		return results;
	}
}
