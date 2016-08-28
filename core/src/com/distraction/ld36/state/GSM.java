package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GSM {

    private Stack<State> states;
    private int updateDepth = 1;

    public GSM() {
        states = new Stack<State>();
    }

    public void setUpdateDepth(int updateDepth) {
        this.updateDepth = updateDepth;
    }

    public void setState(State s) {
        states.pop();
        states.push(s);
        Gdx.input.setInputProcessor(states.peek());
    }

    public void pushState(State s) {
        states.push(s);
        Gdx.input.setInputProcessor(states.peek());
    }

    public void popState() {
        states.pop();
        Gdx.input.setInputProcessor(states.peek());
    }

    public void update(float dt) {
        for (int i = 0; i < updateDepth; i++) {
            if (i == states.size()) {
                break;
            }
            states.get(i).update(dt);
        }
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

}
