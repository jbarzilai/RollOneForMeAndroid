package org.fail.rollrandom.rollrandom;

import java.util.Random;

/**
 * Created by barzilaj on 10/29/2016.
 */
public class RollRandomizer {

    private final Random random = new Random();

    public int roll(int max) {
        return random.nextInt(max) + 1;
    }
}
