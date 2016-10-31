package org.fail.rolloneforme.models;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class RollResult {

    private int rolledNumber;
    private String head;
    private String outcome;

    public RollResult(String head) {
        this.head = head;
    }

    public int getRolledNumber() {
        return rolledNumber;
    }

    public String getHead() {
        return head;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setRolledNumber(int rolledNumber) {
        this.rolledNumber = rolledNumber;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
