import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class DFS{

    static class Grafo{
        private int V; // Num Vértices.
        private LinkedList<Integer> adj[];
        private int arestaAnalise;

        Grafo(int v, int arestaAnalise){
            this.arestaAnalise = arestaAnalise;
            V=v;
            adj = new LinkedList[v+1];
            for(int i=0; i<v+1; ++i)
            adj[i] = new LinkedList();
        }

        // Adiciona os vértices adjacentes do vértice v.
        void addAresta(int v, int w){
            adj[v].add(w);
        }


        void DFS(int vertice) {
            boolean visitado[] = new boolean[V + 1];
            int tempo = 1;
            int TD[] = new int[V + 1];
            int TT[] = new int[V + 1];
            int pai[] = new int[V + 1];
            visitado[vertice] = true;
            Set<String> visitedEdges = new HashSet<>(); // Armazera arestas visitadas como strings "v->adjacente".

            List<String> arestaRetorno = new ArrayList<String>();
            List<String> arestaCruzamento = new ArrayList<String>();
            List<String> arestaAvanco = new ArrayList<String>();
        
            Stack<Integer> pilha = new Stack<>();
            pilha.push(vertice);
        
            // Busca em sí.
            while (!pilha.isEmpty()) {
                int v = pilha.peek();
                boolean flag = true;
        
                if (TD[v] == 0) {
                    TD[v] = tempo++;
                }
        
                for (Integer adjacente : adj[v]) {
                    if (TD[adjacente] == 0) { // Verifica se foi marcado.
                        String edge = v + "->" + adjacente;
                        if (!visitedEdges.contains(edge)) {
                            System.out.println("Aresta de árvore: " + edge);
                            visitedEdges.add(edge);
                        }
                        pilha.push(adjacente);
                        pai[adjacente] = v;
                        visitado[adjacente] = true;
                        flag = false;
                        break;
                    } else if (v == arestaAnalise) {// Verifica se é o vértice escolhido pelo usuário.
                        if (TT[adjacente] == 0) {
                            String edge = v + "->" + adjacente;
                            if (!visitedEdges.contains(edge)) {
                                arestaRetorno.add(edge);
                                visitedEdges.add(edge);
                            }
                        } else if (TD[v] < TD[adjacente]) {
                            String edge = v + "->" + adjacente;
                            if (!visitedEdges.contains(edge)) {
                                arestaAvanco.add(edge);
                                visitedEdges.add(edge);
                            }
                        } else {
                            String edge = v + "->" + adjacente;
                            if (!visitedEdges.contains(edge)) {
                                arestaCruzamento.add(edge);
                                visitedEdges.add(edge);
                            }
                        }
                    }
                }
        
                if (flag) { // Caso true, significa que o vértice em questão foi completamente explorado.
                    TT[v] = tempo++;
                    pilha.pop();
                }
            }
            System.out.println("----------------------");
            System.out.println("Arestas de Retorno do vértice "+arestaAnalise+" ");
            for(int i=0; i<arestaRetorno.size(); i++){
                System.out.println(arestaRetorno.get(i));
            }
            System.out.println("----------------------");
            System.out.println("Arestas de Cruzamento do vértice "+arestaAnalise+" ");
            for(int i=0; i<arestaCruzamento.size(); i++){
                System.out.println(arestaCruzamento.get(i));
            }
            System.out.println("----------------------");
            System.out.println("Arestas de Avanço do vértice "+arestaAnalise+" ");
            for(int i=0; i<arestaAvanco.size(); i++){
                System.out.println(arestaAvanco.get(i));
            }
            System.out.println("----------------------");
        }
        
    }
    public static void main(String[] args) {
        String nomeArq;
        System.out.println("Digite o nome do arquivo desejado: ");
        Scanner sc = new Scanner(System.in);
        nomeArq = sc.nextLine();
        System.out.println("Digite o número do vértice que deseja analisar suas arestas: ");
        int arestaAnalise;
        String temp1 = sc.nextLine();
        arestaAnalise = Integer.parseInt(temp1);
        try{
            BufferedReader br = new BufferedReader(new FileReader(nomeArq));
            String linha;
            int n,m;
            n=m=0;
            linha = br.readLine();
            Scanner scTxt = new Scanner(linha);
            n = scTxt.nextInt(); // Leio #V
            m = scTxt.nextInt(); // Leio #E

            Grafo graph = new Grafo(n, arestaAnalise);

            while((linha=br.readLine()) != null){
                Scanner scLin = new Scanner(linha);
                int v = scLin.nextInt();
                int w = scLin.nextInt();
                graph.addAresta(v, w); // Adiciono as arestas no grafo / lista de Adjacência.
            }

            System.out.println("----------------------");
            System.out.println("Arestas de Árvore: ");
            System.out.println("----------------------");
            graph.DFS(1);


        }catch (Exception e){
            e.printStackTrace();
        }
        sc.close();
    }
}