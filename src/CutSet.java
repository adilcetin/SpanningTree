import java.util.ArrayList;
import java.util.List;

public class CutSet {

    private Graph graph;
    private final List<List<Edge>> cutSets;
    private final List<Edge> cutSetEdges;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<cutSets.size(); i++){
            builder.append(i+1).append(". ").append(cutSets.get(i)).append("\n");
        }
        return builder.toString();
    }

    public List<Edge> getCutSetEdges() {
        return cutSetEdges;
    }

    public CutSet() {
        this.cutSets = new ArrayList<>();
        this.cutSetEdges = new ArrayList<>();
    }

    private static void edgeCombination(List<Edge> arr, List<Edge> data, List<List<Edge>> result, int start, int end, int index, int r) {
        if (index == r) {
            result.add(data);
            return;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++) {
            ArrayList<Edge> cloneOfData = new ArrayList<>(data);
            cloneOfData.add(index, arr.get(i));
            edgeCombination(arr, cloneOfData, result, i + 1, end, index + 1, r);
        }
    }


    public void calculate(Graph graph){
        this.graph = graph;

        for(int i=1; i<graph.getV(); i++){
            List<List<Edge>> result = new ArrayList<>();
            edgeCombination(graph.getEdgeList(), new ArrayList<>(), result, 0, graph.getEdgeList().size()-1, 0, i);

            for(List<Edge> set : result){

                if(isSetIsMinimalCutSet(set)) {
                    if(checkCutSetOrNot(set)){
                        cutSets.add(set);

                        for(Edge edge : set){
                            if(!cutSetEdges.contains(edge)){
                                cutSetEdges.add(edge);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isSetIsMinimalCutSet(List<Edge> set){
        for(List<Edge> cutSet : cutSets){
            if(set.containsAll(cutSet)){
                return false;
            }
        }
        return true;
    }

    private boolean checkCutSetOrNot(List<Edge> set){
        Graph cloneOfGraph = graph.clone();

        for(Edge edge : set){
            cloneOfGraph.removeEdge(edge);
        }

        List<List<Edge>> connectedGraphs = new ArrayList<>();
        findConnectedGraphs(cloneOfGraph.clone(), connectedGraphs);

        return connectedGraphs.size() == 2 || (connectedGraphs.size() == 1 && graph.getAllVertices().size() == 1 + cloneOfGraph.getAllVertices().size());

    }

    private void findConnectedGraphs(Graph g, List<List<Edge>> connectedGraphs){
        if(g.getEdgeList().size() == 0){
            return;
        }

        List<Edge> connectedGraph = new ArrayList<>();
        findConnectedGraph(g, g.getEdgeList().get(0), connectedGraph);

        connectedGraphs.add(connectedGraph);

        if(g.getEdgeList().size() != 0){
            findConnectedGraphs(g, connectedGraphs);
        }
    }

    private void findConnectedGraph(Graph g, Edge startEdge, List<Edge> result){

        result.add(startEdge);
        List<Edge> connectedEdges = g.getConnectedEdges(startEdge);
        g.removeEdge(startEdge);

        for(Edge connectedEdge : connectedEdges){
            if(!result.contains(connectedEdge))
                findConnectedGraph(g, connectedEdge, result);
        }
    }
}
