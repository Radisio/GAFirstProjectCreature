import Game.Environment.Environment;
import Game.Environment.EnvironmentBuilder;
import Game.Environment.EnvironmentUtil;
import Game.Game;
import GeneticAlgorithm.*;
import GeneticAlgorithm.Configuration.CrossOverConfig;
import GeneticAlgorithm.Configuration.GAYamlMapper;
import GeneticAlgorithm.Configuration.SelectionConfig;
import GeneticAlgorithm.CrossOver.CrossOverMapper;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Selection.SelectionMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TunningParameters {

    private static void writeHistoryToFile(String path, String log) throws IOException {
        FileWriter f = new FileWriter(path);
        f.write(log);
        f.flush();
        f.close();
    }
    private static List<Environment> getTestEnvironment(String sourcePath,int nb)
    {
        List<Environment> returnedList = new ArrayList<>();
        for(int i =0;i<nb;i++)
        {
            String pathFile = sourcePath +"\\"+i+".txt";
            if(new File(pathFile).exists())
            {
                returnedList.add(EnvironmentUtil.readEnvironmentFromFile(pathFile));
            }
        }
        if(returnedList.size() != nb)
        {
            int i = returnedList.size();
            for(;i<nb;i++)
            {
                String pathFile = sourcePath +"\\"+i+".txt";
                Environment e = new EnvironmentBuilder().build();
                try {
                    EnvironmentUtil.writeEnvironmentToFile(e, pathFile);
                    returnedList.add(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return returnedList;
    }
    private static String buildFullPath(String path, int nbCrea, int tournaSizeParent, int tournaSizeEvolve, int nEnvironment, String parentSelect,
                                 String evolveSelect,String cross, double crossOverRate, double mutationAddRate, double mutationSubRate, double mutationFlipRate,
                                 double percentageParentsToKeep)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(path).append("\\");
        sb.append(nbCrea).append("-");
        sb.append(tournaSizeParent).append("-");
        sb.append(tournaSizeEvolve).append("-");
        sb.append(nEnvironment).append("-");
        sb.append(parentSelect).append("-");
        sb.append(evolveSelect).append("-");
        sb.append(cross).append("-");
        sb.append(crossOverRate).append("-");
        sb.append(mutationAddRate).append("-");
        sb.append(mutationSubRate).append("-");
        sb.append(mutationFlipRate).append("-");
        sb.append(percentageParentsToKeep).append("-");
        sb.append(".csv");
        return sb.toString();
    }

    private static void getAllPosibilities(int maxNbTick, int nbThread,int[] nbCreature, int[] tournamentSize,
                                           int[] tournamentSize2, int nbEnvironment, List<Environment> environments,
                                           String[] parentSelection, String[] evolveSelection, String[] crossOver,
                                           double[] crossOverRate, double[] mutationAddRate, double[] mutationSubRate,
                                           double[] mutationFlipRate, double[] percentageParentToKeep, String pathResult){

        GeneticAlgorithm ga = new GeneticAlgorithmMultiThread(maxNbTick, nbThread);
        SelectionConfig parentConfig = null;
        SelectionConfig evolveConfig = null;
        CrossOverConfig crossOverConfig = null;
        Map<String,String> tournamentParent = new HashMap<>();
        Map<String,String> tournamentEvolve = new HashMap<>();
        for(int nbCrea : nbCreature) {
            ga.setNbCreature(nbCrea);
            for (int tournaSizeParent : tournamentSize) {
                tournamentParent.clear();
                tournamentParent.put("tournament", String.valueOf(tournaSizeParent));
                for (int tournaSizeEvolve : tournamentSize2) {
                    tournamentEvolve.clear();
                    tournamentEvolve.put("tournament", String.valueOf(tournaSizeEvolve));
                    for (int i =0; i<nbEnvironment;i++) {
                        ga.setEnvironment(environments.get(i));
                        for (String parSelection : parentSelection) {
                            parentConfig = new SelectionConfig(parSelection, tournamentParent);
                            ga.setParentSelection(SelectionMapper.selectionFromSelectionConfig(parentConfig));
                            for (String evoSelection : evolveSelection) {
                                evolveConfig = new SelectionConfig(evoSelection, tournamentEvolve);
                                ga.setCrossOverSelection(SelectionMapper.selectionFromSelectionConfig(evolveConfig));
                                for(String cross : crossOver){
                                    crossOverConfig = new CrossOverConfig(cross, 0);
                                    for(double crossRate : crossOverRate) {
                                        crossOverConfig.setRate(crossRate/100.0);
                                        ga.setCrossOver(CrossOverMapper.selectionFromCrossOverConfig(crossOverConfig));
                                        for (double mutationAddR : mutationAddRate) {
                                            ga.setMutationAddRate(mutationAddR / 100.0);
                                            for (double mutationSubR : mutationSubRate) {
                                                ga.setMutationSubRate(mutationSubR / 100.0);
                                                for (double mutationFlipR : mutationFlipRate) {
                                                    ga.setMutationFlipRate(mutationFlipR / 100.0);
                                                    for (double percentParentKeep : percentageParentToKeep) {
                                                        ga.setPercentageParentsToKeep(percentParentKeep / 100.0);
                                                        try {
                                                            String log = ga.runAlgorithmHistory(500);
                                                            writeHistoryToFile(buildFullPath(pathResult, nbCrea, tournaSizeParent, tournaSizeEvolve,
                                                                    i, parSelection,
                                                                    evoSelection, cross, crossRate, mutationAddR, mutationSubR, mutationFlipR, percentParentKeep), log);
                                                        } catch (InterruptedException | CloneNotSupportedException | IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public static List<Integer> whichScoresAreBetter(List<Double> newScore, List<Double> bestScore)
    {
        List<Integer> returnedList = new ArrayList<>();
        for(int i = 0;i<newScore.size();i++)
        {
            if(newScore.get(i)<bestScore.get(i))
                returnedList.add(i);
        }
        return returnedList;
    }

    public static GeneticAlgorithmMultiThread findBestSolutions(int maxNbTick,int maxNbIter, int nbThread,int[] nbCreature, int[] tournamentSize,
                                        int[] tournamentSize2, int nbEnvironment, List<Environment> environments,
                                        String[] parentSelection, String[] evolveSelection, String[] crossOver,
                                        double[] crossOverRate, double[] mutationAddRate, double[] mutationSubRate,
                                        double[] mutationFlipRate, double[] percentageParentToKeep, List<Double> solutions) throws CloneNotSupportedException {
        GeneticAlgorithmMultiThread ga = new GeneticAlgorithmMultiThread(maxNbTick, nbThread);
        SelectionConfig parentConfig = null;
        SelectionConfig evolveConfig = null;
        CrossOverConfig crossOverConfig = null;
        Map<String,String> tournamentParent = new HashMap<>();
        Map<String,String> tournamentEvolve = new HashMap<>();
        GeneticAlgorithmMultiThread bestGA = null;
        double bestScore = solutions.get(0);
        for(int nbCrea : nbCreature) {
            ga.setNbCreature(nbCrea);

            for (int tournaSizeParent : tournamentSize) {
                tournamentParent.clear();
                tournamentParent.put("tournament", String.valueOf(tournaSizeParent));
                for (int tournaSizeEvolve : tournamentSize2) {
                    tournamentEvolve.clear();
                    tournamentEvolve.put("tournament", String.valueOf(tournaSizeEvolve));
                    for (String parSelection : parentSelection) {
                        parentConfig = new SelectionConfig(parSelection, tournamentParent);
                        ga.setParentSelection(SelectionMapper.selectionFromSelectionConfig(parentConfig));
                        for (String evoSelection : evolveSelection) {
                            evolveConfig = new SelectionConfig(evoSelection, tournamentEvolve);
                            ga.setCrossOverSelection(SelectionMapper.selectionFromSelectionConfig(evolveConfig));
                            for(String cross : crossOver){
                                crossOverConfig = new CrossOverConfig(cross, 0);
                                for(double crossRate : crossOverRate) {
                                    crossOverConfig.setRate(crossRate/100.0);
                                    ga.setCrossOver(CrossOverMapper.selectionFromCrossOverConfig(crossOverConfig));
                                    for (double mutationAddR : mutationAddRate) {
                                        ga.setMutationAddRate(mutationAddR / 100.0);
                                        for (double mutationSubR : mutationSubRate) {
                                            ga.setMutationSubRate(mutationSubR / 100.0);
                                            for (double mutationFlipR : mutationFlipRate) {
                                                ga.setMutationFlipRate(mutationFlipR / 100.0);
                                                for (double percentParentKeep : percentageParentToKeep) {
                                                    ga.setPercentageParentsToKeep(percentParentKeep / 100.0);
                                                    List<Double> newScore = new ArrayList<>();
                                                    for (int i =0; i<nbEnvironment;i++) {
                                                        ga.setEnvironment(environments.get(i));
                                                        //ga.setSolution(solutions.get(i));
                                                        try {
                                                            Game g = ga.runAlgorithm(maxNbIter);
                                                            newScore.add(g.getScore());
                                                        } catch (InterruptedException | CloneNotSupportedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    List<Integer> indexBest = whichScoresAreBetter(newScore, solutions);
                                                    if(indexBest.size() > solutions.size()*0.66)
                                                    {
                                                        for(int index : indexBest)
                                                            solutions.set(index, newScore.get(index));
                                                        bestGA = (GeneticAlgorithmMultiThread)ga.clone();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestGA;
    }
    public static List<Double> getBestSolutionForEnvironments(List<Environment> environments, int maxIter, int iterPerEnvironments) throws IOException {
        List<Double> returnedList = new ArrayList<>();
        GeneticAlgorithm ga = GAYamlMapper.getGeneticAlgorithmFromPath("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\GAConfigs\\1.yaml");
        double bestScore = 0;
        ga.setSolution(bestScore); /// Permet de m'assurer de ne jamais m'arrêter dans un minimum local étant donné que cette solution ne sera jamais atteinte

        for(Environment env : environments)
        {
            bestScore = 0;
            ga.setEnvironment(env);
            for(int i = 0;i<iterPerEnvironments;i++)
            {
                Game g = null;
                try {
                    g = ga.runAlgorithm(maxIter);
                } catch (InterruptedException | CloneNotSupportedException e) {
                    bestScore = Double.MAX_VALUE;
                }
                if(g!=null && g.getScore() < bestScore)
                    bestScore = g.getScore();

            }
            returnedList.add(bestScore);
        }
        return returnedList;
    }
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        int nbEnvironment = 3;
        List<Environment> environments = getTestEnvironment("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\TunningParameter\\Environments",
                nbEnvironment);
        List<Double> solutions = getBestSolutionForEnvironments(environments, 1000, 3);
        int[] nbCreature = new int[]{100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 100};
        String[] parentSelection = new String[]{"BESTFIT", "TOURNAMENT", "WHEEL"};
        String pathResult = "D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\TunningParameter\\Results";
        String[] crossOver = new String[]{"CROSSOVER", "CASEBYCASE", "KEEPFROMBEST"};
        double[] crossOverRate = new double[]{
                10, 12, 14, 16, 18,
                20, 22, 24, 26, 28,
                30, 32, 34, 36, 38,
                40, 42, 44, 46, 48,
                50, 52, 54, 56, 58,
                60, 62, 64, 66, 68,
                70, 72, 74, 76, 78,
                80
        };
        String[] evolveSelection = new String[]{"BESTFIT", "TOURNAMENT", "WHEEL"};
        double[] mutationRate = new double[]{
                0.25, 0.5, 0.75, 1,
                1.25, 1.5, 1.75, 2,
                2.25, 2.5, 2.75, 3,
                3.25, 3.5, 3.75, 4,
                4.25, 4.5, 4.75, 5,
                5.25, 5.5, 5.75, 6,
                6.25, 6.5, 6.75, 7,
                7.25, 7.5, 7.75, 8,
                8.25, 8.5, 8.75, 9,
                9.25, 9.5, 9.75, 10,
        };

        double[] percentageParentToKeep = new double[]{
                0,1,2,3,4,5,6,7,8,9,
                10,11,12,13,14,15,16,17,18,19,
                20,21,22,23,24,25,26,27,28,29,
                30,31,32,33,34,35,36,37,38,39,
                40
        };
        int[] tournamentSize = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50};
        int nbThread = 4;
        int maxNbTick = 1000;
        int maxNbIter = 1000;
        GeneticAlgorithmMultiThread gamt = findBestSolutions(maxNbTick, maxNbIter, nbThread,nbCreature, tournamentSize,
        tournamentSize, nbEnvironment, environments,
                parentSelection, evolveSelection, crossOver,
        crossOverRate, mutationRate, mutationRate,
                mutationRate,percentageParentToKeep, solutions);
        System.out.println("Résultat : ");
        System.out.println(gamt);
    }
}
