package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Manual;

public class ManualState extends State {

    private String[][] areaCodes;

    private BitmapFont font;

    public ManualState(GSM gsm, Manual manual) {
        super(gsm);
        String[][] areaCodes = manual.getAreaCodes();

        this.areaCodes = new String[areaCodes.length][areaCodes[0].length];
        for (int row = 0; row < areaCodes.length; row++) {
            for (int col = 0; col < areaCodes[0].length; col++) {
                this.areaCodes[row][col] = areaCodes[row][col] + " -> " + (row * areaCodes[0].length + col);
            }
        }

        font = Content.getFont("bigFont");
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
        for (int row = 0; row < areaCodes.length; row++) {
            for (int col = 0; col < areaCodes[0].length; col++) {
                font.setColor(Color.BLACK);
                font.draw(sb, areaCodes[row][col], row * Vars.WIDTH / 3 + 15, Vars.HEIGHT - col * 30 - 60);
            }
        }
        sb.end();
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        gsm.setUpdateDepth(1);
        gsm.popState();
        return true;
    }
}
