import java.util.*;

class FastSpeedRun{
    public static List<Integer> fastSpeedrunTest(int origem, int destino, Grafo grafo) {
        int[] dist = new int[grafo.getVertice()];
        int[] anterior = new int[grafo.getVertice()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);

        dist[origem] = 0;

        PriorityQueue<Integer> fila = new PriorityQueue<>(grafo.getVertice(), new Comparator<Integer>() {
            public int compare(Integer u, Integer v) {
                return Integer.compare(dist[u], dist[v]);
            }
        });

        fila.add(origem);

        while (!fila.isEmpty()) {
            int u = fila.poll();

            if (u == destino) {
                return reconstruirCaminho(origem, destino, anterior);
            }

            for (int v : grafo.getAdjList().get(u)) {
                Aresta aresta = null;
                for (Aresta a : grafo.getArestas()) {
                    if ((a.origem == u && a.destino == v) || (a.origem == v && a.destino == u)) {
                        aresta = a;
                        break;
                    }
                }

                int peso = aresta != null ? aresta.peso : 1;

                if (dist[u] + peso < dist[v]) {
                    dist[v] = dist[u] + peso;
                    anterior[v] = u;
                    fila.add(v);
                }
            }
        }

        return new ArrayList<>();
    }

    private static List<Integer> reconstruirCaminho(int origem, int destino, int[] anterior) {
        List<Integer> caminho = new ArrayList<>();
        int u = destino;
        while (u != -1) {
            caminho.add(u);
            u = anterior[u];
        }
        Collections.reverse(caminho);
        return caminho;
    }
}