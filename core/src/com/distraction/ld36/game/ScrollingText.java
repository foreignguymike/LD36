package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class ScrollingText extends GameObject {

    private String text;
    private BitmapFont font;

    public ScrollingText(String text) {
        this.text = text;

        font = Content.getFont("scrollingFont");

        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, text);
        width = (int) glyph.width;
        x = Vars.WIDTH + width / 2;
        y = Vars.HEIGHT / 2 + 30;
    }

    public void update(float dt) {
        if(Math.abs(x - Vars.WIDTH / 2) < 100) {
            x -= 100 * dt;
        } else {
            x -= 1000 * dt;
        }
    }

    public void render(SpriteBatch sb) {
        font.setColor(Color.BLACK);
        font.draw(sb, text, x - width / 2, y);
    }

}
