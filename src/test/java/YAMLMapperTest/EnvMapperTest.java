package YAMLMapperTest;

import Game.Environment.Configuration.EnvironmentConfig;
import Game.Environment.Configuration.EnvironmentYamlMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class EnvMapperTest {

    @Test
    public void testEnvironmentConfig()
    {
        try {
            EnvironmentConfig ec = EnvironmentYamlMapper.getEnvironmentConfigFromYAML("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\test\\resources\\test.yaml");
            assert ec.getMaxNbLine()==50;
            assert ec.getMinNbLine()==20;
            assert ec.getMaxNbCol()==50;
            assert ec.getMinNbCol()==20;
        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception occurs");
        }

    }
}
