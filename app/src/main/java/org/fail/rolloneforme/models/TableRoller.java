package org.fail.rolloneforme.models;

import org.fail.rolloneforme.RollRandomizer;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class TableRoller {
    private RollRandomizer randomizer;

    public TableRoller(RollRandomizer randomizer) {
        this.randomizer = randomizer;
    }

    public RollResult rollTable(Table table) {
        RollResult result = new RollResult(table.getHeader());

        int dieRoll = randomizer.roll(table.getDie());
        result.setRolledNumber(dieRoll);
        TableItem chosenOutcome = table.getOutcomes().get(dieRoll - 1);

        if (chosenOutcome.weight() != dieRoll) {
            throw new RuntimeException("WTF BBQ");
        }

        result.setOutcome(chosenOutcome.outcome());

        return result;
    }
}
