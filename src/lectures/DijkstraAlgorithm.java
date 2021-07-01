package lectures;

import aud.PriorityQueue;
import aud.example.graph.MyGraph;
import aud.example.graph.MyNode;
import aud.graph.AbstractGraph;
import aud.graph.SimpleEdge;
import aud.util.DotViewer;

import java.util.HashMap;
import java.util.Map;

/**
 * Pseudo-code implementation of Dijkstra's algorithm
 * function pfs (s0)
 * foreach node n do
 * d[n] = ∞;
 * p[n] = -1;
 * end
 * d[s0] = 0
 * open:= create_queue(nodes,d)                                    #all nodes!
 * while not open.is_empty() do
 * s := open.pop()
 * foreach edge e in outgoing (s) do
 * t := destination ( e )
 * pr := d [ s ]+γ( e )                                    #assume ∞+γ → ∞!
 * if open. contains ( t ) and pr < d [ t ] then
 * d [ t ]= pr ; p[ t ]= s
 * open. l ow e r ( t )
 * fi
 * od
 * od
 * end
 */

public class DijkstraAlgorithm {
//    public static MyNode[] getAllNodesFromGraph(final MyGraph graph, final MyNode startNode) {
//        if (startNode.graph() != graph) {
//            throw new IllegalArgumentException();
//        }
//        final ArrayList<MyNode> nodes = new ArrayList<>();
//        final Stack<MyNode> needToVisit = new Stack<>();
//        needToVisit.push(startNode);
//        while (!needToVisit.isEmpty()) {
//            System.out.println("looping here");
//            final MyNode node = needToVisit.pop();
//            for (final MyEdge outgoingEdge : graph.getOutEdges(node)) {
//                final MyNode nextNode = (MyNode) outgoingEdge.destination();
//                assert (nextNode != null);
//                if (!needToVisit.contains(nextNode)) {
//                    needToVisit.push(nextNode);
//                }
//            }
//            nodes.add(node);
//        }
//        return nodes.toArray(new MyNode[graph.getNumNodes()]);
//    }

    private static void printMap(final Map<MyNode, Double> map) {
        for (final Map.Entry<MyNode, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static Map<MyNode, Double> priorityFirstSearch(final MyGraph graph, final MyNode start_node, final MyNode[] nodes) {
        if (start_node.graph() != graph) {
            throw new IllegalArgumentException("startNode not in graph");
        }
        final HashMap<MyNode, Double> distances = new HashMap<>();
        final HashMap<MyNode, MyNode> predecessors = new HashMap<>();
        for (final MyNode node : nodes) {
            distances.put(node, Double.MAX_VALUE);
            distances.put(node, -1.0);
        }
        final PriorityQueue<MyNode> needToVisit = new PriorityQueue<>();
        distances.put(start_node, 0.0);
        while (!needToVisit.is_empty()) {
            final MyNode node = needToVisit.pop();
            for (final SimpleEdge edge : graph.getOutEdges(node)) {
                assert (edge.destination() instanceof MyNode);
                final MyNode nextNode = (MyNode) edge.destination();
                final double pr = distances.get(node) + edge.getWeight();
                if (needToVisit.contains(nextNode) && pr < distances.get(nextNode)) {
                    distances.put(nextNode, pr);
                    predecessors.put(nextNode, node);
                    needToVisit.lower(nextNode);
                }
            }
        }
        return distances;
    }

    public static void main(final String[] args) {
        final MyGraph graph = new MyGraph(false);
        final int amountNodes = 8;
        final MyNode[] myNodes = new MyNode[amountNodes];
        for (int i = 0; i < amountNodes; i++) {
            myNodes[i] = graph.addNode();
            myNodes[i].setLabel(String.valueOf(i + 1));
        }
        SimpleEdge se = new SimpleEdge();

        graph.addEdge(myNodes[0], myNodes[1]);
        graph.addEdge(myNodes[0], myNodes[2]);
        graph.addEdge(myNodes[0], myNodes[5]);
        graph.addEdge(myNodes[0], myNodes[6]);
        graph.addEdge(myNodes[0], myNodes[7]);
        graph.addEdge(myNodes[1], myNodes[7]);
        graph.addEdge(myNodes[2], myNodes[7]);
        graph.addEdge(myNodes[3], myNodes[4]);
        graph.addEdge(myNodes[3], myNodes[5]);
        graph.addEdge(myNodes[4], myNodes[5]);
        graph.addEdge(myNodes[4], myNodes[6]);
        graph.addEdge(myNodes[4], myNodes[7]);
        DotViewer.displayWindow(graph.toDot(), null);

        printMap(priorityFirstSearch(graph, myNodes[0], myNodes));

    }


}
