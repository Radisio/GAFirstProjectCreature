package GeneticAlgorithm.Selection;

import GeneticAlgorithm.Configuration.SelectionConfig;

public class SelectionMapper {
    public static Selection selectionFromSelectionConfig(SelectionConfig selection)
    {
        switch(selection.getMethod().toUpperCase())
        {
            case "TOURNAMENT":
            {
                return new TournamenSelection(Integer.parseInt(selection.getMap().get("tournamentSize")));
            }
            case "BESTFIT":
            {
                return new BestFitSelection();
            }
            case "WHEEL":
            case "ROULETTE":
            {
                return new WheelSelection();
            }
        }
        return null;
    }
}
