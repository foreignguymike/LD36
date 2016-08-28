package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Switch extends GameObject {

    private enum Position {
        FRONT(20),
        MIDDLE(0),
        BACK(-20);

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

    private TextureRegion image;

    public Switch(int x, int y, Cord cord) {
        this.x = x;
        this.y = y;
        this.cord = cord;
        position = Position.MIDDLE;

        image = new TextureRegion(Content.getTexture("test"));
        width = image.getRegionWidth();
        height = image.getRegionHeight();
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
        return x > this.x - width / 4 &&
                x < this.x + width / 4 &&
                y > this.y - height / 4 + position.getValue() &&
                y < this.y + height / 4 + position.getValue();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.YELLOW);
        sb.draw(image, x - width / 2, y - height / 2);
        sb.setColor(Color.BLACK);
        sb.draw(image, x - width / 4, y - height / 4 + position.getValue(), width / 2, height / 2);
    }

}
