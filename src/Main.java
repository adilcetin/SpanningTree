import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main (String[] args) {

        /*
            Program Input Graph

                     0-------1------5
                     |  \  / |
                     |   \/  |
                     |  4/\  |
                     |  /  \ |
                     2-------3

         */

        int V = 6;  // Number of vertices in graph
        int E = 9;  // Number of edges in graph
        Graph graph = new Graph(V, E);

        graph.addEdge(new Edge(0, 1));
        graph.addEdge(new Edge(0, 2));
        graph.addEdge(new Edge(0, 4));
        graph.addEdge(new Edge(1, 3));
        graph.addEdge(new Edge(1, 4));
        graph.addEdge(new Edge(2, 3));
        graph.addEdge(new Edge(2, 4));
        graph.addEdge(new Edge(3, 4));
        graph.addEdge(new Edge(1, 5));


        System.out.println("Program 1 Output :");
        System.out.println("Input " + graph.toString() + "\n");

        SpanningTreeList spanningTreeList = new SpanningTreeList();
        graph.findSpanningTrees(spanningTreeList, new ArrayList<>(Collections.singletonList(0)), new ArrayList<>());
        System.out.println(spanningTreeList.toString());

        /*****************************************************************************************************************/

        System.out.println("Program 2 Output :");
        System.out.println("Input " + graph.toString() + "\n");
        System.out.println("Cut Sets :");

        CutSet cutSet = new CutSet();
        cutSet.calculate(graph);
        System.out.println(cutSet.toString());


        for(int i=0; i<spanningTreeList.get().size(); i++){
            StringBuilder builder = new StringBuilder();
            builder.append((i+1) +". Spanning Tree has these cutset edges: [");
            String prefix = "";
            for(Edge edge : cutSet.getCutSetEdges()){
                if(spanningTreeList.get().get(i).contains(edge)){
                    builder.append(prefix).append(edge);
                    prefix = ", ";
                }
            }
            builder.append("]");
            System.out.println(builder.toString());
        }

    }

}
