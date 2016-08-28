package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Cord extends GameObject {

    private boolean dragging;
    private int xdest;
    private int ydest;
    private Jack jack;

    private TextureRegion image;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
        xdest = x;
        ydest = y;

        image = new TextureRegion(Content.getTexture("test"));
        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    public void setDraggingFalse() {
        dragging = false;
    }

    public void setDragging(float x, float y) {
        dragging = true;
        xdest = (int) x;
        ydest = (int) y;
    }

    public void setJack(Jack jack) {
        this.jack = jack;
    }

    public Jack getJack() {
        return jack;
    }

    @Override
    public boolean contains(float x, float y) {
        if (jack != null && jack.isAvailable()) {
            return x > jack.getx() - jack.getWidth() / 2 &&
                    x < jack.getx() + jack.getWidth() / 2 &&
                    y > jack.gety() - jack.getHeight() / 2 &&
                    y < jack.gety() + jack.getHeight() / 2;
        } else {
            return x > this.x - width / 2 &&
                    x < this.x + width / 2 &&
                    y > this.y - height / 2 &&
                    y < this.y + height / 2;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.RED);
        if (dragging) {
            sb.draw(image, xdest - width / 2, ydest - height / 2);
        }
        if (jack != null) {
            sb.draw(image, jack.getx() - width / 2, jack.gety() - height / 2);
        } else if (!dragging){
            sb.draw(image, x - width / 2, y - height / 2);
        }
    }

}
