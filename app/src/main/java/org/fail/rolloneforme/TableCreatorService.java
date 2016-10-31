package org.fail.rolloneforme;

import org.fail.rolloneforme.models.Table;
import org.fail.rolloneforme.models.TableItem;
import org.fail.rolloneforme.models.TableRange;
import org.fail.rolloneforme.models.TableSource;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        return parseTable(lines);
    }

    private Table parseTable(List<String> lines) {
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

    public TableSource parseTableSourceFromText(String rawText) {
        TableSource tableSource = new TableSource();

        List<String> lines = Arrays.asList(rawText.split("\n"));
        tableSource.setLines(lines);

        identityTableRanges(tableSource);

        for (TableRange range : tableSource.getTableRanges()) {
            Table t = parseTable(lines.subList(range.start, range.stop));
            tableSource.addTable(t);
        }

        return tableSource;
    }

    private void identityTableRanges(TableSource tableSource) {
        List<String> lines = tableSource.getLines();

        int i = 0;
        while (i < lines.size()) {
            String currentLine = lines.get(i).trim();
            log(currentLine);

            Matcher m = headerPattern.matcher(currentLine);

            if (m.matches()) {
                log("****** Possible getDie line at " + i);

                int start = i;
                int stop = i + 1;

                while (stop < lines.size() && Pattern.matches("^[0-9].*", lines.get(stop))) {
                    log(lines.get(stop));
                    stop++;
                }
                log(String.format("****** New Range from %s to %s", start, stop));
                log("Print first and last of range for testing");
                log(lines.get(start));
                log(lines.get(stop - 1));
                tableSource.addTableRange(new TableRange(start, stop));
                i = stop - 1;
            }
            i++;
        }
    }

    private void log(String x) {
        System.out.println(x);
    }
}
