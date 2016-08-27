package com.distraction.ld36;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.state.GSM;
import com.distraction.ld36.state.PlayState;

public class LD36 extends ApplicationAdapter {

    private SpriteBatch sb;

    private GSM gsm;

    @Override
    public void create() {

        Gdx.gl.glClearColor(0, 0, 0, 1);

        sb = new SpriteBatch();

        gsm = new GSM();
        gsm.pushState(new PlayState(gsm));

    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);

    }
}
