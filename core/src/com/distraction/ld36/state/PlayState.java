package com.distraction.ld36.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.game.Manual;
import com.distraction.ld36.game.TopPanel;

public class PlayState extends State {

    private Manual manual;
    private TopPanel topPanel;

    public PlayState(GSM gsm) {
        super(gsm);

        manual = new Manual();
        topPanel = new TopPanel(manual);
    }

    @Override
    public void update(float dt) {
        topPanel.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        topPanel.render(sb);
    }

}
