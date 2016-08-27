package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Cord {

    private int x;
    private int y;

    private boolean dragging;
    private int destx;
    private int desty;

    private TextureRegion image;

    public Cord(int x, int y) {
        this.x = x;
        this.y = y;
        destx = x;
        desty = y;

        image = new TextureRegion(Content.getTexture("test"));
    }

    public void touchDown(int x, int y) {
        System.out.println(x + ", " + y);
        if (x > this.x - image.getRegionWidth() / 2 &&
                x < this.x + image.getRegionWidth() / 2 &&
                y > this.y - image.getRegionHeight() / 2 &&
                y < this.y + image.getRegionHeight() / 2) {
            dragging = true;
            System.out.println("YE");
        }
    }

    public void touchUp(int x, int y) {
        dragging = false;
        destx = this.x;
        desty = this.y;
    }

    public void touchDragged(int x, int y) {
        if(dragging) {
            destx = x;
            desty = y;
        }
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.RED);
        sb.draw(image, x - image.getRegionWidth() / 2, y - image.getRegionHeight() / 2);
        if(dragging) {
            sb.draw(image, destx - image.getRegionWidth() / 2, desty - image.getRegionHeight() / 2);
        }
    }

}
