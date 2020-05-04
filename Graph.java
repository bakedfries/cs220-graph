package graph.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;

/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */
public class Graph implements IGraph{
	public Map<String, INode> node = new HashMap<>();
    
    /**
     * Return the {@link Node} with the given name.
     * 
     * If no {@link Node} with the given name exists, create
     * a new node with the given name and return it. Subsequent
     * calls to this method with the same name should
     * then return the node just created.
     * 
     * @param name
     * @return
     */
    public INode getOrCreateNode(String name) {
    	node.putIfAbsent(name, new Node(name));
    	return node.get(name);
    }
    
    public boolean containsNode(String name) {
    	return (node.containsKey(name));
    }

    public Collection<INode> getAllNodes() {
    	return node.values();
    }

    //The pseudocode from the slides was an amazing reference. Just wonderful.
    public void breadthFirstSearch(String startNodeName, NodeVisitor v){
    	Set<INode> visited = new HashSet<INode>(); //visited = new set
    	Queue<INode> toVisit = new LinkedList<>(); //toVisit = new queue
    	toVisit.add(node.get(startNodeName)); //enque(start node)
    	
    	while(toVisit.size() != 0) { 
    		INode temp = toVisit.remove(); //creating a temp to later check if the specific node has been visited or not
    		if (visited.contains(temp));
    		//do nothing? something?continue loop
    		v.visit(temp);
    		INode a = toVisit.remove();
    		visited.add(a);
    		for(INode pointer:temp.getNeighbors()) { //checking the adjacent nodes and enqueuing them if they have not been accessed yet
    			if(!visited.contains(pointer))
    				toVisit.add(pointer);
    		}	
    	}
    }
    
    //start with a stack not a queue and the neighbors of the neighbors are accessed first
    public void depthFirstSearch(String startNodeName, NodeVisitor v){
    	Set<INode> visited = new HashSet<INode>(); //visited = new set
    	Stack<INode> toVisit = new Stack<>(); //toVisit = new queue
    	toVisit.push(node.get(startNodeName)); //enqueue(start node)
    	
    	while(toVisit.isEmpty()) { 
    		INode temp = toVisit.pop(); //creating a temp to later check if the specific node has been visited or not
    		if (visited.contains(temp))
    			continue;
    		v.visit(temp);
    		INode a = toVisit.pop();
    		visited.add(a);
    		
    		for(INode pointer:temp.getNeighbors()) { //checking the adjacent nodes and enqueuing them if they have not been accessed yet
    			if(!visited.contains(pointer))
    				toVisit.push(pointer);
    		}
    			
    	}
    }

    //helper class Path
    class Path implements Comparable <Path>{
		String dest;
		int cost;
		
		public Path(String dest, int cost) {
			this.dest = dest;
			this.cost = cost;	
		}
		public INode getNode() { //returns current node from where it begins
			return node.get(this.dest);
		}
		
		public int getWeight() { //returns the current cost
			return this.cost;
		}
		
		@Override
		public int compareTo(Path o) {
			return this.cost - o.cost;
		}
	}
   public Map<INode,Integer> dijkstra(String startName){
	   Map <INode, Integer> result = new HashMap<INode, Integer>();
	   PriorityQueue<Path> todo = new PriorityQueue<>();
	   todo.add(new Path(startName,0));
	   
	   while (this.getAllNodes().size()> result.size()) {
		   Path tempPath = todo.poll();
		   INode tempNode = node.get(tempPath.dest);
		   if(result.containsKey(tempNode))
			   continue;
		   int cost = tempPath.getWeight();
		   result.put(tempNode, cost);
		   for(INode i : tempNode.getNeighbors())
			   todo.add(new Path(i.getName(),cost + tempNode.getWeight(i)));
	   }
	   return result;
   }  
    
   //helper class
   class Prim implements Comparable <Prim>{
		String start;
		String dest;
		int cost;
		
		public Prim(String start,String dest, int cost) {
			this.start = start;
			this.dest = dest;
			this.cost = cost;	
		}
		
		public INode getNode() { //returns current node from where it begins
			return node.get(this.dest);
		}
		
		public int getWeight() { //returns the current cost
			return this.cost;
		}
		
		@Override
		public int compareTo(Prim o) {
			return this.cost - o.cost;
		}
   }
   
	
    public IGraph primJarnik() {
    	PriorityQueue<Prim> todo = new PriorityQueue<>();
    	IGraph g = new Graph();
		INode s = this.getAllNodes().iterator().next();
		Prim p = todo.poll();
		//for each loop to iterate and add stuff to the priority queue
		for (INode i:s.getNeighbors()) 
			todo.add(new Prim(s.getName(),i.getName(),i.getWeight(s)));
		//graph size should not be equal to reference node sizes for the loop to work
		while(g.getAllNodes().size() != this.getAllNodes().size()) {
			if(g.containsNode(p.dest)) //priority queue
				continue;
			INode dest = g.getOrCreateNode(p.dest);
			INode start = g.getOrCreateNode(p.start);
			start.addUndirectedEdgeToNode(dest, p.cost); //updating the edges
			//creating a Node object that takes account of the edges to be added to the priority queue
			INode addStuff = node.get(p.dest);
			for(INode i: addStuff.getNeighbors())
				todo.add(new Prim(addStuff.getName(), i.getName(),i.getWeight(addStuff)));
				}
		return g;
	}
    
}
