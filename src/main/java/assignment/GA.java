//package assignment;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//public class GA {
//
//    public static void main(String[] args) {
//
//        Individual[] population = new Individual[100];
//        //list of weights: [maxHeight, linesCleared]
//        for (int i = 0; i < population.length; i++) {
//            population[i] = new Individual(Math.random(), Math.random());
//        }
//        int[] scores = new int[population.length];
//        Individual[] offsprings = new Individual[population.length];
////        Individual[] parent1 = new Individual[5];
////        Individual[] parent2 = new Individual[5];
//        int bottomIndex;
//        int maxIndex1;
//        int maxScore1;
//        int maxIndex2;
//        int maxScore2;
//        while (true) {
//            for (int i = 0; i < population.length; i++) {
//                //fitness function just calculates the score
//                scores[i] = population[i].getFitness();
//            }
//            for (int epoch = 0; epoch < 100; epoch++) {
//                bottomIndex = (int) (Math.random() * 95);
//                maxScore1 = -1;
//                maxIndex1 = -1;
//                for (int j = bottomIndex; j < bottomIndex+5; j++) {
//                    if (scores[j] > maxScore1) {
//                        maxScore1 = scores[j];
//                        maxIndex1 = j;
//                    }
//                }
//                bottomIndex = (int) (Math.random() * 95);
//                maxScore2 = -1;
//                maxIndex2 = -1;
//                for (int j = bottomIndex; j < bottomIndex+5; j++) {
//                    if (scores[j] > maxScore2) {
//                        maxScore2 = scores[j];
//                        maxIndex2 = j;
//                    }
//                }
//                offsprings[epoch] = new Individual(population[maxIndex1].getWeight().get(0), population[maxIndex2].getWeight().get(0));
//            }
//            population = Arrays.copyOf(offsprings, offsprings.length);
//            offsprings = new Individual[population.length];
//        }
//    }
//}