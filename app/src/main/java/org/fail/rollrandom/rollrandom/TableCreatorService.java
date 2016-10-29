package org.fail.rollrandom.rollrandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by barzilaj on 10/28/2016.
 */
public class TableCreatorService {
    public static final String LINE_REGEX = "^(\\d+)\\.(\\s*-+\\s*\\d+)?(.*)";

    private Pattern linePattern;
    private Pattern headerPattern;

    public TableCreatorService() {
        linePattern = Pattern.compile(LINE_REGEX);
        headerPattern = Pattern.compile(HEADER_REGEX);
    }

    public TableItem parseItem(String itemString) {
        log("Original Line: " + itemString);
        Matcher m = linePattern.matcher(itemString);

        if (!m.matches()) {
            throw new RuntimeException("No match");
        }

        String weightStr = m.group(1);
        int weight = Integer.parseInt(weightStr);
        log("\tWeight: " + weight);

        String outcome = m.group(3);

        if (outcome.startsWith(".")) {
            outcome = outcome.substring(1, outcome.length());
        }

        outcome = outcome.trim();
        log(String.format("\tOutcome: \"%s\"", outcome));

        TableItem item = new TableItem(weight, outcome);

        return item;
    }

    private static final String HEADER_REGEX = "^(\\d+)?[dD](\\d+)(.*)";

    public Table parseTable(String tableString) {
        log("Original Table String: " + tableString);


        List<String> lines = Arrays.asList(tableString.split("\n"));
        String headerString = lines.get(0);

        Matcher m = headerPattern.matcher(headerString);

        if (!m.matches()) {
            throw new RuntimeException("No match");
        }

        String dieStr = m.group(2);
        int die = Integer.parseInt(dieStr);
        log("\tDie: " + die);

        String header = m.group(3).trim();
        log(String.format("\tTable Header: \"%s\"", header));

        Table table = new Table(die, header);

        for (String itemStr: lines.subList(1, lines.size())) {
            table.addOutcome(parseItem(itemStr));
        }


        return table;
    }

    private void log(String x) {
        System.out.println(x);
    }
}
