package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Switch extends GameObject {

    private enum Position {
        FRONT(11),
        MIDDLE(0),
        BACK(-11);

        private int value;

        Position(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Position position;
    private Cord cord;

    private TextureRegion bg;
    private TextureRegion switchImage;

    public Switch(int x, int y, Cord cord) {
        this.x = x;
        this.y = y;
        this.cord = cord;
        position = Position.MIDDLE;

        bg = Content.getAtlas("main").findRegion("switch_bg");
        switchImage = Content.getAtlas("main").findRegion("switch");
        width = switchImage.getRegionWidth();
        height = switchImage.getRegionHeight();
    }

    public void flipUp() {
        if (position == Position.BACK) {
            position = Position.MIDDLE;
        } else if (position == Position.MIDDLE) {
            position = Position.FRONT;
        }
        updateCaller();
    }

    public void flipDown() {
        if (position == Position.FRONT) {
            position = Position.MIDDLE;
        } else if (position == Position.MIDDLE) {
            position = Position.BACK;
        }
        updateCaller();
    }

    private void updateCaller() {
        if (cord == null || cord.getJack() == null) {
            return;
        }
        if (position == Position.FRONT) {
            cord.getJack().talk();
        } else if (position == Position.MIDDLE) {
            cord.getJack().link();
        } else if (position == Position.BACK) {
            cord.getJack().ring();
        }
    }

    @Override
    public boolean contains(float x, float y) {
        return x > this.x - width / 2 &&
                x < this.x + width / 2 &&
                y > this.y - height / 2 + position.getValue() &&
                y < this.y + height / 2 + position.getValue();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(bg, x - width / 2, y - bg.getRegionHeight() / 2);
        sb.draw(switchImage, x - width / 2, y - height / 2 + position.getValue());
    }

}
