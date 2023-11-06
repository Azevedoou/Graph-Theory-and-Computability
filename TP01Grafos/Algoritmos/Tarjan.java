import java.util.*;

class Tarjan {
    
    private static int tempo = 0;

    private static void tarjanSCC(int u, int[] baixo, int[] descoberta, boolean[] emStack, Stack<Integer> stack, List<List<Integer>> resultado, List<List<Integer>> listaAdjacencia) {
        Stack<Integer> pilhaSimulacao = new Stack<>(); // Pilha para simular a recursão

        stack.push(u);
        pilhaSimulacao.push(u);
        emStack[u] = true;
        descoberta[u] = tempo;
        baixo[u] = tempo;
        tempo++;

        while (!pilhaSimulacao.isEmpty()) {
            int atual = pilhaSimulacao.peek();
            boolean encontrado = false;

            for (Integer v : listaAdjacencia.get(atual)) {
                if (descoberta[v] == -1) {
                    stack.push(v);
                    pilhaSimulacao.push(v);
                    emStack[v] = true;
                    descoberta[v] = tempo;
                    baixo[v] = tempo;
                    tempo++;
                    encontrado = true;
                    break;
                } else if (emStack[v]) {
                    baixo[atual] = Math.min(baixo[atual], descoberta[v]);
                }
            }

            if (!encontrado) {
                int w = -1;
                List<Integer> componente = new ArrayList<>();
                while (w != atual) {
                    w = stack.pop();
                    pilhaSimulacao.pop();
                    emStack[w] = false;
                    componente.add(w);
                }
                resultado.add(componente);
            }
        }
    }

    private static List<List<Integer>> encontrarComponentesFortementeConectados(int V, List<List<Integer>> listaAdjacencia) {
        List<List<Integer>> resultado = new ArrayList<>();
        int[] baixo = new int[V];
        int[] descoberta = new int[V];
        Arrays.fill(baixo, -1);
        Arrays.fill(descoberta, -1);
        boolean[] emStack = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < V; i++) {
            if (descoberta[i] == -1) {
                tarjanSCC(i, baixo, descoberta, emStack, stack, resultado, listaAdjacencia);
            }
        }

        return resultado;
    }

    public static List<Aresta> tarjanMST(Grafo grafo) {
        int V = grafo.getVertice();
        List<Aresta> arestas = grafo.getArestas();
        List<List<Integer>> listaAdjacencia = grafo.getAdjList();

        List<Aresta> arvoreGeradoraMinima = new ArrayList<>();

        List<List<Integer>> componentesFortementeConectados = encontrarComponentesFortementeConectados(V, listaAdjacencia);
        
        List<Aresta> arestasNoGrafoOriginal = kruskalMST(V, arestas);
        
        for (Aresta aresta : arestasNoGrafoOriginal) {
            //int componenteOrigem = encontrarComponenteContendoVertice(componentesFortementeConectados, aresta.origem);
            //int componenteDestino = encontrarComponenteContendoVertice(componentesFortementeConectados, aresta.destino);
            arvoreGeradoraMinima.add(new Aresta(aresta.origem, aresta.destino, aresta.peso));
        }
        

        return arvoreGeradoraMinima;
    }

    private static int encontrarComponenteContendoVertice(List<List<Integer>> componentesFortementeConectados, int vertice) {
        for (int i = 0; i < componentesFortementeConectados.size(); i++) {
            if (componentesFortementeConectados.get(i).contains(vertice)) {
                return i;
            }
        }
        return -1;
    }

    private static List<Aresta> kruskalMST(int V, List<Aresta> arestas) {
        List<Aresta> resultado = new ArrayList<>();
        Collections.sort(arestas, Comparator.comparingInt(aresta -> aresta.peso));
        UnionFind uf = new UnionFind(V);

        for (Aresta aresta : arestas) {
            int paiOrigem = uf.find(aresta.origem);
            int paiDestino = uf.find(aresta.destino);
            if (paiOrigem != paiDestino) {
                resultado.add(aresta);
                uf.union(paiOrigem, paiDestino);
            }
        }

        return resultado;
    }

    static class UnionFind {
        private int[] pai;

        public UnionFind(int tamanho) {
            pai = new int[tamanho];
            for (int i = 0; i < tamanho; i++) {
                pai[i] = i;
            }
        }

        public int find(int u) {
            if (pai[u] != u) {
                pai[u] = find(pai[u]);
            }
            return pai[u];
        }

        public void union(int u, int v) {
            int paiU = find(u);
            int paiV = find(v);
            pai[paiU] = paiV;
        }
    }
}
