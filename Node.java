package edu.tamu.dwang089;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents each node in the ad hoc wireless networks.
 * 
 * @author Daoqi Wang
 *
 */
public class Node {
	private Location position;
	private List<Node> neighbors;
	private Table table;
	private int id;
	private Grid grid;
	
	public Node(Location location) {
		position = location;
		neighbors = new ArrayList<>();
		table = new Table();
	}
	
	public void setId(int num) {
		id = num;
	}
	
	public int getId() {
		return id;
	}
	
	public void setGrid(Grid g) {
		grid = g;
	}
	
	public Grid getGrid() {
		return grid;
	}

	public Location getLocation() {
		return position;
	}
	
	public List<Node> getNeighbors() {
		return neighbors;
	}
	
	public boolean isNeighbor(Node node) {
		return (this != node && position.distance(node.getLocation()) < 20);
	}
	
	public Table getTable() {
		return table;
	}
	
	//information for grid routing
	public void initializeTable() {
		List<Grid> neighborGrids = grid.getNeighbors();
		for (Grid grid : neighborGrids) {
			double MinDistance = position.distance(grid.getCenter());
			Node next = this;
			for (Node node : neighbors) {
				double distance = node.position.distance(grid.getCenter());
				if (distance < MinDistance) {
					MinDistance = distance;
					next = node;
				}
			}
			table.addEntry(grid.getId(), next);
		}
	}
	
	public String toString() {
		String string = "Node: id = " + id
				+ ", grid = " + grid.getId()
				+ ", location = " + position;
		return string;
	}
	
	public void printNeighbors() {
		System.out.println(this);
		
		System.out.println("Neighbors: ");
		for (Node node : neighbors) 
			System.out.println(node);
	}
	
	public void printTable() {
		System.out.println("Table: ");
		
		for (Grid nextGrid : grid.getNeighbors()) {
			System.out.print("Grid = " + nextGrid.getId());
			System.out.println(" , next = " + table.getNextNode(nextGrid.getId()));
		}
	}
}
