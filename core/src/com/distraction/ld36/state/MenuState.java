package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Button;

public class MenuState extends State {

    private BitmapFont smallFont;
    private BitmapFont font;

    private Button start;
    private Button help;

    public MenuState(GSM gsm) {
        super(gsm);

        font = Content.getFont("bigFont");
        smallFont = Content.getFont("mainFont");

        start = new Button("START", Vars.WIDTH / 2, Vars.HEIGHT / 2 + 10);
        help = new Button("HELP", Vars.WIDTH / 2, Vars.HEIGHT / 2 - 30);

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
        Gdx.gl.glClearColor(0.92f, 0.92f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        start.render(sb);
        help.render(sb);

        font.setColor(Color.BLACK);
        font.draw(sb, Vars.TITLE, 130, Vars.HEIGHT - 80);

        smallFont.setColor(Color.BLACK);
        smallFont.draw(sb, "LD #36 by Mike", 5, 10);

        sb.end();
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        m.x = x;
        m.y = y;
        unproject();

        if (start.contains(m.x, m.y)) {
            gsm.setState(new TransitionState(gsm, this, new PlayState(gsm)));
        }

        if (help.contains(m.x, m.y)) {
            gsm.setState(new TransitionState(gsm, this, new HelpState(gsm)));
        }

        return false;
    }
}
