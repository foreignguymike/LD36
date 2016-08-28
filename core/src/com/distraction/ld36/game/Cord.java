package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.ld36.Content;

public class Cord extends GameObject {

    private boolean dragging;
    private int xdest;
    private int ydest;
    private float dist;
    private float degrees;
    private Jack jack;

    private TextureRegion bg;
    private TextureRegion cordImage;
    private TextureRegion wireImage;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
        xdest = x;
        ydest = y;

        bg = Content.getAtlas("main").findRegion("cord_bg");
        cordImage = Content.getAtlas("main").findRegion("cord");
        wireImage = Content.getAtlas("main").findRegion("cord_wire");
        width = bg.getRegionWidth();
        height = bg.getRegionHeight();
    }

    public void setDraggingFalse() {
        dragging = false;
    }

    public void setDragging(float x, float y) {
        if (jack != null) {
            jack.setCord(null);
            jack = null;
        }
        dragging = true;
        xdest = (int) x;
        ydest = (int) y;
        float dx = xdest - this.x;
        float dy = ydest - this.y;
        dist = (float) (Math.sqrt(dx * dx + dy * dy));
        degrees = MathUtils.atan2(dy, dx) * MathUtils.radDeg;
    }

    public void setJack(Jack jack) {
        this.jack = jack;
        if (jack != null) {
            float dx = jack.getx() - x;
            float dy = jack.gety() - y;
            dist = (float) (Math.sqrt(dx * dx + dy * dy));
            degrees = MathUtils.atan2(dy, dx) * MathUtils.radDeg;
        }
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
        sb.setColor(Color.WHITE);

        sb.draw(bg, x - width / 2, y - height / 2);

        if (dragging || jack != null) {
            sb.draw(
                    wireImage,
                    x - wireImage.getRegionWidth() / 2,
                    y - wireImage.getRegionHeight() / 2,
                    wireImage.getRegionWidth() / 2,
                    wireImage.getRegionHeight() / 2,
                    dist,
                    wireImage.getRegionHeight(),
                    1,
                    1,
                    degrees);
        }

        if (dragging) {
            sb.draw(cordImage, xdest - cordImage.getRegionWidth() / 2, ydest - cordImage.getRegionHeight() / 2);
        } else if (jack != null) {
            sb.draw(cordImage, jack.getx() - cordImage.getRegionWidth() / 2, jack.gety() - cordImage.getRegionHeight() / 2);
        } else {
            sb.draw(cordImage, x - cordImage.getRegionWidth() / 2, y - cordImage.getRegionHeight() / 2);
        }
    }

}
