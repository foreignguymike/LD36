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
        jack = null;
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
            xdest = jack.getx();
            ydest = jack.gety();
        }
    }

    public void setToOriginalPosition() {
        xdest = x;
        ydest = y;
    }

    public Jack getJack() {
        return jack;
    }

    @Override
    public boolean contains(float x, float y) {
        boolean inBounds = x > xdest - width / 2 &&
                    x < xdest + width / 2 &&
                    y > ydest - height / 2 &&
                    y < ydest + height / 2;
        if(inBounds) {
            return !(jack != null && !jack.isFinished());
        }
        return false;
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
        sb.draw(cordImage, xdest - cordImage.getRegionWidth() / 2, ydest - cordImage.getRegionHeight() / 2);
    }

}
