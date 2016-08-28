package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class Person extends GameObject {

    private TextureRegion image;

    private String number;
    private Jack callingJack;

    private int xdest;
    private int ydest;
    private float speed = 300;

    private boolean cleared;

    public Person(String number, Jack callingJack) {
        this.number = number;
        this.callingJack = callingJack;
        width = Vars.WIDTH - Vars.PANEL_WIDTH;
        height = Vars.CALLER_HEIGHT;
        x = Vars.PANEL_WIDTH + width / 2;
        y = -height / 2;
        xdest = x;
        ydest = y;

        image = new TextureRegion(Content.getTexture("test"));
    }

    public Jack getCallingJack() {
        return callingJack;
    }

    public void setDest(int xdest, int ydest) {
        this.xdest = xdest;
        this.ydest = ydest;
    }

    public int getxdest() {
        return xdest;
    }

    public int getydest() {
        return ydest;
    }

    public void remove() {
        xdest = Vars.WIDTH + width;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void update(float dt) {
        if (x < xdest) {
            x += speed * dt;
            if (x > xdest) {
                x = xdest;
            }
        }
        if (x > xdest) {
            x -= speed * dt;
            if (x < xdest) {
                x = xdest;
            }
        }
        if (y < ydest) {
            y += speed * dt;
            if (y > ydest) {
                y = ydest;
            }
        }
        if (y > ydest) {
            y -= speed * dt;
            if (y < ydest) {
                y = ydest;
            }
        }
        if(x > Vars.WIDTH + width / 2) {
            cleared = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLUE);
        sb.draw(image, x - width / 2, y - height / 2, width, height);
    }

}
