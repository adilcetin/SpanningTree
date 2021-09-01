public class Edge {

    private final int src, dest;

    public Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    @Override
    public String toString() {
        return "Edge{" + src + "-" + dest + "}";
    }

}
