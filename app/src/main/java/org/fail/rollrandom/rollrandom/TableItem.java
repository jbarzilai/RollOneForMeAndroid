package org.fail.rollrandom.rollrandom;

/**
 * Created by barzilaj on 10/28/2016.
 */
public class TableItem {
    private final int _weight;
    private final String _outcome;

    public TableItem(int weight, String outcome) {
        _weight = weight;
        _outcome = outcome;
    }

    public int weight() {
        return _weight;
    }

    public String outcome() {
        return _outcome;
    }
}
