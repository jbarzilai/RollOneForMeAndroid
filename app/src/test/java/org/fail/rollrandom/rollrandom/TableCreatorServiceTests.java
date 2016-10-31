package org.fail.rollrandom.rollrandom;

import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TableCreatorServiceTests extends TestBase {

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
        assertThat(result.getDie(), is(4));
    }

    @Test
    public void parseTable_ParsesHeader() throws Exception {
        String tableTestString = "D4 Fashions";

        Table result = testObject.parseTable(tableTestString);

        assertNotNull(result);
        assertThat(result.getHeader(), is("Fashions"));
    }

    @Test
    public void parseTable_ParseItems() throws Exception {
        String tableTestString = "D12 Fashions\n"+
                "1. Twenty-four snakeskins.\n" +
                "2. Woolen traveling cape.";

        Table result = testObject.parseTable(tableTestString);

        List<TableItem> resultOutcomes = result.getOutcomes();
        assertThat(resultOutcomes.size(), equalTo(2));
        TableItem outcomeOne = resultOutcomes.get(0);
        assertThat(outcomeOne.weight(), equalTo(1));
        assertThat(outcomeOne.outcome(), equalTo("Twenty-four snakeskins."));
        TableItem outcomeTwo = resultOutcomes.get(1);
        assertThat(outcomeTwo.weight(), equalTo(2));
        assertThat(outcomeTwo.outcome(), equalTo("Woolen traveling cape."));
    }

    @Test
    public void parseSourceFromText_ParsesRawLines() throws Exception {
        String text = loadTestFile("fashion_post_small.txt");

        log(text);

        TableSource tableSource = testObject.parseTableSourceFromText(text);

        assertNotNull(tableSource);
        assertThat(tableSource.getLines().size(), equalTo(15));
    }

    @Test
    public void parseSourceFromText_FindsTableRanges() throws Exception {
        String text = loadTestFile("fashion_post_small.txt");

        log(text);

        TableSource tableGroup = testObject.parseTableSourceFromText(text);

        List<TableRange> tableRanges = tableGroup.getTableRanges();
        assertThat(tableRanges.size(), equalTo(1));
        TableRange rangePair = tableRanges.get(0);
        assertThat(rangePair.start, equalTo(2));
        assertThat(rangePair.stop, equalTo(15));
    }

    @Test
    public void parseSourceFromText_FindsTableRanges_MultipleTables() throws Exception {
        String text = loadTestFile("fashion_post.txt");

        log(text);

        TableSource tableGroup = testObject.parseTableSourceFromText(text);

        List<TableRange> tableRanges = tableGroup.getTableRanges();
            assertThat(tableRanges.size(), equalTo(2));

        TableRange rangePair = tableRanges.get(0);
        assertThat(rangePair.start, equalTo(2));
        assertThat(rangePair.stop, equalTo(15));

        TableRange rangePairTwo = tableRanges.get(1);
        assertThat(rangePairTwo.start, equalTo(17));
        assertThat(rangePairTwo.stop, equalTo(38));
    }

    @Test
    public void parseSourceFromText_ParsesTables() throws Exception {
        String text = loadTestFile("fashion_post.txt");

        log(text);

        TableSource tableGroup = testObject.parseTableSourceFromText(text);

        List<Table> tables = tableGroup.getTables();
        assertThat(tables.size(), equalTo(2));
        assertThat(tables, IsCollectionContaining.hasItem(HasPropertyWithValue.<Table>hasProperty("header", equalTo("Fashions"))));
        assertThat(tables, IsCollectionContaining.hasItem(HasPropertyWithValue.<Table>hasProperty("header", equalTo("Identifiers"))));
    }

}