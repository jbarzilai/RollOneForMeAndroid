package org.fail.rollrandom.rollrandom;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class TableRoller {
    private RollRandomizer randomizer;

    public TableRoller(RollRandomizer randomizer) {
        this.randomizer = randomizer;
    }

    public RollResult rollTable(Table table) {
        RollResult result = new RollResult(table.header());

        int dieRoll = randomizer.roll(table.die());
        result.setRolledNumber(dieRoll);
        TableItem chosenOutcome = table.outcomes().get(dieRoll - 1);

        if (chosenOutcome.weight() != dieRoll) {
            throw new RuntimeException("WTF BBQ");
        }

        result.setOutcome(chosenOutcome.outcome());

        return result;
    }
}
