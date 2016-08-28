package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class Person extends GameObject {

    private TextureRegion image;
    private TextureRegion pixel;

    private String id;
    private String areaCode;
    private String formattedAreaCode;
    private String number;

    private Jack originalJack;
    private Jack callingJack;

    private int xdest;
    private int ydest;
    private float speed = 300;

    private boolean cleared;

    private BitmapFont font;
    private float idWidth;
    private float numberWidth;

    private boolean waiting = true;
    private float patienceTime = (float) (Math.random() * Vars.PATIENCE_RAND + Vars.PATIENCE_MIN_TIME);
    private Color patienceColor = new Color(0.1f, 0.8f, 0.1f, 0.4f);

    public Person(String areaCode, Jack originalJack, Jack callingJack) {
        this.areaCode = areaCode;
        this.originalJack = originalJack;
        this.callingJack = callingJack;
        formattedAreaCode = "(" + areaCode + ")";
        number = Manual.formatRandomNumberFromAreaCode(areaCode);

        image = Content.getAtlas("main").findRegion("person_bg");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
        x = Vars.PANEL_WIDTH + width / 2;
        y = -height / 2;
        xdest = x;
        ydest = y;

        id = originalJack.getId();

        font = Content.getFont("mainFont");
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, String.valueOf(id));
        idWidth = glyph.width;
        glyph.setText(font, formattedAreaCode);
        numberWidth = glyph.width;

        pixel = Content.getAtlas("main").findRegion("pixel");
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return number;
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

    public void stopWaiting() {
        waiting = false;
    }

    public void update(float dt) {

        if (waiting) {
            patienceTime -= dt;
            if(patienceTime < 0) {
                remove();
                originalJack.clear();
                callingJack.clear();
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
            sb.setColor(patienceColor);
            sb.draw(pixel, x - width / 2, y - height / 2, width * patienceTime / Vars.PATIENCE_MAX_TIME, height);
        }
        font.setColor(Color.BLACK);
        font.draw(sb, id, x - width / 2 + 14 - idWidth / 2, y + 4);
        font.draw(sb, formattedAreaCode, x + width / 2 - 40 - numberWidth / 2, y + 4);
    }

}
