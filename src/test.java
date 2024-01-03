//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Stack;
//
//public class RoadMap {
//    /*
//     * Instance variables for the graph, info provided by file, stack to store
//     * nodes, and boolean to check if destination has been reached
//     */
//    private Graph graph;
//    private int start, destination, width, length, initMoney, toll, reward;
//    private Stack<Node> nodeStack = new Stack<Node>();
//    private boolean reachedDestination = false;
//
//    /*
//     * constructor for roadmap
//     */
//    public RoadMap(String inputFile) throws MapException {
//        try {
//            // open file to be read
//            BufferedReader in = new BufferedReader(new FileReader(inputFile));
//
//            // get information from the file
//            Integer.parseInt(in.readLine());
//            start = Integer.parseInt(in.readLine());
//            destination = Integer.parseInt(in.readLine());
//            width = Integer.parseInt(in.readLine());
//            length = Integer.parseInt(in.readLine());
//            initMoney = Integer.parseInt(in.readLine());
//            toll = Integer.parseInt(in.readLine());
//            reward = Integer.parseInt(in.readLine());
//
//            // create a graph large enough to store the nodes and edges
//            graph = new Graph(width * length);
//
//            // create a 2d string array to store the different symbols from the file
//            String[][] storeChars = new String[2 * length - 1][2 * width - 1];
//            // variables to store the node we have added and the line we are on
//            int nodeCounter = 0, lineCounter = 0;
//            String line = in.readLine();
//
//            // while there are still lines, populate the array
//            while (line != null) {
//                for (int i = 0; i < line.length(); i++) {
//                    // if the character is a '+', then this is a node so insert a number to
//                    // represent a node
//                    if (line.charAt(i) == '+') {
//                        storeChars[lineCounter][i] = Integer.toString(nodeCounter);
//                        // increase the counter so that the next node can be stored
//                        nodeCounter++;
//                        // otherwise, add the character to the array
//                    } else
//                        storeChars[lineCounter][i] = String.valueOf(line.charAt(i));
//                }
//                // continue to the next line and increase the line count variable
//                line = in.readLine();
//                lineCounter++;
//            }
//
//            // use the array to store the edges in the graph
//            for (int i = 0; i < 2 * length - 1; i++) {
//                for (int j = 0; j < 2 * width - 1; j++) {
//                    // variable to represent the current symbol
//                    String s = storeChars[i][j];
//                    // if it is a toll road, add a toll type edge with the surrounding nodes as end
//                    // points
//                    if (s.equals("T")) {
//                        // if the road is vertical
//                        if (i % 2 != 0)
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i - 1][j])),
//                                    graph.getNode(Integer.parseInt(storeChars[i + 1][j])), 1);
//                            // if the road is horizontal
//                        else
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i][j - 1])),
//                                    graph.getNode(Integer.parseInt(storeChars[i][j + 1])), 1);
//                        // if it is a public road, add a public type edge with the surrounding nodes as
//                        // end points
//                    } else if (s.equals("F")) {
//                        if (i % 2 != 0) {
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i - 1][j])),
//                                    graph.getNode(Integer.parseInt(storeChars[i + 1][j])), 0);
//                        } else
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i][j - 1])),
//                                    graph.getNode(Integer.parseInt(storeChars[i][j + 1])), 0);
//                        // if it is a reward road, add a reward type edge with the surrounding nodes as
//                        // end points
//                    } else if (s.equals("C")) {
//                        if (i % 2 != 0)
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i - 1][j])),
//                                    graph.getNode(Integer.parseInt(storeChars[i + 1][j])), -1);
//                        else
//                            graph.insertEdge(graph.getNode(Integer.parseInt(storeChars[i][j - 1])),
//                                    graph.getNode(Integer.parseInt(storeChars[i][j + 1])), -1);
//                    }
//                }
//            }
//            // close the file
//
//            // catch exceptioins
//        } catch (IOException e) {
//            throw new MapException();
//        } catch (GraphException e) {
//            System.out.println(e);
//        }
//    }
//
//    // method to get the graph
//    public Graph getGraph() {
//        return graph;
//    }
//
//    // method to get the starting point node
//    public int getStartingNode() {
//        return start;
//    }
//
//    // method to get the destination node
//    public int getDestinationNode() {
//        return destination;
//    }
//
//    // method to get the initial amount of money
//    public int getInitialMoney() {
//        return initMoney;
//    }
//
//    /*
//     * method to return an iterator containing a path to the destination
//     */
//    public Iterator findPath(int start, int destination, int initialMoney) throws GraphException {
//        // variables for the current edge and the next edge to explore
//        Edge currentEdge;
//        Edge previousEdge;
//        try {
//            // get the starting node and mark it, then push it into the stack
//            Node currentNode = graph.getNode(start);
//            currentNode.setMark(true);
//            nodeStack.push(currentNode);
//
//            // if the destination has been reached then return the iterator
//            if (currentNode.getName() == destination) {
//                reachedDestination = true;
//                return nodeStack.iterator();
//            } else {
//                // get the incident edges of the node to explore
//                Iterator<Edge> nodeEdges = graph.incidentEdges(currentNode);
//
//                // while there are edges remaining that are valid to be explored
//                while (nodeEdges.hasNext()) {
//
//                    // if the destination has been reached return the iterator
//                    if (reachedDestination == true) {
//                        return nodeStack.iterator();
//                    }
//
//                    // select the edge to explore
//                    currentEdge = nodeEdges.next();
//                    // set the next node to check at the end of the current edge
//                    Node nextNode;
//                    if (currentEdge.firstEndpoint() == currentNode) {
//                        nextNode = currentEdge.secondEndpoint();
//                    } else
//                        nextNode = currentEdge.firstEndpoint();
//
//                    // make sure the node hasn't been marked
//                    if (nextNode.getMark() == false) {
//                        // adjust money based on type and move onto the next node if possible
//                        if (currentEdge.getType() == 1) {
//                            if (initMoney - toll >= 0) {
//                                initMoney -= toll;
//                                findPath(nextNode.getName(), destination, initMoney);
//                            }
//                        } else if (currentEdge.getType() == -1) {
//                            initMoney += reward;
//                            findPath(nextNode.getName(), destination, initMoney);
//                        } else {
//                            findPath(nextNode.getName(), destination, initMoney);
//                        }
//                    }
//                }
//                // if there are no more edges to explore and the destination has not been
//                // reached
//                if (reachedDestination == false) {
//                    // unmark the node and remove it from the stack
//                    Node topNode = nodeStack.peek();
//                    topNode.setMark(false);
//                    nodeStack.pop();
//                    if (nodeStack.size() > 0) {
//                        // find the previous edge and adjust money based on its type
//                        if (graph.getEdge(graph.getNode(nodeStack.peek().getName()), topNode).getType() == 1) {
//                            initMoney += toll;
//                        } else if (graph.getEdge(graph.getNode(nodeStack.peek().getName()), topNode).getType() == -1) {
//                            initMoney -= reward;
//                        }
//                    }
//                }
//            }
//            // catch graph exception
//        } catch (GraphException e) {
//            throw new GraphException();
//        }
//
//        // if you have reached the destination, return the iterator else return null
//        if (nodeStack.peek().getName() == destination)
//            return nodeStack.iterator();
//        else {
//            return null;
//        }
//    }
//
//}