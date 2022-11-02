package GeneticAlgorithm.Selection;

import GeneticAlgorithm.Configuration.SelectionConfig;

public class SelectionMapper {
    public static Selection selectionFromSelectionConfig(SelectionConfig selection)
    {
        switch(selection.getMethod().toUpperCase())
        {
            case "TOURNAMENT":
            {
                return new TournamenSelection(Integer.valueOf(selection.getMap().get("tournamentSize")), selection.getRate());
            }
            case "BESTFIT":
            {
                return new BestFitSelection(selection.getRate());
            }
        }
        return null;
    }
}
