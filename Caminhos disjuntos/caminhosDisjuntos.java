import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class Edge {
    int dest;
    double weight;

    Edge(int dest, double weight) {
        this.dest = dest;
        this.weight = weight;
    }
}

class Graph {
    int V;
    List<List<Edge>> adjList;

    Graph(int V) {
        this.V = V+1;
        adjList = new ArrayList<>(V);
        for (int i = 0; i < V+1; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    void addEdge(int src, int dest, double weight) {
        adjList.get(src).add(new Edge(dest, weight));
    }

    void removeEdge(int src, int dest) {
        adjList.get(src).removeIf(edge -> edge.dest == dest);
    }

    List<Integer> dijkstra(int src, int dest) {
        double[] dist = new double[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[src] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(node -> dist[node]));
        pq.offer(src);

        int[] prev = new int[V];
        Arrays.fill(prev, -1);

        while (!pq.isEmpty()) {
            int node = pq.poll();

            for (Edge edge : adjList.get(node)) {
                if (dist[node] + edge.weight < dist[edge.dest]) {
                    dist[edge.dest] = dist[node] + edge.weight;
                    prev[edge.dest] = node;
                    pq.offer(edge.dest);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        for (int at = dest; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path.size() > 1 ? path : Collections.emptyList();
    }
}

public class caminhosDisjuntos {
    public static void main(String[] args) {
        String nomeArq;
        System.out.println("Digite o nome do arquivo desejado (sem o .txt): ");
        Scanner sc = new Scanner(System.in);
        nomeArq = sc.nextLine();
        String localDir = System.getProperty("user.dir");
        String arq = localDir + "\\Caminhos disjuntos\\" + nomeArq + ".txt"; // Localização da pasta onde está localizado o meu arquivo.
        try{
            BufferedReader br = new BufferedReader(new FileReader(arq));
            String linha;
            int V,n,m;
            n = m = 0;
            linha = br.readLine();
            Scanner scTxt = new Scanner(linha);
            V = scTxt.nextInt(); // Leio a #V.
            n = scTxt.nextInt(); // Leio a origem.
            m = scTxt.nextInt(); // Leio o destino.
            Graph g = new Graph(V);
            while((linha=br.readLine()) != null){
                Scanner scLin = new Scanner(linha);
                int v = scLin.nextInt();
                int w = scLin.nextInt();
                g.addEdge(v, w, 1);
                scLin.close();
            }
            scTxt.close();
            System.out.println("Caminhos disjuntos em arestas da origem ("+n+") para o destino ("+m+"):");
            int origem = n; 
            int destino = m;
            long inicio = System.currentTimeMillis();
            List<List<Integer>> allDisjointPaths = new ArrayList<>();
            while (true) {
                List<Integer> path = g.dijkstra(origem, destino);
                if (path.isEmpty()) {
                    break;
                }
                allDisjointPaths.add(path);
                for (int j = 0; j < path.size() - 1; j++) {
                    g.removeEdge(path.get(j), path.get(j + 1));
                }
            }
            long tempoTotal = System.currentTimeMillis() - inicio;
            System.out.println("Tamanho total dos caminhos disjuntos: " + allDisjointPaths.size());
            for (List<Integer> path : allDisjointPaths) {
                System.out.println(path);
            }
            System.out.println("Tempo de execução: "+tempoTotal+" ms");
        } catch(Exception e){
            e.printStackTrace();
        }
        sc.close();
    }
}
