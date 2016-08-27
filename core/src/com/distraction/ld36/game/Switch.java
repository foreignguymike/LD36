package com.distraction.ld36.game;

public class Switch {

    private enum Position {
        FRONT,
        MIDDLE,
        BACK
    }

    private Position position;

    public Switch() {
        position = Position.MIDDLE;
    }

}
