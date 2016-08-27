package com.distraction.ld36.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GSM {

    private Stack<State> states;

    public GSM() {
        states = new Stack<State>();
    }

    public void setState(State s) {
        states.pop();
        states.push(s);
    }

    public void pushState(State s) {
        states.push(s);
    }

    public void popState() {
        states.pop();
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
