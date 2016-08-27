package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Rectangle;

public class Column {

    private int x;
    private int y;

    private Switch[] switches;
    private Cord[] cords;

    public Column(int x, int y) {
        this.x = x;
        this.y = y;

        switches = new Switch[2];
        for(int i = 0; i < switches.length; i++) {
            switches[i] = new Switch(x - 20, y + 20 - i * 40);
        }

        cords = new Cord[2];
        for(int i = 0; i < cords.length; i++) {
            cords[i] = new Cord(x + 20, y + 20 - i * 40);
        }
    }

    public void touchDown(int x, int y) {
        for(Cord cord : cords) {
            cord.touchDown(x, y);
        }
    }

    public void touchUp(int x, int y) {
        for(Cord cord : cords) {
            cord.touchUp(x, y);
        }
    }

    public void touchDragged(int x, int y) {
        for(Cord cord : cords) {
            cord.touchDragged(x, y);
        }
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {
        for(Switch s : switches) {
            s.render(sb);
        }
        for(Cord c : cords) {
            c.render(sb);
        }
    }

}
