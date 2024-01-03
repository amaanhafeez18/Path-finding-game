// Created by Amaan Hafeez, 251298573, ahafeez7@uwo.ca
public class GraphEdge {
    /**
     * Instance variables
     */
    private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    /**
     * Constructor to create a graph edge object, used for inserting it into a graph
     *
     * @param u     A node u
     * @param v     A node v
     * @param type  the type represents the number of coins required to pass through this edge
     * @param label the label represents the type of edge, door or corridor
     */
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    /**
     *
     * @return a node u, representing the start of the edge
     */
    public GraphNode firstEndpoint() {
        return u;
    }

    /**
     *
     * @return a node u, representing the end of the edge
     */
    public GraphNode secondEndpoint() {
        return v;
    }

    /**
     *
     * @return the type, or coins required to pass through the door
     */
    public int getType() {
        return type;
    }

    /**
     *
     * @param newType set a new coin value (type for the door)
     */
    public void setType(int newType) {
        this.type = newType;
    }

    /**
     *
     * @return the label of the door, "corridor" or "door"
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param newLabel set the label of the door, "corridor" or "door"
     */
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }
}

