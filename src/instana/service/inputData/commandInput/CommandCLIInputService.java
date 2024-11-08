package instana.service.inputData.commandInput;

import instana.service.inputData.CLIInput;

public class CommandCLIInputService extends CLIInput {

    @Override
    public String inputData() {
        System.out.println("Enter command (e.g.: trace-latency A-B-C)");
        return super.inputData();
    }
}
