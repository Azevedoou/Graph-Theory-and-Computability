import java.util.LinkedList;

class Graph {
    private int V; // Número de vértices
    private int[][] capacity; // Capacidade de arestas
    private int[][] residualGraph; // Grafo Residual

    public Graph(int V) {
        this.V = V;
        capacity = new int[V][V];
        residualGraph = new int[V][V];
    }

    public void addEdge(int from, int to, int cap) {
        capacity[from][to] = cap;
    }

    private boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) {
                if (!visited[v] && rGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return (visited[t] == true);
    }

    public void findDisjointPaths(int s, int t) {
        for (int u = 0; u < V; u++)
            for (int v = 0; v < V; v++)
                residualGraph[u][v] = capacity[u][v];

        int parent[] = new int[V];

        while (bfs(residualGraph, s, t, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            // Imprime o caminho encontrado
            int v = t;
            System.out.print("Caminho: " + v);
            while (v != s) {
                v = parent[v];
                System.out.print(" <- " + v);
            }
            System.out.println();
        }
    }
}

public class caminhosDisjuntos {
    public static void main(String[] args) {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);

        System.out.println("Caminhos disjuntos em arestas da origem (0) para o destino (3):");
        g.findDisjointPaths(0, 3);
    }
}
