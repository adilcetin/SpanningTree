import java.util.ArrayList;
import java.util.List;

public class SpanningTreeList {

    private final List<List<Edge>> list;

    public SpanningTreeList() { list = new ArrayList<>(); }

    public List<List<Edge>> get() {
        return list;
    }

    public int size(){ return list.size(); }

    public void addTree(List<Edge> edges){
        list.add(edges);
    }

    public boolean contains(List<Edge> edges){
        for(List<Edge> spanningTree : list){
            int sameCount = 0;
            for(Edge e: edges){
                if(spanningTree.contains(e)){
                    sameCount++;
                }
            }
            if(sameCount == spanningTree.size()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SpanningTreeList : \n");
        for(int i=0; i<list.size(); i++){
            stringBuilder.append(i+1).append(". Spanning Tree = { ").append(list.get(i).toString()).append(" }\n");
        }
        return stringBuilder.toString();
    }
}
