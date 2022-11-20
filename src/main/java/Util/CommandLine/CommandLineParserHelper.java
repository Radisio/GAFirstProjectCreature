package Util.CommandLine;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineParserHelper {
    public enum status {
        HELP, OK, KO
    }
    private List<CMDOption> options;
    private String name;
    private CommandLine cmd;
    public CommandLineParserHelper(String name,List<CMDOption> options) {
        this.name = name;
        this.options = options;
        this.cmd = null;
    }
    public CommandLineParserHelper(String name)
    {
        this.name = name;
        this.options = new ArrayList<>();
        this.cmd = null;
    }

    private boolean isRequiredParameterPresent()
    {
        for(CMDOption o: options)
        {
            if(o.isRequired())
            {
                if(!cmd.hasOption(o.getName()))
                    return false;
            }
        }
        return true;
    }
    public Map<String, String> getArguments(){
        Map<String,String > returnedMap = new HashMap<>();
        for(CMDOption o: options)
        {
            if(cmd.hasOption(o.getName()))
            {
                if(o.isArgumentRequired())
                    returnedMap.put(o.getName(), cmd.getOptionValue(o.getName()));
                else
                    returnedMap.put(o.getName(), null);
            }

        }
        return returnedMap;
    }

    public status parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options commoncliOptions = new Options();
        for(CMDOption o : options)
            commoncliOptions.addOption(o.getName(), o.isArgumentRequired(), o.getDescription());
        cmd = parser.parse(commoncliOptions, args);
        if(cmd.hasOption("help"))
        {
            return status.HELP;
        }
        else{
            if(!isRequiredParameterPresent())
                return status.KO;
        }
        return status.OK;
    }

    public void addOption(CMDOption cmdOption)
    {
        this.options.add(cmdOption);
    }
    public void addOption(String name, boolean required, String description, boolean argRequired)
    {
        addOption(new CMDOption(name, required, description, argRequired));
    }

    public void help(){
        StringBuilder sb = new StringBuilder();
        sb.append("usage: ").append(name).append("\n");
        for(CMDOption o: options)
            sb.append(o);
        System.out.println(sb);
    }
}
