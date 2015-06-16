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

// Classe que representa o grafo utilizado.
// Implementamos o algoritmo AntSystem aplicado ao problema do Caixeiro Viajante.
public class Graph {

    private int[][] dists;

    double[][] pheromones;

    int numberOfCities;

    public Graph(int num) {
        numberOfCities = num;
        createGraphDistances();
    }

    private void createGraphDistances() {
        Random random = new Random(System.currentTimeMillis());
        dists = new int[numberOfCities][];
        for (int i = 0; i < dists.length; i++) {
            dists[i] = new int[numberOfCities];
        }
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = i + 1; j < numberOfCities; j++) {
                int d = random.nextInt(numberOfCities) + 1;
                dists[i][j] = d;
                dists[j][i] = d;
            }
        }
    }

    public void initializePheromones(int numAnts, int bestLength) {
        pheromones = new double[numberOfCities][];
        for (int i = 0; i < numberOfCities; i++) {
            pheromones[i] = new double[numberOfCities];
        }
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromones[i][j] = numAnts/bestLength;

            }
        }
    }

    public int[][] getDistances() {
        return dists;
    }

    public double[][] getPheromones() {
        return pheromones;
    }

    // Metodos: updatePheromones() e edgeInTrail()
    // Contem a implementacao da atualizacao do nivel de feromonio
    // nos caminhos (atualizacao de tau em cada caminho)
    public void updatePheromones(Ant[] ants, double rho, double Q) {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = i + 1; j < numberOfCities; j++) {
                for (int k = 0; k < ants.length; k++) {
                    int length = ants[k].getLengthOfTrail(dists);
                    // length of ant k trail
                    double decrease = (1.0 - rho) * pheromones[i][j];
                    double increase = 0.0;
                    if (edgeInTrail(i, j, ants[k].getTrail()) == true) {
                        increase = (Q / length);
                    }

                    pheromones[i][j] = decrease + increase;

                    if (pheromones[i][j] < 0.0001) {
                        pheromones[i][j] = 0.0001;
                    } else if (pheromones[i][j] > 100000.0) {
                        pheromones[i][j] = 100000.0;
                    }

                    pheromones[j][i] = pheromones[i][j];
                }
            }
        }
    }

    private boolean edgeInTrail(int cityX, int cityY, int[] trail) {
        // are cityX and cityY adjacent to each other in trail[]?
        int lastIndex = trail.length - 1;
        int idx = indexOfTarget(trail, cityX);

        if (idx == 0 && trail[1] == cityY) {
            return true;
        } else if (idx == 0 && trail[lastIndex] == cityY) {
            return true;
        } else if (idx == 0) {
            return false;
        } else if (idx == lastIndex && trail[lastIndex - 1] == cityY) {
            return true;
        } else if (idx == lastIndex && trail[0] == cityY) {
            return true;
        } else if (idx == lastIndex) {
            return false;
        } else if (trail[idx - 1] == cityY) {
            return true;
        } else if (trail[idx + 1] == cityY) {
            return true;
        } else {
            return false;
        }
    }

    private int indexOfTarget(int[] trail, int target) {
        // helper for RandomTrail
        for (int i = 0; i <= trail.length - 1; i++) {
            if (trail[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
