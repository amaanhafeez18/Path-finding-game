// Created by Amaan Hafeez, 251298573, ahafeez7@uwo.ca

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;


public class Maze {
    /**
     * instance methods of the class
     */
    private Graph mazeGraph;
    private GraphNode startingNode;
    private GraphNode exitNode;
    private int coinsAvailable;

    /**
     * A constructor for the maze class, responsible for correctly creating edges based on the input data
     *
     * @param inputFile A text file containing ordered data
     * @throws MazeException
     * @throws FileNotFoundException
     * @throws NumberFormatException
     * @throws ArrayIndexOutOfBoundsException
     */
    public Maze(String inputFile) throws MazeException, FileNotFoundException, NumberFormatException, ArrayIndexOutOfBoundsException {
        // Try-Catch block ensure the file format is correct.
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            int width;
            int height;
            try {
                int S = Integer.parseInt(in.readLine()); // Scale factor (not used)
                width = Integer.parseInt(in.readLine());
                height = Integer.parseInt(in.readLine());
                coinsAvailable = Integer.parseInt(in.readLine()); // Coins available
            } catch (NumberFormatException | IOException e) {
                throw new RuntimeException(e);
            }

            mazeGraph = new Graph(width * height);

            // Setting the size of the 2d array meant to hold the graph
            String[][] storeChars = new String[2 * height - 1][2 * width - 1];
            int lineCounter = 0;
            String line;

            // Storing all maze line input into a one 2d array
            while ((line = in.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    storeChars[lineCounter][i] = String.valueOf(line.charAt(i));
                }
                lineCounter++;
            }

            // The index of the current node being processed
            int curindex = 0;
            String rightNeighbour;
            String downNeighbour;
            for (int i = 0; i < 2 * height - 1; i++) {
                for (int j = 0; j < 2 * width - 1; j++) {
                    String s = storeChars[i][j];
                    // This check skips all lines that do not contain starting, exit or rooms.
                    if (i % 2 == 0) {
                        // Start of the maze
                        if (s.equals("s")) {
                            startingNode = mazeGraph.getNode(curindex);

                            // Checking neighbour to the immediate left.
                            rightNeighbour = storeChars[i][j + 1];
                            if (Objects.equals(rightNeighbour, "c")) {
                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), 0, "corridor");

                            } else if (!Objects.equals(rightNeighbour, "w")) {
                                int coinsRequired = Integer.parseInt(storeChars[i][j + 1]);
                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), coinsRequired, "door");
                            }

                            // Checking neighbour to the immediate bottom
                            downNeighbour = storeChars[i + 1][j];
                            if (Objects.equals(downNeighbour, "c")) {
                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), 0, "corridor");

                            } else if (!Objects.equals(downNeighbour, "w")) {
                                int coinsRequired = Integer.parseInt(storeChars[i + 1][j]);
                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), coinsRequired, "door");
                            }
                            curindex += 1;
                            // A room in the maze or an exit of the maze
                        } else if ((s.equals("o") || (s.equals("x")))) {
                            if (s.equals("x")) {
                                exitNode = mazeGraph.getNode(curindex);
                            }
                            // Checking neighbour to the immediate left.
                            if ((2 * width - 1) > j + 1) {
                                rightNeighbour = storeChars[i][j + 1];
                                if (Objects.equals(rightNeighbour, "c")) {
                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), 0, "corridor");

                                } else if (!Objects.equals(rightNeighbour, "w")) {
                                    int coinsRequired = Integer.parseInt(storeChars[i][j + 1]);
                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), coinsRequired, "door");
                                }
                            }
                            // Checking neighbour to the immediate bottom

                            if ((2 * height - 1) > i + 1) {
                                downNeighbour = storeChars[i + 1][j];
                                if (Objects.equals(downNeighbour, "c")) {
                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), 0, "corridor");
                                } else if (!Objects.equals(downNeighbour, "w")) {
                                    int coinsRequired = Integer.parseInt(storeChars[i + 1][j]);
                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), coinsRequired, "door");

                                }
                            }
                            curindex += 1;
                        }
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new MazeException("Input file not found");
        } catch (NumberFormatException e) {
            throw new MazeException("Invalid file format");
        } catch (GraphException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return a graph object representing the maze in this case
     * @throws MazeException
     */
    public Graph getGraph() throws MazeException {
        if (mazeGraph == null) {
            throw new MazeException("Graph is not initialized");
        }
        return mazeGraph;
    }

    /**
     * Algorithm conducted in lecture, adapted to fit the requirements of the maze program
     *
     * @return an Iterator object containing the path required to complete the maze or null in the case no path is found
     * @throws GraphException
     */
    public Iterator<GraphNode> solve() throws GraphException {
        // Three stacks are set up to track the movement.
        Stack<GraphNode> stack = new Stack<>();
        Stack<GraphNode> path = new Stack<>();
        Stack<Integer> coinsStack = new Stack<>();
        stack.push(startingNode);
        coinsStack.push(coinsAvailable);

        while (!stack.isEmpty()) {
            GraphNode currentNode = stack.peek();
            int currentCoins = coinsStack.peek();

            // if exit is found
            if (currentNode.equals(exitNode)) {
                path.push(currentNode);
                return path.iterator();
            }

            if (!currentNode.isMarked()) {
                currentNode.mark(true);
                path.push(currentNode);

                Iterator<GraphEdge> edges = mazeGraph.incidentEdges(currentNode);
                // Iterating through all the edges incident on the graph
                while (edges.hasNext()) {
                    GraphEdge edge = edges.next();
                    GraphNode neighbor = edge.firstEndpoint().equals(currentNode) ? edge.secondEndpoint() : edge.firstEndpoint();

                    if (!neighbor.isMarked()) {
                        int coinsRequired = edge.getLabel().equals("door") ? edge.getType() : 0;
                        // Subtracting the coins required to pass through the door
                        int newCoins = currentCoins - coinsRequired;

                        if (newCoins >= 0) {
                            stack.push(neighbor);
                            // Pushing the new available coins on to the coinstack
                            coinsStack.push(newCoins);
                        }
                    }
                }
            } else {
                // In the case path is not found, return the stacks to original position
                stack.pop();
                coinsStack.pop();
                path.pop();
                currentNode.mark(false);
            }
        }

        return null; // No path found
    }

}

