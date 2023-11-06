import java.util.*;

public class GrafoGIRG {
    public static void generateGIRG(Grafo grafo, double connectivityParameter, int V, double maxCoordinateValue) {
        Random rand = new Random();
        for (int i = 0; i < grafo.getVertice(); i++) {
            for (int j = 0; j < grafo.getVertice(); j++) {
                if (rand.nextInt(10) == 1) {
                    int peso = rand.nextInt(10) + 1; // Gere um peso aleatório entre 1 e 10 (personalizável)
                    grafo.adicionarAresta(i, j, peso);
                }
            }
        }

        // Certifique-se de que o grafo seja semi-fortemente conexo
        for (int i = 0; i < grafo.getVertice(); i++) {
            boolean[] visitado = new boolean[grafo.getVertice()];
            visitar(i, visitado, grafo);
        }
    }

    private static void visitar(int vertice, boolean[] visitado, Grafo grafo) {
        visitado[vertice] = true;
        for (int adj : grafo.getAdjList().get(vertice)) {
            if (!visitado[adj]) {
                visitar(adj, visitado, grafo);
            }
        }
    }
}