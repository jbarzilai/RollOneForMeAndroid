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

    public int die() {
        return die;
    }

    public String header() {
        return header;
    }

    public List<TableItem> outcomes() {
        return outcomes;
    }

    public void addOutcome(TableItem item) {
        outcomes.add(item);
    }
}
