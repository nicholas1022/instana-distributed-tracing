package instana.service.inputData.graphInput;

import instana.service.inputData.CLIInput;

public class GraphFileCLIInputService extends CLIInput {

    @Override
    public String inputData() {
        System.out.println("Enter graph file");
        return super.inputData();
    }
}
