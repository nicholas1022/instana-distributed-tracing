package instana.service.inputData.graphInput;

import instana.service.inputData.CommandLineInput;

public class GraphFileCommandLineInput extends CommandLineInput {

    @Override
    public String inputData() {
        System.out.println("Enter graph file");
        return super.inputData();
    }
}
