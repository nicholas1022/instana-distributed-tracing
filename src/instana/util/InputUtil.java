package instana.util;

import instana.model.AbstractState;
import instana.model.TraceHopState;
import instana.model.TraceLatencyState;

import java.util.Arrays;
import java.util.List;

public class InputUtil {

    public boolean isValidNodeInput(String node) {
        if (node == null || node.length() > 1 || !isAlpha(node)) return false;

        return true;
    }

    public boolean isAlpha(String s) {
        if (s == null || s.isEmpty()) return false;
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }


    public TraceLatencyState parseLatency(String s) {
        List<String> parseList = traceStateParseHelper(s);
        if (parseList == null) return null;

        return new TraceLatencyState(parseList.get(0), parseList.get(1), Integer.parseInt(parseList.get(2)));

    }

    public TraceHopState parseHop(String s) {
        List<String> parseList = traceStateParseHelper(s);
        if (parseList == null) return null;

        return new TraceHopState(parseList.get(0), parseList.get(1), Integer.parseInt(parseList.get(2)));
    }

    private List<String> traceStateParseHelper(String s) {
        if (s == null) return null;
        int i;
        for (i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                break;
            }
        }
        String hop = s.substring(i + 1);
        if (isAlpha(hop) || hop.isEmpty()) return null;

        String[] services = s.substring(0, i+1).split("-");
        if (services.length < 2) {
            return null;
        }
        return Arrays.asList(services[0], services[1], hop);
    }
}
