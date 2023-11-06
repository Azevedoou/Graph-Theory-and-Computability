import java.util.*;

class Grafo {
  private int V, totalArestas=0;
  private List<Aresta> arestas;
  private List<List<Integer>> adjList;
  public Grafo (int V) {
    this.V = V;
    arestas = new ArrayList<Aresta>();
    adjList = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      adjList.add(new ArrayList<>());
    } 
  }

  public Grafo(List<Aresta> lista){
    
  }

  public void adicionarAresta(int origem, int destino, int peso) {
    arestas.add(new Aresta(origem, destino, peso));
    totalArestas++;
    adjList.get(origem).add(destino); // Adiciona destino na lista de adjacência de origem.
  }

    public int getTotalAresta(){
        return totalArestas;
    }

  public int getVertice() {
    return V;
  }

  public List<List<Integer>> getAdjList(){
    return adjList;
  }

  public List<Aresta> getArestas(){
    return arestas;
  }
}