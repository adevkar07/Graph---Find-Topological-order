/*
 * Team : 
 * Abhilasha Devkar : acd170130
 * Rashmi Priya 	: rxp170021
 * 
 * 
 * 
 * SP4 : Find Topological Sort in Directed Acyclic Graph
 * */


package acd170130;

import java.util.*;
import acd170130.Graph.Vertex;
import acd170130.Graph.Edge;
import acd170130.Graph.GraphAlgorithm;
import acd170130.Graph.Factory;
import acd170130.Graph.AdjList;


import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * This is DFS class which is used to find 
 * if graph is acyclic and to get the DFS order 
 * for given input graph.
 */
public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	static private int pre[]; 		
	static private int post[];		
	static private int time;		
	Stack<Vertex> TopoStack;		
	
    public static class DFSVertex implements Factory
    {
    	int cno;
    	public DFSVertex(Vertex u) 
    	{
    	}
    	public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    /**
     * This is DFS Constructor
     * @param time : Setting timer value to 1.
     * @param pre :  Array for keeping track of Start time for any vertex
     * @param post : Array for keeping track of Start time for any vertex
     * @param TopoStack : Stack for Topological Sort
     **/
    public DFS(Graph g)
    {
		super(g, new DFSVertex(null));
		time = 1;
		pre = new int[this.g.n+1];
		post = new int[this.g.n+1];
		TopoStack = new Stack<>();
    }

    /**
     * This method is used to detect if cycle is present in the given
     * graph or not.
     * @param Graph : Input Graph 
     * @return Null if cycle is found else DFS graph.
     */
    public  DFS depthFirstSearch(Graph g)
    { 	
    	Vertex[] vlist =  g.getVertexArray();
    	for (Vertex v: vlist) 
    	{
    	    if (pre[v.name] == 0)
    	     {
    	    	 if (depthFirstSearchUtil(v) == true) {
    	    		 System.out.println("Cycle is present, so can not find topological order.");
    	    		 return null;	 
    	    	 }
    	     }
    	}	
	return new DFS(g);
    }
    
    /**
     * This is a helper function to determine if graph 
     * is cyclic or acyclic.
     * Updates Topological stack till graph is found to
     * be acyclic.
     * @param u : Stating vertex.
     * @return True if graph is cyclic else False.
     */
    private boolean depthFirstSearchUtil(Vertex u) {     	
    	pre[u.name] = time; 	
    	time++;
    	AdjList al = this.g.adj(u);
    	List<Edge> oE = al.outEdges;
    	for (Edge v : oE) 
    	{  
    		if (post[v.to.name] == 0) {    		
	            if (pre[v.to.name] > 0 && post[v.to.name] == 0)    
	            {
	            	return true;
	            }
	            else
	            {
	            	if(depthFirstSearchUtil(v.to))
	            		return true;
	            }
    	    }
        } 
    	post[u.name] = time;
    	TopoStack.push(u);
    	time++;
	return false;
    }
    
    /**
     * Member function to find topological order. 
     * It goes through the Topological stack and 
     * updates the List of Topological Order.    
     * @return List of Topological order.
     */
    public List<Vertex> topologicalOrder1() {
    	DFS d = depthFirstSearch(g);
    	if (d == null)
    		return null;
    	List<Vertex> l = new ArrayList<>();
	
    	while (!TopoStack.isEmpty())
    	{
    		l.add(TopoStack.peek());
    		TopoStack.pop();	
    	}   	    	
	return l;
    }
    
    /**
     * Finds topological oder of a DAG using DFS. 
     * @return Returns null if g is not a DAG.
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
		DFS d = new DFS(g);
		return d.topologicalOrder1();
    }
    
    /**
     * Member Function to print Topological order.
     */ 
    public static void printTopologicalOrder1(List<Vertex> order) {
    	if(order!=null)
    	{
        	System.out.print("Topological Order: ");
        	for(Vertex v : order)
        	{
        		System.out.print(v.name + " ");
        	}
    	}
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }
    
    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
	return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
	return get(u).cno;
    }
    
    
    /**
     * Main Function
     */
    public static void main(String[] args) throws Exception {
    	List<Vertex> topologicalOrder = new ArrayList<>();
		String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1";
	    //String string = "7 6   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   6 7 1";
	
		Scanner in;
		
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		
		// Read graph from input
	    Graph g = Graph.readDirectedGraph(in);
		g.printGraph(false);
		
		// Create instance of DFS
	    DFS d = new DFS(g);	
	    
	    // Find the Topological Order
	    topologicalOrder = d.topologicalOrder1();
	    
	    // Print the Topological Order
	    printTopologicalOrder1(topologicalOrder);
    }
}

