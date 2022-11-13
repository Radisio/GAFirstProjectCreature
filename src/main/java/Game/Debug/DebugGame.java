package Game.Debug;

import Game.Game;
import GeneticAlgorithm.Population;

import java.text.DecimalFormat;

public class DebugGame {

    public static void showProgressBar(int max, int current)
    {
        ///Résultat compris entre 0 et 1
        double currentProgression = (double)current/(double)max;
        int currentPercentage = (int)(currentProgression * 100);
        StringBuilder displayedProgressBar = new StringBuilder();
        int i =0;
        for(;i<currentPercentage;i++)
        {
            displayedProgressBar.append("/");
        }
        for(;i<max;i++)
        {
            displayedProgressBar.append("-");
        }
        System.out.print("\r" + currentPercentage + "% ->"+ displayedProgressBar + " : ");
    }


}
