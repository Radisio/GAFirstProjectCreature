package Game.Environment.Configuration;

import Game.Environment.EnvironmentBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.JacksonYAMLParseException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class EnvironmentYamlMapper {

    public static EnvironmentConfig getEnvironmentConfigFromYAML(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValue(new File(path), EnvironmentConfig.class);
    }

    public static EnvironmentBuilder getEnvironmentBuilderFromYAML(String path) throws IOException, ClassNotFoundException {
        EnvironmentConfig ec = getEnvironmentConfigFromYAML(path);
        EnvironmentBuilder eb = new EnvironmentBuilder();
        if(ec.getNbCol()!=-1)
        {
            eb.nbCol(ec.getNbCol());
            if(ec.getNbLine()!=-1)
            {
                eb.nbLine(ec.getNbLine());
            }
            else{
                if(ec.getMaxNbLine()!=-1 && ec.getMinNbLine()!=-1)
                {
                    eb.setMaxNbLine(ec.getMaxNbLine());
                    eb.setMinNbLine(ec.getMinNbLine());
                }
                else{
                    throw new ClassNotFoundException("Define number of line and column !");
                }
            }
        }
        else{
            if(ec.getMaxNbCol()!=-1 && ec.getMinNbCol()!=-1)
            {
                eb.setMaxNbCol(ec.getMaxNbCol());
                eb.setMinNbCol(ec.getMinNbCol());
                if(ec.getNbLine()!=-1)
                {
                    eb.nbLine(ec.getNbLine());
                }
                else{
                    if(ec.getMaxNbLine()!=-1 && ec.getMinNbLine()!=-1)
                    {
                        eb.setMaxNbLine(ec.getMaxNbLine());
                        eb.setMinNbLine(ec.getMinNbLine());
                    }
                    else{
                        throw new ClassNotFoundException("Define number of line and column !");
                    }
                }
            }
            else{
                throw new ClassNotFoundException("Define number of line and column !");
            }
        }
        if(ec.getStart().x!=-1 && ec.getStart().y!=-1)
            eb.setStartingPos(ec.getStart());
        if(ec.getEnd().x!=-1 && ec.getEnd().y!=-1)
            eb.setEndingPos(ec.getEnd());
        return eb;
    }
}
