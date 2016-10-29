package org.fail.rollrandom.rollrandom;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TableCreatorServiceTests {

    private String tableTestString = "D12 Fashions\n"+
            "1. Twenty-four snakeskins.\n"+
            "2. Woolen traveling cape.\n"+
            "3. Almost translucent wrap.\n"+
            "4. Close-fit cowl.\n"+
            "5. Heavy, stiff overgown.\n"+
            "6. Pale rose robes.\n"+
            "7. Too-large shirt.\n"+
            "8. Sabatons and greaves.\n"+
            "9. Blatantly armored breastplate.\n"+
            "10. Voluminous shawl.\n"+
            "11. Jacket.\n"+
            "12. Blouse";

    TableCreatorService testObject;

    @Before
    public void setUp() {
        testObject = new TableCreatorService();
    }

    // Handle line weight ranges
    // Strip out "some" punctuation (i.e. handle apostrophes)
    // Identify sub-tables

    @Test
    public void parseItem_Simple() throws Exception {
        String lineString = "1. Twenty-four snakeskins";

        TableItem result = testObject.parseItem(lineString);

        assertNotNull(result);
        assertEquals(result.weight(), 1);
        assertThat(result.outcome(), is("Twenty-four snakeskins"));
    }

    @Test
    public void parseTable_ParsesDie() throws Exception {
        String tableTestString = "D4 Fashions";

        Table result = testObject.parseTable(tableTestString);

        assertNotNull(result);
        assertThat(result.die(), is(4));
    }

    @Test
    public void parseTable_ParsesHeader() throws Exception {
        String tableTestString = "D4 Fashions";

        Table result = testObject.parseTable(tableTestString);

        assertNotNull(result);
        assertThat(result.header(), is("Fashions"));
    }

    @Test
    public void parseTable_ParseItems() throws Exception {
        String tableTestString = "D12 Fashions\n"+
                "1. Twenty-four snakeskins.\n" +
                "2. Woolen traveling cape.";

        Table result = testObject.parseTable(tableTestString);

        List<TableItem> resultOutcomes = result.outcomes();
        assertThat(resultOutcomes.size(), equalTo(2));
        TableItem outcomeOne = resultOutcomes.get(0);
        assertThat(outcomeOne.weight(), equalTo(1));
        assertThat(outcomeOne.outcome(), equalTo("Twenty-four snakeskins."));
        TableItem outcomeTwo = resultOutcomes.get(1);
        assertThat(outcomeTwo.weight(), equalTo(2));
        assertThat(outcomeTwo.outcome(), equalTo("Woolen traveling cape."));
    }
}