/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antcolony;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author henrisilver
 */
public class AntColony {

   private static Random random = new Random(0);
        // influence of pheromone on direction
        private static int alpha = 3;
        // influence of adjacent node distance
        private static int beta = 2;

        // pheromone decrease factor
        private static double rho = 0.01;
        // pheromone increase factor
        private static double Q = 2.0;

        public static void main(String[] args)
        {
            try
            {
                System.out.println("\nBegin Ant Colony Optimization demo\n");

                int numCities = 60;
                int numAnts = 4;
                int maxTime = 1000;

                System.out.println("Number cities in problem = " + numCities);

                System.out.println("\nNumber ants = " + numAnts);
                System.out.println("Maximum time = " + maxTime);

                System.out.println("\nAlpha (pheromone influence) = " + alpha);
                System.out.println("Beta (local node influence) = " + beta);
                System.out.println("Rho (pheromone evaporation coefficient) = " + "F2: " + String.valueOf(rho));
                System.out.println("Q (pheromone deposit factor) = " + "F2: " + String.valueOf(Q));

                System.out.println("\nInitialing dummy graph distances");
                int[][] dists = MakeGraphDistances(numCities);

                System.out.println("\nInitialing ants to random trails\n");
                int[][] ants = InitAnts(numAnts, numCities);
                // initialize ants to random trails
                ShowAnts(ants, dists);

                int[] bestTrail = AntColony.BestTrail(ants, dists);
                // determine the best initial trail
                double bestLength = Length(bestTrail, dists);
                // the length of the best trail

                System.out.println("\nBest initial trail length: " + "F1: " + String.valueOf(bestLength) + "\n");
                //Display(bestTrail);

                System.out.println("\nInitializing pheromones on trails");
                double[][] pheromones = InitPheromones(numCities);

                int time = 0;
                System.out.println("\nEntering UpdateAnts - UpdatePheromones loop\n");
                while (time < maxTime)
                {
                    UpdateAnts(ants, pheromones, dists);
                    UpdatePheromones(pheromones, ants, dists);

                    int[] currBestTrail = AntColony.BestTrail(ants, dists);
                    double currBestLength = Length(currBestTrail, dists);
                    if (currBestLength < bestLength)
                    {
                        bestLength = currBestLength;
                        bestTrail = currBestTrail;
                        System.out.println("New best length of " + "F1: " + String.valueOf(bestLength) + " found at time " + time);
                    }
                    time += 1;
                }

                System.out.println("\nTime complete");

                System.out.println("\nBest trail found:");
                Display(bestTrail);
                System.out.println("\nLength of best trail found: " + "F1: " + String.valueOf(bestLength));

                System.out.println("\nEnd Ant Colony Optimization demo\n");
                //Console.ReadLine();
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                //Console.ReadLine();
            }

        }
        // Main

        // --------------------------------------------------------------------------------------------

        private static int[][] InitAnts(int numAnts, int numCities) throws Exception
        {
            int[][] ants = new int[numAnts][];
            for (int k = 0; k <= numAnts - 1; k++)
            {
                int start = random.nextInt(numCities);
                ants[k] = RandomTrail(start, numCities);
            }
            return ants;
        }

        private static int[] RandomTrail(int start, int numCities) throws Exception
        {
            // helper for InitAnts
            int[] trail = new int[numCities];

            // sequential
            for (int i = 0; i <= numCities - 1; i++)
            {
                trail[i] = i;
            }

            // Fisher-Yates shuffle
            for (int i = 0; i <= numCities - 1; i++)
            {
                int r = random.nextInt(numCities - i) + i;
                int tmp = trail[r];
                trail[r] = trail[i];
                trail[i] = tmp;
            }

            int idx = IndexOfTarget(trail, start);
            // put start at [0]
            int temp = trail[0];
            trail[0] = trail[idx];
            trail[idx] = temp;

            return trail;
        }

        private static int IndexOfTarget(int[] trail, int target) throws Exception
        {
            // helper for RandomTrail
            for (int i = 0; i <= trail.length - 1; i++)
            {
                if (trail[i] == target)
                {
                    return i;
                }
            }
            throw new Exception("Target not found in IndexOfTarget");
        }

        private static double Length(int[] trail, int[][] dists)
        {
            // total length of a trail
            double result = 0.0;
            for (int i = 0; i <= trail.length - 2; i++)
            {
                result += Distance(trail[i], trail[i + 1], dists);
            }
            return result;
        }

        // -------------------------------------------------------------------------------------------- 

        private static int[] BestTrail(int[][] ants, int[][] dists)
        {
            // best trail has shortest total length
            double bestLength = Length(ants[0], dists);
            int idxBestLength = 0;
            for (int k = 1; k <= ants.length - 1; k++)
            {
                double len = Length(ants[k], dists);
                if (len < bestLength)
                {
                    bestLength = len;
                    idxBestLength = k;
                }
            }
            int numCities = ants[0].length;
            //INSTANT VB NOTE: The local variable bestTrail was renamed since Visual Basic will not allow local variables with the same name as their enclosing function or property:
            int[] bestTrail_Renamed;// = new int[numCities];
            bestTrail_Renamed = Arrays.copyOf(ants[idxBestLength], numCities);//ants[idxBestLength].copyTo(bestTrail_Renamed, 0);
            return bestTrail_Renamed;
        }

        // --------------------------------------------------------------------------------------------

        private static double[][] InitPheromones(int numCities)
        {
            double[][] pheromones = new double[numCities][];
            for (int i = 0; i <= numCities - 1; i++)
            {
                pheromones[i] = new double[numCities];
            }
            for (int i = 0; i <= pheromones.length - 1; i++)
            {
                for (int j = 0; j <= pheromones[i].length - 1; j++)
                {
                    pheromones[i][j] = 0.01;
                    // otherwise first call to UpdateAnts -> BuiuldTrail -> NextNode -> MoveProbs => all 0.0 => throws
                }
            }
            return pheromones;
        }

        // --------------------------------------------------------------------------------------------

        private static void UpdateAnts(int[][] ants, double[][] pheromones, int[][] dists) throws Exception
        {
            int numCities = pheromones.length;
            for (int k = 0; k <= ants.length - 1; k++)
            {
                int start = random.nextInt(numCities);
                int[] newTrail = BuildTrail(k, start, pheromones, dists);
                ants[k] = newTrail;
            }
        }

        private static int[] BuildTrail(int k, int start, double[][] pheromones, int[][] dists) throws Exception
        {
            int numCities = pheromones.length;
            int[] trail = new int[numCities];
            boolean[] visited = new boolean[numCities];
            trail[0] = start;
            visited[start] = true;
            for (int i = 0; i <= numCities - 2; i++)
            {
                int cityX = trail[i];
                int next = NextCity(k, cityX, visited, pheromones, dists);
                trail[i + 1] = next;
                visited[next] = true;
            }
            return trail;
        }

        private static int NextCity(int k, int cityX, boolean[] visited, double[][] pheromones, int[][] dists) throws Exception
        {
            // for ant k (with visited[]), at nodeX, what is next node in trail?
            double[] probs = MoveProbs(k, cityX, visited, pheromones, dists);

            double[] cumul = new double[probs.length + 1];
            for (int i = 0; i <= probs.length - 1; i++)
            {
                cumul[i + 1] = cumul[i] + probs[i];
                // consider setting cumul[cuml.Length-1] to 1.00
            }

            double p = random.nextDouble();

            for (int i = 0; i <= cumul.length - 2; i++)
            {
                if (p >= cumul[i] && p < cumul[i + 1])
                {
                    return i;
                }
            }
            throw new Exception("Failure to return valid city in NextCity");
        }

        private static double[] MoveProbs(int k, int cityX, boolean[] visited, double[][] pheromones, int[][] dists)
        {
            // for ant k, located at nodeX, with visited[], return the prob of moving to each city
            int numCities = pheromones.length;
            double[] taueta = new double[numCities];
            // inclues cityX and visited cities
            double sum = 0.0;
            // sum of all tauetas
            // i is the adjacent city
            for (int i = 0; i <= taueta.length - 1; i++)
            {
                if (i == cityX)
                {
                    taueta[i] = 0.0;
                    // prob of moving to self is 0
                }
                else if (visited[i] == true)
                {
                    taueta[i] = 0.0;
                    // prob of moving to a visited city is 0
                }
                else
                {
                   
                    taueta[i] = Math.pow(pheromones[cityX][i], alpha) * Math.pow((1.0 / Distance(cityX, i, dists)), beta);
                    // could be huge when pheromone[][] is big
                    if (taueta[i] < 0.0001)
                    {
                        taueta[i] = 0.0001;
                    }
                    else if (taueta[i] > (Double.MAX_VALUE / (numCities * 100)))
                    {
                        taueta[i] = Double.MAX_VALUE / (numCities * 100);
                    }
                }
                sum += taueta[i];
            }

            double[] probs = new double[numCities];
            for (int i = 0; i <= probs.length - 1; i++)
            {
                probs[i] = taueta[i] / sum;
                // big trouble if sum = 0.0
            }
            return probs;
        }

        // --------------------------------------------------------------------------------------------

        private static void UpdatePheromones(double[][] pheromones, int[][] ants, int[][] dists) throws Exception
        {
            for (int i = 0; i <= pheromones.length - 1; i++)
            {
                for (int j = i + 1; j <= pheromones[i].length - 1; j++)
                {
                    for (int k = 0; k <= ants.length - 1; k++)
                    {
                        double length = AntColony.Length(ants[k], dists);
                        // length of ant k trail
                        double decrease = (1.0 - rho) * pheromones[i][j];
                        double increase = 0.0;
                        if (EdgeInTrail(i, j, ants[k]) == true)
                        {
                            increase = (Q / length);
                        }

                        pheromones[i][j] = decrease + increase;

                        if (pheromones[i][j] < 0.0001)
                        {
                            pheromones[i][j] = 0.0001;
                        }
                        else if (pheromones[i][j] > 100000.0)
                        {
                            pheromones[i][j] = 100000.0;
                        }

                        pheromones[j][i] = pheromones[i][j];
                    }
                }
            }
        }

        private static boolean EdgeInTrail(int cityX, int cityY, int[] trail) throws Exception
        {
            // are cityX and cityY adjacent to each other in trail[]?
            int lastIndex = trail.length - 1;
            int idx = IndexOfTarget(trail, cityX);

            if (idx == 0 && trail[1] == cityY)
            {
                return true;
            }
            else if (idx == 0 && trail[lastIndex] == cityY)
            {
                return true;
            }
            else if (idx == 0)
            {
                return false;
            }
            else if (idx == lastIndex && trail[lastIndex - 1] == cityY)
            {
                return true;
            }
            else if (idx == lastIndex && trail[0] == cityY)
            {
                return true;
            }
            else if (idx == lastIndex)
            {
                return false;
            }
            else if (trail[idx - 1] == cityY)
            {
                return true;
            }
            else if (trail[idx + 1] == cityY)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        // --------------------------------------------------------------------------------------------

        private static int[][] MakeGraphDistances(int numCities)
        {
            int[][] dists = new int[numCities][];
            for (int i = 0; i <= dists.length - 1; i++)
            {
                dists[i] = new int[numCities];
            }
            for (int i = 0; i <= numCities - 1; i++)
            {
                for (int j = i + 1; j <= numCities - 1; j++)
                {
                    int d = random.nextInt(8) + 1;
                    // [1,8]
                    dists[i][j] = d;
                    dists[j][i] = d;
                }
            }
            return dists;
        }

        private static double Distance(int cityX, int cityY, int[][] dists)
        {
            return dists[cityX][cityY];
        }

        // --------------------------------------------------------------------------------------------

        private static void Display(int[] trail)
        {
            for (int i = 0; i <= trail.length - 1; i++)
            {
                System.out.print(trail[i] + " ");
                if (i > 0 && i % 20 == 0)
                {
                    System.out.println("");
                }
            }
            System.out.println("");
        }


        private static void ShowAnts(int[][] ants, int[][] dists)
        {
            for (int i = 0; i <= ants.length - 1; i++)
            {
                System.out.print(i + ": [ ");

                for (int j = 0; j <= 3; j++)
                {
                    System.out.print(ants[i][j] + " ");
                }

                System.out.print(". . . ");

                for (int j = ants[i].length - 4; j <= ants[i].length - 1; j++)
                {
                    System.out.print(ants[i][j] + " ");
                }

                System.out.print("] len = ");
                double len = Length(ants[i], dists);
                System.out.print("F4: " + String.valueOf(len));
                System.out.println("");
            }
        }

        private static void Display(double[][] pheromones)
        {
            for (int i = 0; i <= pheromones.length - 1; i++)
            {
                System.out.print(i + ": ");
                for (int j = 0; j <= pheromones[i].length - 1; j++)
                {
                    System.out.print("F4: " + String.valueOf(pheromones[i][j])/*.PadLeft(8) + */ + " ");
                }
                System.out.println("");
            }

        }


}
