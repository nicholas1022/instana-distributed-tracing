package instana.service.inputData;

import java.util.Scanner;

public abstract class CommandLineInput implements InputDataInterface {

    @Override
    public String inputData() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
