import Game.Environment.Environment;
import Game.Environment.EnvironmentUtil;

public class Test {

    public static void main(String[] args) {
        Environment environment = EnvironmentUtil.newEnvironmentFromFilePos("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\EnvironmentConfigs\\test.txt");
        environment.displayOnce();
        Environment environment2 = EnvironmentUtil.newEnvironmentFromFilePosOtherView("D:\\Master2\\Système distribué\\PremierProjetCreature\\src\\main\\resources\\EnvironmentConfigs\\testOtherView.txt");
        environment2.displayOnce();
    }
}
