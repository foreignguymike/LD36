package com.distraction.ld36;

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

    public static final int RUSH = 10;
    public static final int BULLET = 10;
    public static final float[] CALL_TIMES =
            {
                    // start
                    2.5f, 0.3f, 8, 7, 7, 0.2f, 7, 7, 7,

                    // rush

                    14,
                    14,
                    14,
                    13,
                    13,
                    13,

                    // bullet

                    9,
                    8,
                    8,
                    7
            };

}
