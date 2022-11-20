package YAMLMapperTest;

import static org.junit.Assert.*;

import GeneticAlgorithm.Configuration.GAConfig;
import GeneticAlgorithm.Configuration.GAYamlMapper;
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Selection.TournamenSelection;
import org.junit.Test;

import java.io.IOException;

public class GAMapperTest {
    @Test
    public final void testMapperGAConfig()
    {
        try {
            GAConfig gaConfig = GAYamlMapper.getGAConfigFromYAML("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\test\\resources\\1.yaml");
            assert gaConfig.getPercentageParentsToKeep()*100==20.0;
            assert gaConfig.getNbCreature()==500;
            assert gaConfig.getSolution() == 0;
            assert gaConfig.getNbThread()==4;
            assert gaConfig.getParentSelection().getMethod().equals("tournament");
            assert gaConfig.getParentSelection().getMap().containsKey("tournamentSize");
            assert gaConfig.getParentSelection().getMap().get("tournamentSize").equals("10");

            assert gaConfig.getEvolveSelection().getMethod().equals("tournament");
            assert gaConfig.getEvolveSelection().getMap().containsKey("tournamentSize");
            assert gaConfig.getEvolveSelection().getMap().get("tournamentSize").equals("10");

            assert gaConfig.getMutationFlipRate()==0.025;
            assert gaConfig.getMutationAddRate()==0.025;
            assert gaConfig.getMutationSubRate()==0.025;
            assert gaConfig.getMaxNbTick()==1000;

            assert gaConfig.getCrossOver().getMethod().equals("keepfrombest");
            assert gaConfig.getCrossOver().getRate()==0.5;
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception occur");
        }

    }

    @Test
    public final void testMapperGA()
    {
        try {
            GeneticAlgorithm ga = GAYamlMapper.getGeneticAlgorithmFromPath("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\test\\resources\\1.yaml");
            assert ga.getPercentageParentsToKeep()*100 == 20.0;
            assert ga.getNbCreature()==500;
            assert ga.getSolution() == 0;
            assert ga.getMutationFlipRate()==0.025;
            assert ga.getMutationAddRate()==0.025;
            assert ga.getMutationSubRate()==0.025;
            assert ga.getMaxNbTick()==1000;
            assert ga.getParentSelection() instanceof TournamenSelection;
            assert ga.getCrossOverSelection() instanceof TournamenSelection;

        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception occur");
        }
    }
}
