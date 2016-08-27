package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Vars;

public class BottomPanel {

    private Column[] columns;

    public BottomPanel() {
        columns = new Column[Vars.NUM_JACK_ROWS];

        int width = Vars.PANEL_WIDTH / columns.length;
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new Column(i * width + width / 2, Vars.HEIGHT / 2 / 2);
        }
    }

    public void touchDown(int x, int y) {
        for (Column column : columns) {
            column.touchDown(x, y);
        }
    }

    public void touchUp(int x, int y) {
        for (Column column : columns) {
            column.touchUp(x, y);
        }
    }

    public void touchDragged(int x, int y) {
        for (Column column : columns) {
            column.touchDragged(x, y);
        }
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {
        for (Column column : columns) {
            column.render(sb);
        }
    }

}
