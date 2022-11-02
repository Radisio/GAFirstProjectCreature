import Game.Environment.Environment;
import Game.Environment.EnvironmentBuilder;
import Game.Game;
import GeneticAlgorithm.Configuration.GAConfig;
import GeneticAlgorithm.Configuration.GAYamlMapper;
import GeneticAlgorithm.MonoThread.GeneticAlgorithmMonoThread;
import jdk.jshell.spi.ExecutionControl;

import java.io.*;

public class Program {
    public static void main(String[] args) throws IOException, InterruptedException, CloneNotSupportedException, ExecutionControl.NotImplementedException {
        /*
        Game.Environment b = null;

            b = new EnvironmentBuilder().build();
            FileOutputStream fos = new FileOutputStream("test.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(b);
            oos.flush();
            oos.close();
            System.out.println("First display");
            b.displayOnce();

        try {
            FileInputStream fis = new FileInputStream("test.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Game.Environment b2 = (Game.Environment) ois.readObject();
            System.out.println("Second display");
            b2.displayOnce();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<MovementConst> movements = new ArrayList<>(){
            {
                add(MovementConst.RIGHT);
                add(MovementConst.LEFT);
                add(MovementConst.UP);
                add(MovementConst.UP_RIGHT);
            }
        };
        Game game = new Game(new EnvironmentBuilder().build(), new Creature(movements));
       // game.start();

        Integer test = 1;
        Byte b = test.byteValue();
        byte[] bytes = new byte[]{0,0,1};
        System.out.println(MovementConst.fromByteArray(bytes));*/
        /*GeneticAlgorithmMonoThread ga = new GeneticAlgorithmMonoThread();
        Game bestG = ga.runAlgorithm(new EnvironmentBuilder().build(), 10000, 0, 5000, 20.0/100.0);
        bestG.getEnvironment().displayOnce();
        bestG.start();*/
        //System.out.println("TEST");
        //System.out.println(GAYamlMapper.getGAConfigFromYAML(Program.class.getClassLoader().getResource("GAConfigs/1.yaml").getPath()));
        //System.out.println(GAYamlMapper.getGAConfigFromYAML("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\GAConfigs\\1.yaml"));
        GeneticAlgorithmMonoThread ga = GAYamlMapper.getGeneticAlgorithmFromPath("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\GAConfigs\\1.yaml");
        Game bestG = ga.runAlgorithm(new EnvironmentBuilder().build(), 1000);
        bestG.start();
    }
}
