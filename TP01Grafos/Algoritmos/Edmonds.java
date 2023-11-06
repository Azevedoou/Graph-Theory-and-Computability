import java.util.*;

public class Edmonds {

    int[] acharAGMEdmonds(Grafo grafo, int raiz) {
        List<Aresta> arestas = grafo.getArestas();
        int V = grafo.getVertice();
        int[] pai = new int[V];
        Arrays.fill(pai, -1);

        for (int i = 0; i < V; i++) {
            if (i == raiz) {
                continue;
            }

            for (Aresta aresta : arestas) {
                if (aresta.destino == i && (pai[i] == -1 || aresta.peso < arestas.get(pai[i]).peso)) {
                    pai[i] = arestas.indexOf(aresta);
                }
            }

            if (pai[i] == -1) {
                return null; // Não é uma arborescência válida
            }
        }

        return pai;
    }
}
