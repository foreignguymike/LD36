package com.distraction.ld36;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vars {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final int SCALE = 2;

    public static final int NUM_JACK_ROWS = 3;
    public static final int NUM_JACK_COLS = 6;

    public static final int PANEL_WIDTH = WIDTH - 100;

    public static final float CALL_MAX_TIME = 5;

    public static final float PATIENCE_MIN_TIME = 18;
    public static final float PATIENCE_RAND = 10;
    public static final float PATIENCE_MAX_TIME = PATIENCE_MIN_TIME + PATIENCE_RAND;

    public static float getNextTime(int index) {
        int count = 0;
        for (int i = 0; i < CALL_TIMES.size(); i++) {
            for (int j = 0; j < CALL_TIMES.get(i).size(); j++) {
                if (count == index) {
                    return CALL_TIMES.get(i).get(j);
                }
                count++;
            }
        }
        return -1;
    }

    public static final Float[] CALL_TIMES1 =
            {2.5f, 0.3f, 8f, 7f, 7f, 0.2f, 7f, 0.2f, 7f};
    public static final Float[] CALL_TIMES2 =
            {5f, 5f, 0.3f, 4.5f, 4.5f, 0.2f, 4.5f, 4.5f, 0.2f, 0.2f, 4.5f, 4.5f, 0.2f, 4.5f};
    public static final Float[] CALL_TIMES3 =
            {2.5f, 2.5f, 2.4f, 2.4f, 2.5f, 2.4f, 2.5f, 2.4f, 2.5f, 2.2f, 2f, 2f, 2f, 2f};
    public static final List<List<Float>> CALL_TIMES;
    public static final int NUM_CALLS;

    static {
        CALL_TIMES = new ArrayList<List<Float>>();
        CALL_TIMES.add(Arrays.asList(CALL_TIMES1));
        CALL_TIMES.add(Arrays.asList(CALL_TIMES2));
        CALL_TIMES.add(Arrays.asList(CALL_TIMES3));

        int sum = 0;
        for (int i = 0; i < CALL_TIMES.size(); i++) {
            for (int j = 0; j < CALL_TIMES.get(i).size(); j++) {
                sum++;
            }
        }
        NUM_CALLS = sum;
    }

}
