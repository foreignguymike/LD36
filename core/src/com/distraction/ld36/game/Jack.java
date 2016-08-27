package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Jack {

    private Person person;

    private boolean lit;
    private boolean taken;
    private boolean hasCord;

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isAvailable() {
        return !lit && !taken;
    }

    public void setPerson(Person person) {
        lit = true;
        this.person = person;
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

}
