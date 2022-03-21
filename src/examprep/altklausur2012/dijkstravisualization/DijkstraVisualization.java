package examprep.altklausur2012.dijkstravisualization;

import aud.example.graph.TraversalExample;

import java.io.File;

public class DijkstraVisualization {
    public static void main(final String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        final File f = new File("./src/examprep/altklausur2012/dijkstravisualization/g.graph");
        System.out.println(f.exists());
        final String graph = "-g";
        final String graph2 = "./src/examprep/altklausur2012/dijkstravisualization/g2.graph";
        final String method = "-m";
        final String method2 = "prim";
        final String[] strArr = {graph, graph2, method, method2};

        TraversalExample.main(strArr);

    }
}
