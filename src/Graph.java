import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private final int V, E;
    private ArrayList<Edge> edgeList;

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public Graph(int v, int e) {
        V = v;      // Number of vertices in graph
        E = e;      // Number of edges in graph
        edgeList = new ArrayList<>();
    }

    void addEdge(Edge e){
        if(edgeList.size() <= E){
            edgeList.add(e);
        }
    }

    void removeEdge(Edge e){
        edgeList.remove(e);
    }
    
    public List<Integer> getAllVertices(){
        List<Integer> vertices = new ArrayList<>();

        for (Edge edge : edgeList) {
            if (!vertices.contains(edge.getSrc())) {
                vertices.add(edge.getSrc());
            }
            if (!vertices.contains(edge.getDest())) {
                vertices.add(edge.getDest());
            }
        }
        return vertices;
    }

    public int getV(){
        return V;
    }

    @Override
    public String toString() {
        return "Graph = { " + edgeList + " }";
    }

    private List<Integer> getExceptVertices(List<Integer> exceptVertices){
        List<Integer> allVertices = getAllVertices();
        allVertices.removeIf(exceptVertices::contains);
        return allVertices;
    }

    private Edge findConnectedEdgeBetween2Vertices(int fromNode, int toNode){
        for(int i=0; i<E; i++){
            if((edgeList.get(i).getSrc() == fromNode && edgeList.get(i).getDest() == toNode)
                    || (edgeList.get(i).getDest() == fromNode && edgeList.get(i).getSrc() == toNode) ){
                return edgeList.get(i);
            }
        }
        return null;
    }

    void generatePermutations(List<List<Integer>> lists, List<List<Integer>> result, int depth, ArrayList<Integer> current) {
        if (depth == lists.size()) {
            result.add(current);
            return ;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            ArrayList<Integer> cloneOfCurrent = new ArrayList<>(current);
            cloneOfCurrent.add(lists.get(depth).get(i));
            generatePermutations(lists, result, depth + 1, cloneOfCurrent);
        }
    }

    public Graph clone(){
        Graph g = new Graph(V, E);
        ArrayList<Edge> edges = new ArrayList<>(getEdgeList());
        g.setEdgeList(edges);
        return g;
    }

    public void findSpanningTrees(SpanningTreeList spanningTreeList, ArrayList<Integer> takenVertices, ArrayList<Edge> takenEdges) {

        if(takenVertices.size() == V && !spanningTreeList.contains(takenEdges)){
            spanningTreeList.addTree(takenEdges);
            return;
        }

        List<List<Integer>> permutationOfVertices = new ArrayList<>();
        generatePermutations(Arrays.asList(takenVertices, getExceptVertices(takenVertices)), permutationOfVertices, 0, new ArrayList<>());

        for(List<Integer> vertices : permutationOfVertices){

            Edge edgeBetween2Vertices = findConnectedEdgeBetween2Vertices(vertices.get(0), vertices.get(1));

            if(edgeBetween2Vertices != null){

                ArrayList<Edge> cloneOfTakenEdges = new ArrayList<>(takenEdges);
                ArrayList<Integer> cloneOfTakenVertices = new ArrayList<>(takenVertices);

                cloneOfTakenEdges.add(edgeBetween2Vertices);
                cloneOfTakenVertices.add(vertices.get(1));

                findSpanningTrees(spanningTreeList, cloneOfTakenVertices, cloneOfTakenEdges);
            }
        }
    }

    private boolean areTwoEdgeNeighbors(Edge e1, Edge e2){
        return e1.getDest() == e2.getSrc() || e1.getSrc() == e2.getDest() || e1.getSrc() == e2.getSrc() || e1.getDest() == e2.getDest();
    }

    public List<Edge> getConnectedEdges(Edge e1){
        List<Edge> list = new ArrayList<>();
        for (Edge e2 : edgeList){
            if(e1 != e2 && areTwoEdgeNeighbors(e1, e2)){
                list.add(e2);
            }
        }
        return list;
    }

}
