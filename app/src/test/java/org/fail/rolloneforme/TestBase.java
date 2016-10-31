package org.fail.rolloneforme;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class TestBase {
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    protected void log(String str) {
        System.out.println(String.format("%s: %s", getClass().getSimpleName(), str));
    }

    protected String loadTestFile(String filePath) throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(filePath);
        File testFile = new File(resource.getPath());

        assertNotNull(testFile);

        //Read text from file
        StringBuilder text = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(testFile));

        try {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    text.append(line);
                    text.append('\n');
                }
            }
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        finally {
            br.close();
        }
        return text.toString();
    }
}
