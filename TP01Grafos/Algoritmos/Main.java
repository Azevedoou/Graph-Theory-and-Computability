import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

class Main {
    public static Grafo grafo;
    public static int V, E, corr=0;
    public static void LeituraGrafo (String nomeArq) {
    try {
      BufferedReader arq = new BufferedReader(new FileReader(nomeArq));
      String linha[] = arq.readLine().trim().replaceAll("\\s+"," ").split(" ");
      V = Integer.parseInt(linha[0]);
      grafo = new Grafo(V);
      E = Integer.parseInt(linha[1]);
      for(int linhaArq=0; linhaArq < E; linhaArq++){
        linha = arq.readLine().trim().replaceAll("\\s+"," ").split(" ");
        if(linhaArq==0){
            if(Integer.parseInt(linha[0])==1){
                corr=1;
            }
        }
        grafo.adicionarAresta(Integer.parseInt(linha[0])-corr, Integer.parseInt(linha[1])-corr, Integer.parseInt(linha[2]));
      }
      arq.close();
    } catch (IOException e) {
        System.out.println("Arquivo não encontrado no diretório!");
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
  }

  // Main
  public static void main(String[] args) {
    // Abrir Scanner e ler o nome do arquivo
    Scanner sc = new Scanner(System.in);
    System.out.println("Digite a opção de grafo: \n1-Gerar grafo aleatório\n2-Inserir nome de arquivo já existente.");
    int varGraf = Integer.parseInt(sc.nextLine());
    if(varGraf == 2){
        System.out.print("Digite o nome do arquivo desejado: ");
        String nomeArq = sc.nextLine();
        LeituraGrafo(nomeArq); // Fazer a leitura do arquivo e gerar o grafo
    }
    else{
        System.out.print("Digite a quantidade de vértices do grafo: ");
        int totalVer= Integer.parseInt(sc.nextLine());
        grafo = new Grafo(totalVer);
        GrafoGIRG.generateGIRG(grafo, 5.0, totalVer, 10.0);
    }
    System.out.println("Digite o método que deseja executar: \n1-Edmonds\n2-Tarjan\n3-Gabow");
    int switchCaseChoice = Integer.parseInt(sc.nextLine());
    switch (switchCaseChoice) {
        case 1: // Edmonds
            Edmonds msa = new Edmonds();
            int raiz = 0;
            long inicio = System.currentTimeMillis();
            int[] minimumSpanningArborescenceEdmonds = msa.acharAGMEdmonds(grafo, raiz);
            long tempoTotal = System.currentTimeMillis() - inicio;
            int pesoTotal = 0;
            Grafo grafoFSREdmonds = new Grafo(minimumSpanningArborescenceEdmonds.length);
            if (minimumSpanningArborescenceEdmonds != null) {
                System.out.println("Arestas na Arborescência Geradora Mínima:");
                for (int i = raiz; i < minimumSpanningArborescenceEdmonds.length; i++) {
                    if (i != raiz) {
                        Aresta aresta = grafo.getArestas().get(minimumSpanningArborescenceEdmonds[i]);
                        System.out.println(aresta.origem + " -> " + aresta.destino + " (Peso: " + aresta.peso + ")");
                        pesoTotal += aresta.peso;
                        grafoFSREdmonds.adicionarAresta(aresta.origem, aresta.destino, aresta.peso);
                    }
                }
                System.out.println("Peso total do caminho: "+ pesoTotal);
                System.out.println("Tempo total gasto: " + tempoTotal + " ms" );

                System.out.println("Deseja fazer o teste FastSpeedRun?\n1-Sim\n2-Não");
                int tempFSREdmonds = Integer.parseInt(sc.nextLine());
                if(tempFSREdmonds == 1){
                    System.out.println("Digite a origem do vértice que deseja analisar:");
                    int fsrOrigemTest = Integer.parseInt(sc.nextLine());
                    System.out.println("Digite o destino do vértice que deseja analisar:");
                    int fsrDestinoTest = Integer.parseInt(sc.nextLine());

                    long inicioFSR = System.currentTimeMillis();
                    List<Integer> resultFSREdmonds = FastSpeedRun.fastSpeedrunTest(fsrOrigemTest, fsrDestinoTest, grafoFSREdmonds);
                    long fimFSR = System.currentTimeMillis() - inicioFSR;
                    System.out.print("Caminho: ");
                    for(int i=0; i<resultFSREdmonds.size(); i++){
                        System.out.print(resultFSREdmonds.get(i) + " | ");
                    }
                    System.out.println("\nTempo de execução FastSpeedRun Test: " + fimFSR + " ms");
                }
            } 
            else {
              System.out.println("Não é uma arborescência válida.");
            }
        break;
        case 2: // Tarjan
            inicio = System.currentTimeMillis();
            List<Aresta> AGM = Tarjan.tarjanMST(grafo);
            tempoTotal = System.currentTimeMillis() - inicio;
            System.out.println("Arborescência Geradora Mínima:");
            pesoTotal = 0;
            Grafo grafoFSRTarjan = new Grafo(AGM.size());
            for (Aresta edge : AGM) {
                System.out.println(edge.origem + " -> " + edge.destino + " (Peso: " + edge.peso + ")");
                pesoTotal += edge.peso;
                grafoFSRTarjan.adicionarAresta(edge.origem, edge.destino, edge.peso);
            }
            System.out.println("Peso total do caminho: "+ pesoTotal);
            System.out.println("Tempo total gasto: " + tempoTotal + " ms" );

            System.out.println("Deseja fazer o teste FastSpeedRun?\n1-Sim\n2-Não");
            int tempFSRTarjan = Integer.parseInt(sc.nextLine());
            if(tempFSRTarjan == 1){
                System.out.println("Digite a origem do vértice que deseja analisar:");
                int fsrOrigemTest = Integer.parseInt(sc.nextLine());
                System.out.println("Digite o destino do vértice que deseja analisar:");
                int fsrDestinoTest = Integer.parseInt(sc.nextLine());

                long inicioFSR = System.currentTimeMillis();
                List<Integer> resultFSRTarjan = FastSpeedRun.fastSpeedrunTest(fsrOrigemTest, fsrDestinoTest, grafoFSRTarjan);
                long fimFSR = System.currentTimeMillis() - inicioFSR;
                System.out.print("Caminho: ");
                for(int i=0; i<resultFSRTarjan.size(); i++){
                    System.out.print(resultFSRTarjan.get(i) + " | ");
                }
                System.out.println("\nTempo de execução FastSpeedRun Test: " + fimFSR + " ms");
            }
        break;
        case 3: //Gabow
            inicio = System.currentTimeMillis();
            List<Aresta> minimumSpanningArborescence = Gabow.acharAGMGabow(0, grafo);
            tempoTotal = System.currentTimeMillis() - inicio;
            System.out.println("Arborescência Geradora Mínima:");
            Grafo grafoFSRGabow = new Grafo(grafo.getVertice());
            pesoTotal = 0;
            for (int i=0; i<grafo.getVertice()-1;i++){
                System.out.println(minimumSpanningArborescence.get(i).origem + " -> " + minimumSpanningArborescence.get(i).destino + " (Peso: " + minimumSpanningArborescence.get(i).peso + ")");
                pesoTotal += minimumSpanningArborescence.get(i).peso;
                grafoFSRGabow.adicionarAresta(minimumSpanningArborescence.get(i).origem, minimumSpanningArborescence.get(i).destino, minimumSpanningArborescence.get(i).peso);
            }
            System.out.println("Peso total do caminho: "+ pesoTotal);
            System.out.println("Tempo total gasto: " + tempoTotal + " ms" );
            
            System.out.println("Deseja fazer o teste FastSpeedRun?\n1-Sim\n2-Não");
            int tempFSRGabow = Integer.parseInt(sc.nextLine());
            if(tempFSRGabow == 1){
                System.out.println("Digite a origem do vértice que deseja analisar:");
                int fsrOrigemTest = Integer.parseInt(sc.nextLine());
                System.out.println("Digite o destino do vértice que deseja analisar:");
                int fsrDestinoTest = Integer.parseInt(sc.nextLine());

                long inicioFSR = System.currentTimeMillis();
                List<Integer> resultFSRGabow = FastSpeedRun.fastSpeedrunTest(fsrOrigemTest, fsrDestinoTest, grafoFSRGabow);
                long fimFSR = System.currentTimeMillis() - inicioFSR;
                System.out.print("Caminho: ");
                for(int i=0; i<resultFSRGabow.size(); i++){
                    System.out.print(resultFSRGabow.get(i) + " | ");
                }
                System.out.println("\nTempo de execução FastSpeedRun Test: " + fimFSR + " ms");
            }
        break;
    }

    sc.close();
  }
}