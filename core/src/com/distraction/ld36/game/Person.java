package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class Person extends GameObject {

    public static float REFILL_MULTIPLIER = 2;

    private TextureRegion image;
    private TextureRegion pixel;

    private String id;
    private String otherId;

    private Jack originalJack;
    private Jack callingJack;

    private int xdest;
    private int ydest;
    private float speed;

    private boolean removing;
    private boolean cleared;

    private BitmapFont font;
    private float idWidth;
    private float otherIdWidth;

    private boolean waiting = true;
    private float patienceTime = (float) (Math.random() * Vars.PATIENCE_RAND + Vars.PATIENCE_MIN_TIME);
    private Color patienceColorGreen = new Color(0.1f, 0.6f, 0.1f, 0.4f);
    private Color patienceColorRed = new Color(0.8f, 0.1f, 0.1f, 0.4f);

    public Person(Jack originalJack, Jack callingJack) {
        this.originalJack = originalJack;
        this.callingJack = callingJack;

        image = Content.getAtlas("main").findRegion("person_bg");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
        x = Vars.PANEL_WIDTH + width / 2;
        y = -height / 2;
        xdest = x;
        ydest = y;

        speed = 300;

        id = originalJack.getId();
        otherId = callingJack.getId();

        font = Content.getFont("mainFont");

        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, id);
        idWidth = glyph.width;
        glyph.setText(font, otherId);
        otherIdWidth = glyph.width;

        pixel = Content.getAtlas("main").findRegion("pixel");
    }

    public void setDest(int xdest, int ydest) {
        this.xdest = xdest;
        this.ydest = ydest;
    }

    public int getxdest() {
        return xdest;
    }

    public void remove() {
        removing = true;
        xdest = Vars.WIDTH + width;
        originalJack.clear();
        callingJack.clear();
    }

    public boolean isCleared() {
        return cleared;
    }

    public void update(float dt) {

        if (originalJack.getCord() != null && callingJack.getCord() != null) {
            waiting = false;
        }

        if (!removing) {
            if (waiting) {
                patienceTime -= dt;
                if (patienceTime < 0) {
                    patienceTime = 0;
                    waiting = false;
                    System.out.println("remove1");
                    remove();
                }
            } else {
                patienceTime += REFILL_MULTIPLIER * dt;
                if (patienceTime > Vars.PATIENCE_MAX_TIME) {
                    patienceTime = Vars.PATIENCE_MAX_TIME;
                    System.out.println("remove2");
                    remove();
                }
            }
        }

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
        if (x > Vars.WIDTH + width / 2) {
            cleared = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(image, x - width / 2, y - height / 2, width, height);
        if (waiting) {
            sb.setColor(patienceColorRed);
        } else {
            sb.setColor(patienceColorGreen);
        }
        sb.draw(pixel, x - width / 2, y - height / 2, width * patienceTime / Vars.PATIENCE_MAX_TIME, height);
        font.setColor(Color.BLACK);
        font.draw(sb, id, x - width / 4 - idWidth / 2, y + 4);
        font.draw(sb, otherId, x + width / 4 - otherIdWidth / 2, y + 4);
    }

}
