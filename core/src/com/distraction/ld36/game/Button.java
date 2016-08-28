package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class Button extends GameObject {

    private String text;
    private float fontWidth;

    private TextureRegion bg;

    private BitmapFont font;

    public Button(String text, int y) {
        this.text = text;
        x = Vars.WIDTH - (Vars.WIDTH - Vars.PANEL_WIDTH) / 2;
        this.y = y;

        font = Content.getFont("bigFont");
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, text);
        fontWidth = glyph.width;

        bg = Content.getAtlas("main").findRegion("button_bg");
        width = bg.getRegionWidth();
        height = bg.getRegionHeight();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(bg, x - width / 2, y - height / 2);
        font.setColor(Color.BLACK);
        font.draw(sb, text, x - fontWidth / 2, y + 7);
    }

}
