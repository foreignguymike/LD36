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

    private TextureRegion image;

    public Switch(int x, int y) {
        this.x = x;
        this.y = y;
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
    }

    public void flipDown() {
        if (position == Position.FRONT) {
            position = Position.MIDDLE;
        } else if (position == Position.MIDDLE) {
            position = Position.BACK;
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
        sb.draw(image, x - width / 4, y - height / 4 + position.getValue(), width / 4, height / 4);
    }

}
