/* MST.java

   Minimum Spanning Tree algorithm
   
   The project is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.lang.*;

public class MST {

	static int[] parent;

	// Find set of vertex i 
	static int find(int i) 
	{ 
		while (parent[i] != i) 
			i = parent[i]; 
		return i; 
	} 
	
	// Does union of i and j. It returns 
	// false if i and j are already in same 
	// set. 
	static void union(int i, int j) 
	{ 
		int a = find(i); 
		int b = find(j); 
		parent[a] = b; 
	} 

    /* mst(adj)
       Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
       of all edges in a minimum spanning tree.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
    */
    static int mst(int[][][] adj) {

		int V = adj.length;
		parent = new int[V];
		int totalWeight = 0;

		// Initialize sets of disjoint sets. 
		for (int i = 0; i < V; i++) {
			parent[i] = i;
			//System.out.println(parent[i]); 
		}

		// Include minimum weight edges one by one 
    	int edge_count = 0;
		while (edge_count < V - 1) 
		{ 
			int min = Integer.MAX_VALUE, a = -1, b = -1; 
			for (int i = 0; i < V; i++) 
			{ 
				for (int j = 0; j < adj[i].length; j++)  
				{ 
					if (find(i) != find(adj[i][j][0]) && adj[i][j][1] < min)  
					{ 
						min = adj[i][j][1]; 
						a = i; 
						b = adj[i][j][0]; 
					} 
				} 
			} 
	
			union(a, b); 
			//System.out.printf("Edge %d:(%d, %d) cost:%d \n", 
			//	edge_count, a, b, min); 

			++edge_count; 
			totalWeight += min; 
		}

		return totalWeight;
		
    }


    public static void main(String[] args) {
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0) {
			//If a file argument was provided on the command line, read from the file
			try {
				s = new Scanner(new File(args[0]));
			}
			catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}
		else {
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
			
		//Read graphs until EOF is encountered (or an error occurs)
		while(true) {
			graphNum++;
			if(!s.hasNextInt()) {
				break;
			}
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();

			int[][][] adj = new int[n][][];
			
			
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++) {
				LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
				for (int j = 0; j < n && s.hasNextInt(); j++) {
					int weight = s.nextInt();
					if(weight > 0) {
						edgeList.add(new int[]{j, weight});
					}
					valuesRead++;
				}
				adj[i] = new int[edgeList.size()][2];
				Iterator it = edgeList.iterator();
				for(int k = 0; k < edgeList.size(); k++) {
					adj[i][k] = (int[]) it.next();
				}
			}
			if (valuesRead < n * n) {
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}

			// // output the adjacency list representation of the graph
			// for(int i = 0; i < n; i++) {
			// 	System.out.print(i + ": ");
			// 	for(int j = 0; j < adj[i].length; j++) {
			// 	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
			// 	}
			// 	System.out.print("\n");
			// }

			int totalWeight = mst(adj);
			System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

					
		}
    }

    
}
