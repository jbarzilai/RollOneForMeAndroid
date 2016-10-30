package org.fail.rollrandom.rollrandom;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barzilaj on 10/30/2016.
 */
public class TableSource {
    private List<Table> tables;
    private List<String> lines = new ArrayList<>();
    private List<TableRange> tableRanges = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
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

    public void setTableRanges(List<TableRange> tableRanges) {
        this.tableRanges = tableRanges;
    }

    public void addTableRange(TableRange range) {
        tableRanges.add(range);
    }

}
