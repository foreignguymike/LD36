package com.distraction.ld36.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Vars;

public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera cam;

    protected State(GSM gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera(Vars.WIDTH * Vars.SCALE, Vars.HEIGHT * Vars.SCALE);
    }

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

}
