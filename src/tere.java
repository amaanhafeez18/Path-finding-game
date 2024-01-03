//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Objects;
//import java.util.Stack;
//
//
//public class Maze {
//    private Graph mazeGraph;
//    private GraphNode startingNode;
//    private GraphNode exitNode;
//    private int coinsAvailable;
//
//    public Maze(String inputFile) throws MazeException, FileNotFoundException, NumberFormatException {
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(inputFile));
//            int S = Integer.parseInt(in.readLine()); // Scale factor (not used)
//            int width = Integer.parseInt(in.readLine()); // Width of maze (number of rooms in each row)
//            int height = Integer.parseInt(in.readLine()); // Height of maze (number of rooms in each column)
//            coinsAvailable = Integer.parseInt(in.readLine()); // Coins available
//            mazeGraph = new Graph(width * height);
//
//            String[][] storeChars = new String[2 * height - 1][2 * width - 1];
//            int lineCounter = 0;
//            String line;
//
//            while ((line = in.readLine()) != null) {
//                for (int i = 0; i < line.length(); i++) {
//                    storeChars[lineCounter][i] = String.valueOf(line.charAt(i));
//                }
//                lineCounter++;
//            }
//
//            int nodeCounter = 0;
//            int curindex = 0;
//            for (int i = 0; i < 2 * height - 1; i = i + 2) {
//                for (int j = 0; j < 2 * width - 1; j++) {
//                    String s = storeChars[i][j];
//                    switch (s) {
//                        case "s":
//                            startingNode = new GraphNode(nodeCounter);
//
//                            if (Objects.equals(storeChars[i][j + 1], "c")) {
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), 0, "corridor");
//                            } else if (Objects.equals(storeChars[i][j + 1], "w")) {
//                                break;
//                            } else {
//                                int coinsRequired = Integer.parseInt(s);
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), coinsRequired, "door");
//
//                            }
//                            if (Objects.equals(storeChars[i + 1][j], "c")) {
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), 0, "corridor");
//                            } else if (Objects.equals(storeChars[i + 1][j], "w")) {
//                                break;
//                            } else {
//                                int coinsRequired = Integer.parseInt(s);
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), coinsRequired, "door");
//
//                            }
//                            nodeCounter++;
//                            curindex += 2;
//                            break;
//                        case "o":
//                            if (width > j + 1) {
//                                if (Objects.equals(storeChars[i][j + 1], "c")) {
//                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), 0, "corridor");
//                                } else if (Objects.equals(storeChars[i][j + 1], "w")) {
//                                    break;
//                                } else {
//                                    int coinsRequired = Integer.parseInt(s);
//                                    mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + 1), coinsRequired, "door");
//
//                                }
//                            }
//                            if (Objects.equals(storeChars[i + 1][j], "c")) {
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), 0, "corridor");
//                            } else if (Objects.equals(storeChars[i + 1][j], "w")) {
//                                break;
//                            } else {
//                                int coinsRequired = Integer.parseInt(s);
//                                mazeGraph.insertEdge(mazeGraph.getNode(curindex), mazeGraph.getNode(curindex + width), coinsRequired, "door");
//
//                            }
//
//                            nodeCounter++;
//                            curindex += 2;
//                            break;
//
//
//                    }
//                }
//            }
//            in.close();
//        } catch (FileNotFoundException e) {
//            throw new MazeException("Input file not found");
//        } catch (NumberFormatException e) {
//            throw new MazeException("Invalid file format");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (GraphException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public Graph getGraph() throws MazeException {
//        if (mazeGraph == null) {
//            throw new MazeException("Graph is not initialized");
//        }
//        return mazeGraph;
//    }
//
//    public Iterator<GraphNode> solve() throws GraphException {
//        Stack<GraphNode> stack = new Stack<>();
//        Stack<GraphNode> path = new Stack<>();
//        int currentCoins = coinsAvailable;
//        stack.push(startingNode);
//
//        while (!stack.isEmpty()) {
//            GraphNode current = stack.pop();
//
//            if (!current.isMarked()) {
//                current.mark(true);
//                path.push(current);
//
//                if (current.equals(exitNode)) {
//                    return path.iterator(); // Found exit
//                }
//
//                // Explore neighbors
//                Iterator<GraphEdge> edges = mazeGraph.incidentEdges(current);
//                while (edges != null && edges.hasNext()) {
//                    GraphEdge edge = edges.next();
//                    GraphNode neighbor;
//                    if (edge.firstEndpoint().equals(current)) {
//                        neighbor = edge.secondEndpoint();
//                    } else {
//                        neighbor = edge.firstEndpoint();
//                    }
//
//                    // Check for coins if it's a door
//                    if (edge.getLabel().equals("door")) {
//                        int coinsRequired = edge.getType();
//                        if (currentCoins < coinsRequired) {
//                            continue; // Not enough coins
//                        }
//                        currentCoins -= coinsRequired; // Subtract coins used
//                    }
//
//                    if (!neighbor.isMarked()) {
//                        stack.push(neighbor);
//                    }
//                }
//            } else {
//                current.mark(false);
//                path.pop();
//                stack.pop();
//
//                // Re-add coins if backtracking through a door
//                if (!stack.isEmpty()) {
//                    GraphEdge edge = mazeGraph.getEdge(current, stack.peek());
//                    if (edge != null && edge.getLabel().equals("door")) {
//                        currentCoins += edge.getType();
//                    }
//                }
//            }
//
//
//        }
//        return null; // No path found
//    }
//}
//
