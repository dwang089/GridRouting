package edu.tamu.dwang089;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	private Location center;
	private Location topLeft;
	private Location bottomRight;
	private List<Node> nodes;
	private List<Grid> neighbors;
	private int id;
	
	public Grid(Location upperLeft, Location lowerRight) {
		nodes = new ArrayList<>();
		neighbors = new ArrayList<>();
		topLeft = upperLeft;
		bottomRight = lowerRight;
	}
	
	public void setId(int num) {
		id = num;
	}
	
	public int getId() {
		return id;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Grid> getNeighbors() {
		return neighbors;
	}
	
	public void setCenter(Location location) {
		center = location;
	}
	
	public Location getCenter() {
		return center;
	}
	
	public boolean containsNode(Node node) {
		Location nodeLocation = node.getLocation();
		double x = nodeLocation.getX();
		double y = nodeLocation.getY();
		
		if (x < topLeft.getX() || x >= bottomRight.getX())
			return false;
		
		if (y < topLeft.getY() || y >= bottomRight.getY())
			return false;
		
		return true;
	}
	
	public boolean isNeighbor(Grid grid) {
		if (grid == this)
			return false;
		
		double centerDistance = center.distance(grid.center);
		double diagonalDistance = topLeft.distance(bottomRight);
		
		return centerDistance < 1.01 * diagonalDistance;
	}
	
	public String toString() {
		String string = "Grid: id = " + id
				+ ", topLeft = " + topLeft
				+ ", bottomRight = " + bottomRight
				+ ", center = " + center;
		
		return string;
	}
	
	public void print() {
		System.out.println(this);
		
		System.out.println("Neighbors: ");
		for (Grid grid : neighbors)
			System.out.println(grid);
		
		System.out.println("Nodes: ");
		for (Node node : nodes) {
			System.out.println(node);
		}
		
		System.out.println();
	}
}
