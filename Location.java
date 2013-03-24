package edu.tamu.dwang089;

/**
 * This class represents the location in the network
 * 
 * @author Daoqi Wang
 *
 */
public class Location {
	private double x;
	private double y;
	
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double distance(Location loc) {
		return Math.sqrt(Math.pow((loc.x - x), 2) + Math.pow((loc.y - y), 2));
	}
	
	public String toString() {
		String string = "(" + x
				+ ", " + y + ")";
		return string;
	}
}
