package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Jack extends GameObject {

    public interface JackListener {
        void finish();
    }

    private String id;
    private JackListener jackListener;

    private Cord cord;
    private Jack otherJack;

    private boolean finished;

    private TextureRegion jackImage;
    private TextureRegion greenLightImage;
    private TextureRegion yellowLightImage;
    private TextureRegion offLightImage;

    private BitmapFont font;
    private float fontWidth;

    public Jack(int x, int y, int callingId, JackListener jackListener) {
        this.x = x;
        this.y = y;
        this.id = String.valueOf(callingId);
        this.jackListener = jackListener;

        jackImage = Content.getAtlas("main").findRegion("jack");
        greenLightImage = Content.getAtlas("main").findRegion("jack_light_green");
        yellowLightImage = Content.getAtlas("main").findRegion("jack_light_yellow");
        offLightImage = Content.getAtlas("main").findRegion("jack_light_off");

        width = jackImage.getRegionWidth();
        height = jackImage.getRegionHeight();

        font = Content.getFont("mainFont");
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, String.valueOf(this.id));
        fontWidth = glyph.width;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return cord == null && otherJack == null;
    }

    public Jack getOtherJack() {
        return otherJack;
    }

    public Cord getCord() {
        return cord;
    }

    public boolean isFinished() {
        return otherJack == null;
    }

    public void setCord(Cord cord) {
        this.cord = cord;
    }

    public void setOtherJack(Jack otherJack) {
        this.otherJack = otherJack;
    }

    public void clear() {
        otherJack = null;

        if (cord != null) {
            cord.setToOriginalPosition();
            cord.setJack(null);
            cord = null;
        }
    }

    public void update(float dt) {

        if (otherJack == null) {
            return;
        }

        if (!finished && cord != null && otherJack.getCord() != null) {
            finished = true;
            jackListener.finish();
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(jackImage, x - width / 2, y - height / 2);

        if (cord != null) {
            if (otherJack.getCord() != null) {
                sb.draw(greenLightImage, x - greenLightImage.getRegionWidth() / 2, y + 12);
            } else {
                sb.draw(yellowLightImage, x - yellowLightImage.getRegionWidth() / 2, y + 12);
            }
        } else {
            sb.draw(offLightImage, x - offLightImage.getRegionWidth() / 2, y + 12);
        }

        font.setColor(Color.WHITE);
        font.draw(sb, id, x - fontWidth / 2, y - height / 2 - 2);
    }

}
