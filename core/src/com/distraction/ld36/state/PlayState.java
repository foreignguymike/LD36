package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.game.BottomPanel;
import com.distraction.ld36.game.Manual;
import com.distraction.ld36.game.TopPanel;

public class PlayState extends State {

    private Manual manual;
    private TopPanel topPanel;
    private BottomPanel bottomPanel;

    public PlayState(GSM gsm) {
        super(gsm);

        manual = new Manual();
        topPanel = new TopPanel(manual);
        bottomPanel = new BottomPanel();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {
        topPanel.update(dt);
        bottomPanel.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        topPanel.render(sb);
        bottomPanel.render(sb);
        sb.end();
    }

    @Override
    public boolean touchDragged(int x, int y, int p) {
        m.x = x;
        m.y = y;
        unproject();
        bottomPanel.touchDragged((int) m.x, (int) m.y);
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        m.x = x;
        m.y = y;
        unproject();
        bottomPanel.touchDown((int) m.x, (int) m.y);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int p, int b) {
        m.x = x;
        m.y = y;
        unproject();
        bottomPanel.touchUp((int) m.x, (int) m.y);
        return true;
    }
}
