package org.fail.rollrandom.rollrandom;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class MockingTestBase {
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
