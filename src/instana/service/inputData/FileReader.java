package instana.service.inputData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class FileReader {

    Scanner sc;

    public FileReader(Scanner sc) {
        this.sc = sc;
    }

    public String readData(String file) {
        String line;
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return line;
    }
}
