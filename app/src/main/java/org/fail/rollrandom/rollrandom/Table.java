package org.fail.rollrandom.rollrandom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barzilaj on 10/28/2016.
 */
public class Table {

    private final int die;
    private String header;
    private List<TableItem> outcomes;

    public Table(int die, String header) {
        this.die = die;
        this.header = header;
        this.outcomes = new ArrayList<>();
    }

    public int getDie() {
        return die;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<TableItem> getOutcomes() {
        return outcomes;
    }

    public void addOutcome(TableItem item) {
        outcomes.add(item);
    }
}
