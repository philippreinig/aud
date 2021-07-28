package examprep.dijkstravisualization;

import aud.example.graph.TraversalExample;

public class DijkstraVisualization {
    public static void main(final String[] args) {
        final String graph = "-g";
        final String graph2 = "D:\\Development\\aud\\src\\examprep\\dijkstravisualization\\g.graph";
        final String method = "-m";
        final String method2 = "prim";
        final String[] strArr = {graph, graph2, method, method2};

        TraversalExample.main(strArr);

    }
}
