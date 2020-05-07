package graph;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface INode
{
    String getName(); //method getname which might get the name
    
    Collection<INode> getNeighbors(); //collection type...hm...getsNeighbours 
    
    void addDirectedEdgeToNode(INode neighbor, int weight); //no public/private...adds directed edges to node
    
    void addUndirectedEdgeToNode(INode neighbor, int weight); //adds undirected edges to node
    
    void removeDirectedEdgeToNode(INode neighbor); //removes de
    
    void removeUndirectedEdgeToNode(INode neightbor); //removes ue
    
    boolean hasEdge(INode node); //to check if inode has edge or not
    
    int getWeight(INode node); //gets the weight of the node
    
    
}
    	    
