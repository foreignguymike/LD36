package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class FinishState extends State {

    private String text;
    private float fontWidth;
    private BitmapFont font;

    public FinishState(GSM gsm, int points) {
        super(gsm);

        text = "Final Score: " + points;
        font = Content.getFont("bigFont");
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, text);
        fontWidth = glyph.width;
    }

    @Override
    public void onResume() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Color.WHITE);
        font.draw(sb, text, Vars.WIDTH / 2 - fontWidth / 2, Vars.HEIGHT / 2 + 20);
        sb.end();
    }
}
