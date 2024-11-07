package instana.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CommandFileInput {

    Scanner sc;

    public CommandFileInput() {
        this.sc = new Scanner(System.in);
    }

    public String inputCommand(String file) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return line;
    }
}
