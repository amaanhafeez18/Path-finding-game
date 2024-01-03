// Created by Amaan Hafeez, 251298573, ahafeez7@uwo.ca
public class GraphNode {
    /**
     * Instance variables
     */
    private int name;

    private boolean mark;

    /**
     *
     * @param name which is an integer representing the node
     */
    public GraphNode(int name){
        this.name = name;
    }

    /**
     *
     * @param mark used to mark whether a node has been visited or not
     */
    public void mark(boolean mark) {
        this.mark = mark;
    }

    /**
     *
     * @return boolean whether a node is marked or not.
     */
    public boolean isMarked() {
        return mark;
    }

    /**
     *
     * @return the unique name of a node object, used to identify it.
     */
    public int getName() {
        return name;
    }
}
