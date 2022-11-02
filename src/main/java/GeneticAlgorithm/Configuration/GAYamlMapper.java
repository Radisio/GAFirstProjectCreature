package GeneticAlgorithm.Configuration;

import GeneticAlgorithm.MonoThread.GeneticAlgorithmMonoThread;
import GeneticAlgorithm.Selection.SelectionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.io.IOException;

public class GAYamlMapper {

    public static GAConfig getGAConfigFromYAML(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(path), GAConfig.class);
    }

    public static GeneticAlgorithmMonoThread getGeneticAlgorithmFromPath(String path) throws IOException, ExecutionControl.NotImplementedException {
        GAConfig config = getGAConfigFromYAML(path);
        if(config.getNbThread()==1)
        {
            ///MonoThread double mutationFlipRate, double mutationAddRate, double mutationSubRate, double percentageParentsToKeep, double solution, Selection parentSelection, Selection crossOverSelection
            return new GeneticAlgorithmMonoThread(config.getNbCreature(),config.getMutationFlipRate(), config.getMutationAddRate(), config.getMutationSubRate(),
                    config.getPercentageParentsToKeep(), config.getSolution(), SelectionMapper.selectionFromSelectionConfig(config.getParentSelection()),
                    SelectionMapper.selectionFromSelectionConfig(config.getEvolveSelection())
                    );
        }
        else
        {
            /// Multithread
            throw new ExecutionControl.NotImplementedException("Not implemented yet");
        }
    }
}
