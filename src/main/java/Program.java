import Const.DesignConst;
import Environment.EnvironmentBuilder;
import Environment.Environment;
import MathUtil.MathUtil;
import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        Environment b = null;
        try {
            b = new EnvironmentBuilder().build();
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        b.displayOnce();

        System.out.println("Test de la fonction statique : " + MathUtil.getAngleBetweenTwoVectors(new int[]{2, 2}, new int[]{0, 3}));
    }
}
