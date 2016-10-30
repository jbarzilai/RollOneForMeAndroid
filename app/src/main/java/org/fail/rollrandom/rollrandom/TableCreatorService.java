package org.fail.rollrandom.rollrandom;

import android.util.IntProperty;
import android.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fail.rollrandom.rollrandom.TableSource.*;

/**
 * Created by barzilaj on 10/28/2016.
 */
public class TableCreatorService {
    private static final String LINE_REGEX = "^(\\d+)\\.(\\s*-+\\s*\\d+)?(.*)";
    private static final String HEADER_REGEX = "^(\\d+)?[dD](\\d+)(.*)";

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

    public TableSource parseTableSourceFromText(String rawText) {
        TableSource tableSource = new TableSource();

        List<String> lines = Arrays.asList(rawText.split("\n"));
        tableSource.setLines(lines);

        identityTableRanges(tableSource);

        return tableSource;
    }

    private void identityTableRanges(TableSource tableSource) {
        List<String> lines = tableSource.getLines();
        Integer start = null;

        for(int i = 0; i < lines.size(); i++) {
            log(String.format("Evaluating line %s of %s", i, lines.size()));

            String currentLine = lines.get(i);
            log(currentLine);

            Matcher m = headerPattern.matcher(currentLine);

            if (m.matches()) {
                log("****** Match on line " + i);

                if (start == null) {
                    start = i;
                }
                else {
                    log(String.format("****** New Range from %s to %s", start, i - 1));
                    tableSource.addTableRange(new TableRange(start, i - 1));
                    start = i;
                }
            }
        }

        if (start != null) {
            tableSource.addTableRange(new TableRange(start, lines.size() - 1));
        }
    }
}
