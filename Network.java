package edu.tamu.dwang089;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Network {
	private List<Node> nodes;
	private List<Grid> grids;
	private double width;
	private double height;
	private Random random;
	private static int nodeId;
	private static int gridId;
	
	public Network() {
		nodes = new ArrayList<>();
		grids = new ArrayList<>();
		random = new Random();
		nodeId = 0;
		gridId = 0;
	}
	
	public void generateNode(double x, double y) {
		if (x < 0 || x > width || y < 0 || y > height)
			return;
		
		Location location = new Location(x, y);
		Node node = new Node(location);
		
		node.setId(nodeId);
		nodeId++;
		nodes.add(node);
	}
	
	public void generateRandomNodes(int num) {
		nodes.clear();
		nodeId = 0;
		
		for (int i = 0; i < num; i++) {
			double x = width * random.nextDouble();
			double y = height * random.nextDouble();
			
			Location location = new Location(x, y);
			Node node = new Node(location);
			
			node.setId(nodeId);
			nodeId++;
			nodes.add(node);
		}
	}
	
	public void generateGrids(int x, int y) {
		grids.clear();
		gridId = 0;
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				double topX = (double)i / x * width;
				double topY = (double)j / y * height;
				double bottomX = ((double)i + 1) / x * width;
				double bottomY = ((double)j + 1) / y * height;
				Location topLeft = new Location(topX, topY);
				Location bottomRight = new Location(bottomX, bottomY);
				Grid grid = new Grid(topLeft, bottomRight);
				
				double centerX = (i + 0.5) / x * width;
				double centerY = (j + 0.5) / y * height;
				Location center = new Location(centerX, centerY);
				grid.setCenter(center);
				
				grid.setId(gridId);
				gridId++;
				grids.add(grid);
			}
		}
	}
	
	public void initializeGrids() {
		for (Grid grid : grids) {
			for (Node node : nodes) {
				if (grid.containsNode(node)) {
					node.setGrid(grid);
					grid.getNodes().add(node);
				}
			}
		}
	}
	
	public void initializeNeighbors() {
		for (Node node : nodes) {
			for (Node anotherNode : nodes) {
				if (node.isNeighbor(anotherNode))
					node.getNeighbors().add(anotherNode);
			}
		}
			
		for (Grid grid : grids) {
			for (Grid anotherGrid : grids) {
				if (grid.isNeighbor(anotherGrid))
					grid.getNeighbors().add(anotherGrid);
			}
		}
		
	}
	
	public void initializeTables() {
		for (Node node : nodes)
			node.initializeTable();
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getNode(int id) {
		for (Node node : nodes) {
			if (id == node.getId())
				return node;
		}
		
		return null;
	}
	
	public List<Grid> getGrids() {
		return grids;
	}
	
	public Grid getGrid(int id) {
		for (Grid grid : grids) {
			if (id == grid.getId())
				return grid;
		}
		
		return null;
	}
	
	public void setWidth(double w) {
		width = w;
	}
	
	public void setHeight(double h) {
		height = h;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void printGrids() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Printing grids...");
		
		for (Grid grid : grids)
			grid.print();
	}
	
	public void printNodes() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Printing nodes...");
		
		for (Node node : nodes) {
			node.printNeighbors();
			node.printTable();
			System.out.println();
		}
	}
}
