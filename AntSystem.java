/*
* Trabalho para a Disciplina SCC 0630 - Inteligência Artificial
* Professor: João Luís Garcia Rosa
* * * * * * * * Grupo: * * * * * * * * 
*** Nome:                                     No. USP
*** Elisa Jorge Marcatto                      7961965
*** Giuliano Barbosa Prado                    7961109
*** Henrique de Almeida Machado da Silveira   7961089
*** Lucas Tomazela                            8124271
*** Victor Marcelino Nunes                    8622381
**************************************
* TEMA: COLÔNIA DE FORMIGAS
*/


import java.util.Random;

// Classe que representa nosso AntSystem - "motor do algoritmo"
public class AntSystem {

    private Random random;

    private int alpha;

    private int beta;

    private double rho;

    private double Q;

    private int numOfIterations;

    private int numOfCities;

    private Graph graph;

    private int[] bestTrailSoFar;

    private int bestLengthSoFar;

    private int currBestLengthSoFar;

    private Ant[] ants;

    private int numOfAnts;

    // Construtor que inicializa as variaveis de classe
    public AntSystem(int numAnts, int a, int b, double r, double q, int numOfIter, Graph g, int numberOfCities) {
        random = new Random(System.currentTimeMillis());
        graph = g;
        numOfCities = numberOfCities;
        numOfAnts = numAnts;
        ants = new Ant[numOfAnts];

        for (int i = 0; i < numOfAnts; i++) {
            ants[i] = new Ant(numOfCities);
        }

        alpha = a;
        beta = b;
       
        Q = q;
        numOfIterations = numOfIter;
        
        bestTrailSoFar = new int[numberOfCities];
        bestTrailSoFar = bestTrail();
        bestLengthSoFar = currBestLengthSoFar;
        rho = r;
        g.initializePheromones(numOfAnts, bestLengthSoFar);
        run();
    }

    // Executa o algoritmo
    public void run() {
        
        System.out.println("\nMelhor caminho inicial:");
        display(bestTrailSoFar);
        System.out.println("\nMelhor comprimento inicial: "  + bestLengthSoFar + "\n");
        int currentIteration = 0;
        
        // Durante um numero predeterminado de iteracoes, busca novas
        // configuracoes de caminho para as formigas
        while (currentIteration < numOfIterations) {
            
            // Atualiza o caminho de cada formiga e os feromonios no grafo
            updateAntsTrail();
            graph.updatePheromones(ants, rho, Q);

            // Avalia se uma melhor solucao foi encontrada
            int[] currBestTrail = bestTrail();
            if (currBestLengthSoFar < bestLengthSoFar) {
                bestLengthSoFar = currBestLengthSoFar;
                bestTrailSoFar = currBestTrail;
                System.out.println("Novo melhor comprimento: " + bestLengthSoFar + " encontrado na iteração: " + currentIteration);
            }
            currentIteration++;
        }
        
        // Mostra o resultado final
        System.out.println("\nFim do Loop\nMelhor caminho encontrado nessa execução:");
        display(bestTrailSoFar);
        System.out.println("\nComprimento do melhor caminho: " + bestLengthSoFar);
    }

    // Procura o melhor caminho a cada iteracao
    private int[] bestTrail() {
        currBestLengthSoFar = ants[0].getLengthOfTrail(graph.getDistances());
        int indexBestLength = 0;
        for (int k = 1; k < numOfAnts; k++) {
            int len = ants[k].getLengthOfTrail(graph.getDistances());
            if (len < currBestLengthSoFar) {
                currBestLengthSoFar = len;
                indexBestLength = k;
            }
        }
        int[] bestTrailNow = ants[indexBestLength].getTrail();
        return bestTrailNow;
    }

    // Atualiza o caminho de cada formiga
    private void updateAntsTrail() {
        for (int k = 0; k < numOfAnts; k++) {
            int start = random.nextInt(numOfCities);
            int[] newTrail = BuildTrail(k, start, graph.getPheromones(), graph.getDistances());
            ants[k].setTrail(newTrail);
        }
    }
    
    // Constroi novo caminho para uma formiga
    private int[] BuildTrail(int k, int start, double[][] pheromones, int[][] dists) {

        int[] trail = new int[numOfCities];
        boolean[] visited = new boolean[numOfCities];
        trail[0] = start;
        visited[start] = true;
        for (int i = 0; i < numOfCities - 1; i++) {
            int currentCity = trail[i];
            int nextCity = nextCity(k, currentCity, visited, pheromones, dists);
            trail[i + 1] = nextCity;
            visited[nextCity] = true;
        }
        return trail;
    }

    // Logica para escolher a proxima cidade, levando em conta a probabilidade
    // de escolher algum caminho. Aqui ha, de fato, a implementacao do calculo
    // da probabilidade de se escolher um caminho, juntamente com o metodo
    // calculateProbabilities()
    private int nextCity(int k, int currentCity, boolean[] visited, double[][] pheromones, int[][] dists) {
        double[] probs = calculateProbabilities(k, currentCity, visited, pheromones, dists);

        double[] cumulativeProbabilities = new double[probs.length + 1];
        cumulativeProbabilities[0] = 0.0;
        for (int i = 0; i < numOfCities; i++) {
            cumulativeProbabilities[i + 1] = cumulativeProbabilities[i] + probs[i];
        }

        double p = random.nextDouble();

        for (int i = 0; i <= cumulativeProbabilities.length - 2; i++) {
            if (p >= cumulativeProbabilities[i] && p < cumulativeProbabilities[i + 1]) {
                return i;
            }
        }
        return -1;

    }

    // Implementacao do calculo da probabilidade de se escolher um caminho
    private double[] calculateProbabilities(int k, int currentCity, boolean[] visited, double[][] pheromones, int[][] dists) {
        double[] taueta = new double[numOfCities];
        // inclues cityX and visited cities
        double sum = 0.0;
        // sum of all tauetas
        // i is the adjacent city
        for (int i = 0; i < numOfCities; i++) {
            if (i == currentCity || visited[i] == true) {
                taueta[i] = 0.0;
                // prob of moving to self is 0
                // prob of moving to a visited city is 0
            } else {

                taueta[i] = Math.pow(pheromones[currentCity][i], alpha) * Math.pow((1.0 / dists[currentCity][i]), beta);
                // could be huge when pheromone[][] is big
                if (taueta[i] < 0.0001) {
                    taueta[i] = 0.0001;
                } else if (taueta[i] > (Double.MAX_VALUE / (numOfCities * 100))) {
                    taueta[i] = Double.MAX_VALUE / (numOfCities * 100);
                }
            }
            sum += taueta[i];
        }

        double[] probs = new double[numOfCities];
        for (int i = 0; i <= probs.length - 1; i++) {
            probs[i] = taueta[i] / sum;
            // big trouble if sum = 0.0
        }
        return probs;
    }

    // Mostra o caminho na tela
    private static void display(int[] trail) {
        for (int i = 0; i < trail.length; i++) {
            System.out.print(trail[i] + " ");
        }
        System.out.println("");
    }

}
