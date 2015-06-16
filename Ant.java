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

// Classe que representa as formigas
public class Ant {

    private int[] trail;
    private Random random;
    private int numOfCities;
    private int trailLength;

    public Ant(int numCities) {

        numOfCities = numCities;
        random = new Random(System.currentTimeMillis());
        int startingCity = random.nextInt(numCities);
        trail = generateTrail(startingCity);

    }

    // Gera uma trilha aleatoria inicial
    private int[] generateTrail(int startingCity) {

        int[] trail = new int[numOfCities];

        // Gera trilha em ordem crescente
        for (int i = 0; i < numOfCities; i++) {
            trail[i] = i;
        }

        // Embaralha a trilha
        for (int i = 0; i < numOfCities; i++) {
            int r = random.nextInt(numOfCities - i) + i;
            int tmp = trail[r];
            trail[r] = trail[i];
            trail[i] = tmp;
        }

        // Garante com maior probabilidade que a trilha não inicie
        // com a cidade 0
        int index = findIndexOfStartingCity(trail, startingCity);

        if (index != -1) {
            int temp = trail[0];
            trail[0] = trail[index];
            trail[index] = temp;
        }

        return trail;
    }

    private int findIndexOfStartingCity(int[] trail, int target) {

        for (int i = 0; i < trail.length; i++) {
            if (trail[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public int getLengthOfTrail(int[][] dists) {

        int result = 0;
        for (int i = 0; i <= trail.length - 2; i++) {
            result += getDistance(trail[i], trail[i + 1], dists);
        }
        return result;
    }

    private int getDistance(int cityX, int cityY, int[][] dists) {
        return dists[cityX][cityY];
    }

    public int[] getTrail() {
        int[] buffer = new int[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            buffer[i] = trail[i];
        }
        return buffer;
    }

    public void setTrail(int[] trail) {
        this.trail = trail;
    }

}
