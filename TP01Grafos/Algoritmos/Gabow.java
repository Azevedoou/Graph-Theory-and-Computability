import java.util.*;

class Gabow {
 public static List<Aresta> acharAGMGabow(int raiz, Grafo grafo) {
        int V = grafo.getVertice();
        List<Aresta> arestas = grafo.getArestas();
        List<Aresta> resultado = new ArrayList<>();
        int[] pai = new int[V];
        int[] pesoMinimo = new int[V];

        while (true) {
            Arrays.fill(pesoMinimo, Integer.MAX_VALUE);
            Arrays.fill(pai, -1);

            for (Aresta aresta : arestas) {
                if (aresta.origem != aresta.destino && aresta.peso < pesoMinimo[aresta.destino]) {
                    pesoMinimo[aresta.destino] = aresta.peso;
                    pai[aresta.destino] = aresta.origem;
                }
            }

            boolean ciclo = false;
            for (int i = 0; i < V; ++i) {
                if (i != raiz && pai[i] == -1) {
                    ciclo = true;
                    break;
                }
            }

            if (!ciclo) {
                break;
            }

            //int cicloNo = -1;
            boolean[] emCiclo = new boolean[V];
            Arrays.fill(emCiclo, false);
            int cur = raiz;
            while (!emCiclo[cur]) {
                emCiclo[cur] = true;
                cur = pai[cur];
            }
            //cicloNo = cur;
            

            List<Aresta> arestasCiclo = new ArrayList<>();
            for (Aresta aresta : arestas) {
                if (emCiclo[aresta.origem] && emCiclo[aresta.destino]) {
                    arestasCiclo.add(aresta);
                }
            }

            int idxArestaPesoMinimo = 0;
            int pesoArestaPesoMinimo = Integer.MAX_VALUE;
            for (int i = 0; i < arestasCiclo.size(); ++i) {
                if (arestasCiclo.get(i).peso < pesoArestaPesoMinimo) {
                    pesoArestaPesoMinimo = arestasCiclo.get(i).peso;
                    idxArestaPesoMinimo = i;
                }
            }

            Aresta arestaPesoMinimo = arestasCiclo.get(idxArestaPesoMinimo);
            arestas.remove(arestaPesoMinimo);
            arestas.add(new Aresta(raiz, arestaPesoMinimo.destino, arestaPesoMinimo.peso - pesoArestaPesoMinimo));

            for (Aresta aresta : arestasCiclo) {
                if (aresta != arestaPesoMinimo) {
                    arestas.add(new Aresta(aresta.origem, aresta.destino, aresta.peso - pesoArestaPesoMinimo));
                }
            }
        }

        for (int i = 0; i < V; ++i) {
            if (i != raiz) {
                resultado.add(new Aresta(pai[i], i, pesoMinimo[i]));
            }
        }
            return resultado;
    }
}
