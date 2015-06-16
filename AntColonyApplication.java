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

// Implementacao do algoritmo AntSystem
public class AntColonyApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Parametros indicados na tabela da Figura 2 do relatorio para o
        // algoritmo AS
        int numberOfCities = 30;
        int numberOfAnts = 30;
        int alpha = 1;
        int beta = 5;
        double rho = 0.5;
        double Q = 2.0;
        int numOfIterations = 1000;

        // Criacao do grafo que sera usado pelo probema
        Graph g = new Graph(numberOfCities);

        // Experimentos - caso de simulacao
        // 1 - Varia-se alfa e beta
        System.out.println("*****SIMULATION 1.1******\n");
        AntSystem simulation11 = new AntSystem(numberOfAnts, 1, 5, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 1.2******\n");
        AntSystem simulation12 = new AntSystem(numberOfAnts, 2, 4, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 1.3******\n");
        AntSystem simulation13 = new AntSystem(numberOfAnts, 3, 3, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 1.4******\n");
        AntSystem simulation14 = new AntSystem(numberOfAnts, 4, 2, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 1.5******\n");
        AntSystem simulation15 = new AntSystem(numberOfAnts, 5, 1, rho, Q, numOfIterations, g, numberOfCities);
        
        // 2 - Varia-se o numero de formigas
        System.out.println("*****SIMULATION 2.1******\n");
        AntSystem simulation21 = new AntSystem(10, alpha, beta, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 2.2******\n");
        AntSystem simulation22 = new AntSystem(20, alpha, beta, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 2.3******\n");
        AntSystem simulation23 = new AntSystem(30, alpha, beta, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 2.4******\n");
        AntSystem simulation24 = new AntSystem(40, alpha, beta, rho, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 2.5******\n");
        AntSystem simulation25 = new AntSystem(50, alpha, beta, rho, Q, numOfIterations, g, numberOfCities);
        
        // 3 - Varia-se o coeficiente de decaimento de feromonio, rho
        System.out.println("*****SIMULATION3.1******\n");
        AntSystem simulation31 = new AntSystem(numberOfAnts, alpha, beta, 0.1, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 3.2******\n");
        AntSystem simulation32 = new AntSystem(numberOfAnts, alpha, beta, 0.5, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 3.3******\n");
        AntSystem simulation33 = new AntSystem(numberOfAnts, alpha, beta, 1.0, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 3.4******\n");
        AntSystem simulation34 = new AntSystem(numberOfAnts, alpha, beta, 5.0, Q, numOfIterations, g, numberOfCities);
        System.out.println("\n\n\n*****SIMULATION 3.5******\n");
        AntSystem simulation35 = new AntSystem(numberOfAnts, alpha, beta, 10.0, Q, numOfIterations, g, numberOfCities);

    }

}
