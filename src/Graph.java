// Created by Amaan Hafeez, 251298573, ahafeez7@uwo.ca
import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
    private int numNodes;
    /**
     * Creation of an adjacency list to store nodes and the edges
     * Arraylist structure has been chosen for its simplicity and ease of use
     */
    private ArrayList<GraphNode> nodes; // ArrayList to store nodes
    private ArrayList<ArrayList<GraphEdge>> adjList; // ArrayList of ArrayLists for adjacency list

    /**
     * Constructor for the graph object
     * populates the arraylist for the nodes
     * populates the arraylist for the edges with arraylists, so that edges can be added to the arraylist of each node
     * @param n
     */
    public Graph(int n) {
        this.numNodes = n;
        nodes = new ArrayList<>(numNodes);
        adjList = new ArrayList<>(numNodes);
        int i = 0;
        for (; i < numNodes; i++) {
            nodes.add(new GraphNode(i));
            adjList.add(new ArrayList<GraphEdge>());
        }
    }

    /**
     *
     * @param u a node u
     * @param v a node v
     * @param type the coins required to open a door, 0 for a corridor since no coins needed to pass
     * @param label the label of the edge, door or corridor
     * @throws GraphException Exception catches if given nodes do not exist or if edge is already a part of the grpah
     */
    public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException {
        // check if name exists or not, which is an integer
        if (u.getName() >= numNodes || v.getName() >= numNodes || u.getName() <0 || v.getName()<0){
            throw new GraphException("One or both nodes do no exist");
        }
        if (areAdjacent(u, v)) {
            throw new GraphException("Edge already exists");
        }
        GraphEdge newEdge = new GraphEdge(u, v, type, label);
        adjList.get(u.getName()).add(newEdge);
        if (!u.equals(v)) { // In case of undirected graph, add edge to both nodes' lists
            adjList.get(v.getName()).add(newEdge);
        }
    }

    /**
     *
     * @param u an integer u, which is a unique identifier of a particular node
     * @return a Graph node object
     * @throws GraphException in the case node does not exist within the graph object
     */
    public GraphNode getNode(int u) throws GraphException {
        if (u >= numNodes || u < 0) {
            throw new GraphException("Node does not exist");
        }
        return nodes.get(u);
    }

    /**
     *
     * @param u a node u, for which edges are incident on it
     * @return An iterator object that ensures the program can traverse through all possible edgees
     * @throws GraphException in the case the node u does not exist in the graph
     */
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        if (nodes.contains(u)) {
            return adjList.get(u.getName()).iterator();
        } else {
            throw new GraphException("Node does not exist in the graph");
        }
    }

    /**
     *
     * @param u a node u
     * @param v a node v
     * @return a graph edge object
     * @throws GraphException in the case edge does not exist for the two nodes
     */
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        if (nodes.contains(u) && nodes.contains(v)) {
            Iterator<GraphEdge> edges = incidentEdges(u);
            if (edges == null) {
                throw new GraphException("No edges do not exist for one of the nodes entered");
            }
            while (edges.hasNext()) {
                GraphEdge edge = edges.next();
                if (edge.firstEndpoint().equals(v) || edge.secondEndpoint().equals(v)) {
                    return edge;
                }
            }
        } else {
            throw new GraphException("One of the nodes entered does not exist in the graph");
        }
        throw new GraphException("Edge does not exist");
    }

    /**
     *
     * @param u a node u
     * @param v a node v
     * @return a boolean value indicating if nodes are adjacent or not
     * @throws GraphException
     */
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        try {
            getEdge(u, v); // Try to get the edge between u and v
            return true; // If an edge exists, then nodes are adjacent
        } catch (GraphException e) {
            return false; // If an exception is thrown, nodes are not adjacent
        }
    }
}
