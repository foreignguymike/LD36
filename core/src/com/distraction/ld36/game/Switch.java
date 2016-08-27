package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Switch {

    private enum Position {
        FRONT,
        MIDDLE,
        BACK
    }

    private int x;
    private int y;

    private Position position;

    private TextureRegion image;

    public Switch(int x, int y) {
        this.x = x;
        this.y = y;
        position = Position.MIDDLE;

        image = new TextureRegion(Content.getTexture("test"));
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(image, x - image.getRegionWidth() / 2, y - image.getRegionHeight() / 2);
    }

}
