package org.fail.rolloneforme.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barzilaj on 10/30/2016.
 */
public class TableSource {
    private List<Table> tables = new ArrayList<>();
    private List<String> lines = new ArrayList<>();
    private List<TableRange> tableRanges = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public List<TableRange> getTableRanges() {
        return tableRanges;
    }

    public void addTableRange(TableRange range) {
        tableRanges.add(range);
    }
}
