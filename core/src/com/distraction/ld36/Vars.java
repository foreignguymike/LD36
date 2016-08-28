package com.distraction.ld36;

public class Vars {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final int SCALE = 2;

    public static final int NUM_JACK_ROWS = 3;
    public static final int NUM_JACK_COLS = 6;

    public static final int PANEL_WIDTH = WIDTH - 100;

    public static final float CALL_MIN_TIME = 15;
    public static final float CALL_RAND = 5;
    public static final float CALL_MAX_TIME = CALL_MIN_TIME + CALL_RAND;

    public static final float PICKUP_MIN_TIME = 3;
    public static final float PICKUP_RAND = 8;
    public static final float PICKUP_MAX_TIME = PICKUP_MIN_TIME + PICKUP_RAND;

    public static final float PATIENCE_MIN_TIME = PICKUP_MAX_TIME + 20;
    public static final float PATIENCE_RAND = 20;
    public static final float PATIENCE_MAX_TIME = PATIENCE_MIN_TIME + PATIENCE_RAND;

    public static final float CALLING_OPERATOR_TIME = 10;

}
