package GeneticAlgorithm.CrossOver;

import GeneticAlgorithm.Configuration.CrossOverConfig;
import GeneticAlgorithm.Configuration.SelectionConfig;
import GeneticAlgorithm.Selection.BestFitSelection;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Selection.TournamenSelection;
import GeneticAlgorithm.Selection.WheelSelection;

public class CrossOverMapper {
    public static CrossOver selectionFromCrossOverConfig(CrossOverConfig crossOverConfig)
    {
        switch(crossOverConfig.getMethod().toUpperCase())
        {
            case "CROSSOVER":
            {
                return new CrossOver(crossOverConfig.getRate());
            }
            case "CASEBYCASE":
            {
                return new CaseByCaseCrossOver(crossOverConfig.getRate());
            }
            case "KEEPFROMBEST":
            {
                return new KeepFromBestCrossOver(crossOverConfig.getRate());
            }
        }
        return null;
    }
}
