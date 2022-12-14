import Game.Environment.Configuration.EnvironmentYamlMapper;
import Game.Environment.Environment;
import Game.Environment.EnvironmentBuilder;
import Game.Environment.EnvironmentUtil;
import Game.Game;
import GeneticAlgorithm.Configuration.GAYamlMapper;
import GeneticAlgorithm.GeneticAlgorithm;
import Util.CommandLine.CommandLineParserHelper;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.cli.*;
import org.fusesource.jansi.*;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Program {
    static String ESC = "\u001b";
    static String CSI = ESC + '[';
    private static String csinf(int n)
    {
        return CSI + n + "F";
    }
    private static String text(String str, int n)
    {
        return csinf(n) + str;
    }

    public static void main(String[] args) throws IOException, InterruptedException, CloneNotSupportedException, ExecutionControl.NotImplementedException, ClassNotFoundException {

        GeneticAlgorithm ga = null;
        EnvironmentBuilder eb = null;
        Environment env = null;
        int nbIter = 0;
        boolean debug = false;
        CommandLineParserHelper parser = new CommandLineParserHelper("gaEric");
        parser.addOption("nbIter", true, "Nombre d'itérations maximum", true);
        parser.addOption("GAPath", true,"Paramètre de l'algorithme génétique", true);
        parser.addOption("ENVPath", false, "Paramètre de la génération de terrain\n\t.yaml: paramètre de la génération \n\ttxt : terrain déja généré" +
                "\n\tnull, alors un nouveau terrain sera généré", true);
        parser.addOption("ENVPathClass", false, "Fichier contenant les détails de la génération d'un fichier comme demandé en classe", true);
        parser.addOption("ENVPathClassReverse", false, "Fichier contenant les détails de la génération d'un fichier comme demandé en classe " +
                "avec un système d'axe différent", true);
        parser.addOption("solution", false,"Solution attendue :\n\tdouble: la solution" +
                "\n\t-1: Solution temporaire à partir d'un terrain généré" +
                "\n\tnull: Solution se trouvant dans le GAPath", true);
        parser.addOption("debug",false, "Active le mode debug", false);
        try {
            CommandLineParserHelper.status status = parser.parse(args);
            if(status!=CommandLineParserHelper.status.OK)
            {
                if(status==CommandLineParserHelper.status.HELP)
                    parser.help();
                else{
                    System.out.println("Il manque au moins un argument important");
                    parser.help();
                }
            }
            else{
                Map<String, String> arg = parser.getArguments();
                nbIter = Integer.parseInt(arg.get("nbIter"));
                String gaPath = arg.get("GAPath");
                ga = GAYamlMapper.getGeneticAlgorithmFromPath(gaPath);

                System.out.println("Iter : " + nbIter);
                System.out.println("GA PATH : " + gaPath);
                String envPath = arg.getOrDefault("ENVPath",null);
                if(envPath!=null)
                {
                    String end = envPath.substring(envPath.lastIndexOf("."));
                    System.out.println("End = " + end);
                    if(end.equals(".txt")) {
                        System.out.println("Text !");
                        eb = null;
                        env= EnvironmentUtil.readEnvironmentFromFile(envPath);
                    }
                    else if (end.equals(".yaml")) {
                        System.out.println("YAML !");
                        eb=EnvironmentYamlMapper.getEnvironmentBuilderFromYAML("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\EnvironmentConfigs\\test.yaml");
                        env=eb.build();
                    }
                    else
                        System.out.println("Aie");
                }
                else{
                    String envPathClass = arg.getOrDefault("ENVPathClass", null);
                    if(envPathClass!=null)
                    {
                        env = EnvironmentUtil.newEnvironmentFromFilePos(envPathClass);
                    }
                    else {
                        String envPathClassReverse = arg.getOrDefault("ENVPathClassReverse",null);
                        if(envPathClassReverse!=null)
                        {
                            env= EnvironmentUtil.newEnvironmentFromFilePosOtherView(envPathClassReverse);
                        }
                        else {
                            System.out.println("Nouveau terrain sera généré");
                            eb = new EnvironmentBuilder();
                            env = eb.build();
                        }
                    }
                }
                ga.setEnvironment(env);
                String solution = arg.getOrDefault("solution",null);
                if(solution==null)
                {
                    System.out.println("Solution found in GA");
                }
                else{
                    if(solution.equals("-1"))
                    {
                        System.out.println("Solution found in ENV");
                        if(eb!=null)
                            ga.setSolution(eb.getNbMouvement()*0.33);
                    }
                    else
                    {
                        double solutionDouble = Double.parseDouble(solution);
                        System.out.println("Solution double : " + solutionDouble);
                        ga.setSolution(solutionDouble);
                    }
                }
                debug = arg.containsKey("debug");
            }
            Game bestG = null;
            if(debug)
                bestG = ga.runAlgorithmDebug(1, TimeUnit.SECONDS);
            else {
                assert ga != null;
                bestG = ga.runAlgorithm(nbIter);
            }
            bestG.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
