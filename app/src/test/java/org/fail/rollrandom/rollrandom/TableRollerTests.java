package org.fail.rollrandom.rollrandom;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by barzilaj on 10/29/2016.
 */

public class TableRollerTests extends TestBase {

    @Mock
    private RollRandomizer randomizer;
    private TableRoller testObject;

    @Before
    public void setup() {
        testObject = new TableRoller(randomizer);
    }

    @Test
    public void RollTable() throws Exception {
        Table t = new Table(2, "Test Table");
        t.addOutcome(new TableItem(1, "One"));
        t.addOutcome(new TableItem(2, "Two"));

        when(randomizer.roll(2)).thenReturn(2);

        RollResult result = testObject.rollTable(t);

        assertThat(result.getHead(), equalTo("Test Table"));
        assertThat(result.getRolledNumber(), equalTo(2));
        assertThat(result.getOutcome(), equalTo("Two"));
    }
}
