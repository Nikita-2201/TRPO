import GUI.GUI;
import Testing.Testing;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InstantiationException {
        Testing test = new Testing();

        test.testIntegerType();
        test.testFractionType();
        GUI gui = new GUI();
        gui.showMenu();
    }
}


