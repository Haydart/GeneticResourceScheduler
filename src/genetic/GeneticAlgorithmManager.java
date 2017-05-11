package genetic;

import javafx.util.Pair;
import ms_rcpsp_lib.src.msrcpsp.evaluation.BaseEvaluator;
import ms_rcpsp_lib.src.msrcpsp.evaluation.DurationEvaluator;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Resource;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;
import ms_rcpsp_lib.src.msrcpsp.scheduling.greedy.Greedy;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * Created by r.makowiecki on 15/03/2017.
 */
class GeneticAlgorithmManager {

    private static final int POPULATION_SIZE = 200;
    static final int GENERATIONS_COUNT = 500;
    private static final float CROSSOVER_PROBABILITY = .2f;
    private static final float MUTATION_PROBABILITY = .0085f;
    private static final int TOURNAMENT_SIZE = 10;

    private static SecureRandom random = new SecureRandom();
    private Schedule[] population;
    private Schedule[] nextGenPopulation;

    //current population fitness
    private double[] populationFitness;

    //statistics
    private double[] generationBestFitnesses;
    private double[] generationAverageFitnesses;
    private double[] generationWorstFitnesses;
    private double everBestFitness = Double.MAX_VALUE;
    private double everWorstFitness = Double.MIN_VALUE;
    private double averageAverageFitness;

    //schedule with constraints to generate from
    private Schedule caseDescriptionSchedule;
    private Task[] caseDescriptionTasks;
    private int[] caseDescriptionUpperBounds;

    GeneticAlgorithmManager(Schedule caseDescriptionSchedule) {
        this.caseDescriptionSchedule = caseDescriptionSchedule;
        caseDescriptionTasks = caseDescriptionSchedule.getTasks().clone();
        caseDescriptionUpperBounds = caseDescriptionSchedule.getUpperBounds(caseDescriptionSchedule.getTasks().length);

        populationFitness = new double[POPULATION_SIZE];
        generationBestFitnesses = new double[GENERATIONS_COUNT];
        generationAverageFitnesses = new double[GENERATIONS_COUNT];
        generationWorstFitnesses = new double[GENERATIONS_COUNT];

        population = new Schedule[POPULATION_SIZE];
        nextGenPopulation = new Schedule[POPULATION_SIZE];
    }

    void run() {
        int generationNumber = 0;

        initializeValidPopulation();
        while (generationNumber < GENERATIONS_COUNT) {
            evaluatePopulationAndCollectStatistics(generationNumber);
            selectPopulation();
            crossPopulationOver();
            mutatePopulation();

            generationNumber++;
        }
        printStatistics();
    }

    private void printStatistics() {
        for (int i = 0; i < GENERATIONS_COUNT; i++) {
            averageAverageFitness += generationAverageFitnesses[i];
        }
        averageAverageFitness /= GENERATIONS_COUNT;
        System.out.println("Overall - best: " + everBestFitness + ", average: " + averageAverageFitness + ", worst: " + everWorstFitness);
    }

    //INITIALIZATION
    private void initializeValidPopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = generateValidSpecimen();
        }
    }

    private Schedule generateValidSpecimen() {
        List<Resource> capableResources;

        Schedule resultSchedule = new Schedule(caseDescriptionSchedule);

        for (int i = 0; i < caseDescriptionTasks.length; i++) {
            capableResources = resultSchedule.getCapableResources(caseDescriptionSchedule.getTasks().clone()[i]);
            resultSchedule.assign(resultSchedule.getTasks().clone()[i], capableResources.get((int) (random.nextDouble() * caseDescriptionUpperBounds[i])));
        }
        return resultSchedule;
    }

    //EVALUATION
    private void evaluatePopulationAndCollectStatistics(int generationNumber) {
        double bestGenerationFitness = Double.MAX_VALUE;
        double worstGenerationFitness = Double.MIN_VALUE;
        double averageGenerationFitness = 0;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            double currentEvaluation = evaluateSchedule(population[i]);
            populationFitness[i] = currentEvaluation;
            averageGenerationFitness += currentEvaluation;

            if (currentEvaluation < bestGenerationFitness) {
                bestGenerationFitness = currentEvaluation;
            }
            if (currentEvaluation > worstGenerationFitness) {
                worstGenerationFitness = currentEvaluation;
            }
            if(currentEvaluation < everBestFitness) {
                everBestFitness = currentEvaluation;
            }
            if(currentEvaluation > everWorstFitness) {
                everWorstFitness = currentEvaluation;
            }
        }
        averageGenerationFitness /= POPULATION_SIZE;

        System.out.println("GEN: " + generationNumber + " -- Best: " + bestGenerationFitness + ", Avg: " + averageGenerationFitness + ", Worst: " + worstGenerationFitness);
        generationBestFitnesses[generationNumber] = bestGenerationFitness;
        generationAverageFitnesses[generationNumber] = averageGenerationFitness;
        generationWorstFitnesses[generationNumber] = worstGenerationFitness;
    }

    private double evaluateSchedule(Schedule evaluatedSchedule) {
        Greedy greedy = new Greedy(evaluatedSchedule.getSuccesors());
        greedy.buildTimestamps(evaluatedSchedule);
        BaseEvaluator evaluator = new DurationEvaluator(evaluatedSchedule);
        return evaluator.evaluate();
    }

    //SELECTION
    //ROULETTE
    private void selectPopulation() {
        int[] nextGenerationParentIndexes = selectUsingTournament();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            nextGenPopulation[i] = new Schedule(population[nextGenerationParentIndexes[i]]);
        }
        population = deepCopyOf(nextGenPopulation);
    }

    private int[] selectUsingRoulette() {
        double[] cumulativeFitnesses = new double[POPULATION_SIZE];
        cumulativeFitnesses[0] = getAdjustedFitness(populationFitness[0]);
        for (int i = 1; i < POPULATION_SIZE; i++) {
            double fitness = getAdjustedFitness(populationFitness[i]);
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        int[] selection = new int[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0) {
                index = Math.abs(index + 1);
            }
            selection[i] = index;
        }
        return selection;
    }

    private double getAdjustedFitness(double populationFitness) {
        return populationFitness == 0 ? Double.POSITIVE_INFINITY : 1 / populationFitness;
    }

    //TOURNAMENT
    private int[] selectUsingTournament() {
        int[] selection = new int[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            selection[i] = performTournament();
        }
        return selection;
    }

    private int performTournament() {
        int bestIndex = -1;
        double bestFitness = Double.MAX_VALUE;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomCandidateIndex = random.nextInt(POPULATION_SIZE);
            if (populationFitness[randomCandidateIndex] < bestFitness) {
                bestIndex = randomCandidateIndex;
                bestFitness = populationFitness[bestIndex];
            }
        }
        return bestIndex;
    }

    //CROSSING OVER
    private void crossPopulationOver() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (random.nextDouble() < Math.abs(CROSSOVER_PROBABILITY)) {
                int crossingPartnerIndex = random.nextInt(POPULATION_SIZE);
                Pair<Schedule, Schedule> childPair = performCrossover(population[i], population[crossingPartnerIndex]);
                population[i] = new Schedule(childPair.getKey());
                population[crossingPartnerIndex] = new Schedule(childPair.getValue());
            }
        }
    }

    private static Pair<Schedule, Schedule> performCrossover(Schedule parent1, Schedule parent2) {
        Schedule child1 = new Schedule(parent1);
        Schedule child2 = new Schedule(parent2);
        Task[] child1Tasks = child1.getTasks();
        Task[] child2Tasks = child2.getTasks();

        Task[] parent1Tasks = parent1.getTasks();
        Task[] parent2Tasks = parent2.getTasks();

        int crossPoint = random.nextInt(parent1.getResources().length - 1) + 1;

        for (int i = 0; i < parent1Tasks.length; i++) {
            if (i < crossPoint) {
                child1Tasks[i] = parent1Tasks[i];
                child2Tasks[i] = parent2Tasks[i];
            } else {
                child1Tasks[i] = parent2Tasks[i];
                child2Tasks[i] = parent1Tasks[i];
            }
        }
        Pair<Schedule, Schedule> children = new Pair<>(child1, child2);
        return children;
    }

    //MUTATION
    private void mutatePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            mutate(population[i]);
        }
    }

    private void mutate(Schedule mutatingSchedule) {
        for (int i = 0; i < mutatingSchedule.getTasks().length; i++) {
            if (random.nextDouble() < Math.abs(MUTATION_PROBABILITY)) {
                List<Resource> capableResources;
                capableResources = mutatingSchedule.getCapableResources(mutatingSchedule.getTasks()[i]);
                mutatingSchedule.assign(mutatingSchedule.getTasks()[i], capableResources.get((int) (random.nextDouble() * caseDescriptionUpperBounds[i])));
            }
        }
    }

    private Task[] deepCopyOf(Task[] array) {
        Task[] resultArray = new Task[array.length];
        for (int i = 0; i < array.length; i++) {
            Task task = array[i];
            resultArray[i] = new Task(task.getId(), task.getRequiredSkills(), task.getDuration(), task.getStart(), task.getPredecessors().clone(), task.getResourceId());
        }
        return resultArray;
    }

    private Schedule[] deepCopyOf(Schedule[] array) {
        Schedule[] resultArray = new Schedule[array.length];
        for (int i = 0; i < array.length; i++) {
            resultArray[i] = new Schedule(array[i]);
        }
        return resultArray;
    }

    double[] getBestSpecimenData() {
        return generationBestFitnesses;
    }

    double[] getAverageSpecimenData() {
        return generationAverageFitnesses;
    }

    double[] getWorstSpecimenData() {
        return generationWorstFitnesses;
    }
}
