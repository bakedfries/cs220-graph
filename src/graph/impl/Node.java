package graph.impl;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import graph.INode;

 //@author jspacco

public class Node implements INode{
	public String name; //node name
    public Map<INode, Integer> neighbors; //creating a new map from node to integer

    public Node(String name) { //works
    	this.name = name ;
    	neighbors = new HashMap<INode, Integer>();
    }
  
    public String getName() { //works, return the name
    	return this.name;
    }

    public Collection<INode> getNeighbors() {
    	return this.neighbors.keySet();
    }
    
    public void addDirectedEdgeToNode(INode n, int weight) {
    	neighbors.put(n,weight);
    }
   
    public void addUndirectedEdgeToNode(INode n, int weight) {
    	addDirectedEdgeToNode(n, weight);
    	n.addDirectedEdgeToNode(this, weight);
    }

    public void removeDirectedEdgeToNode(INode n) throws IllegalStateException{
    	if(!neighbors.containsKey(n))
    		throw new IllegalStateException();
    	neighbors.remove(n); //remove the key
    }

    public void removeUndirectedEdgeToNode(INode n) throws IllegalStateException{
    	if(!neighbors.containsKey(n))
    		throw new IllegalStateException();
    	removeDirectedEdgeToNode(n); //first remove the directed edges
    	n.removeDirectedEdgeToNode(this);//removes the reference too
    }
    
    public boolean hasEdge(INode other) {
    	/*if(other.getNeighbors()==null)
    		return false;
    	else return true;
    	
    	tweaked a little bit (which blew my own mind) so I HAD to include this awesome "transformation"
    	okay, no shame here because I am geeking out about being able to condense literally 3 lines of code to 1 what even is life at this point
    	*/
    	return(neighbors.containsKey(other)); //i'm way too happy about this line of code than i should
    }
    
    public int getWeight(INode n) throws IllegalStateException{
    	if (!neighbors.containsKey(n))
    		throw new IllegalStateException();
    	return neighbors.get(n); //reminder: the weights are the keys to their corresponding n(s)
    }
}
