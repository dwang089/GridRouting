package edu.tamu.dwang089;

import java.util.Map;
import java.util.TreeMap;

/**
 * This class contains caching information about the neighboring node
 * in each neighboring zone
 * 
 * @author Daoqi Wang
 *
 */

public class Table {
	Map<Integer, Node> nextNodes;
	
	public Table() {
		nextNodes = new TreeMap<>();
	}

	public void addEntry(int gridId, Node node) {
		if (nextNodes.containsKey(gridId)) {
			System.out.println("Next node for grid " + gridId + " already exists");
			return;
		}
		
		nextNodes.put(gridId, node);
	}
	
	public Node getNextNode(int gridId) {
		Node node = nextNodes.get(gridId);
		return node;
	}
}
