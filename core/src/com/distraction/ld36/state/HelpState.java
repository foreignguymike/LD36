package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Button;
import com.distraction.ld36.game.Cord;
import com.distraction.ld36.game.Jack;
import com.distraction.ld36.game.Person;

import java.util.ArrayList;
import java.util.List;

public class HelpState extends State implements Person.FinishListener {

    private Jack[][] jacks;
    private Cord[][] cords;

    private List<Person> callers;

    private float time;

    private Cord draggingCord;
    private PlayState.Element draggingElement;

    private TextureRegion bg;
    private BitmapFont font;

    private Button button;

    public HelpState(GSM gsm) {
        super(gsm);

        initJacks();
        initCords();

        callers = new ArrayList<Person>();
        createCaller();

        bg = Content.getAtlas("main").findRegion("bg");
        font = Content.getFont("tutorial");

        button = new Button("BACK", Vars.WIDTH - (Vars.WIDTH - Vars.PANEL_WIDTH) / 2, Vars.HEIGHT - 15);

        Content.getSound("start").play();
    }

    private void initJacks() {
        jacks = new Jack[Vars.NUM_JACK_ROWS][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / jacks[0].length;
        int height = Vars.HEIGHT / 2 / jacks.length;
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                jacks[row][col] = new Jack(
                        col * width + width / 2,
                        Vars.HEIGHT - (row * height + height / 2) - 10,
                        row * jacks[0].length + col);
            }
        }
    }

    private void initCords() {
        cords = new Cord[2][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / cords[0].length;
        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[0].length; col++) {
                cords[row][col] = new Cord(col * width + width / 2, Vars.HEIGHT / 5 + 25 - row * 50);
            }
        }
    }

    private void createCaller() {

        List<PlayState.Element> elements = new ArrayList<PlayState.Element>();
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                if (jacks[row][col].isAvailable()) {
                    elements.add(new PlayState.Element(row, col));
                }
            }
        }

        int rand1 = (int) (Math.random() * elements.size());

        do {
            int rand2 = (int) (Math.random() * elements.size());

            if (rand1 == rand2) {
                continue;
            }

            int row = elements.get(rand1).getRow();
            int col = elements.get(rand1).getCol();
            int row2 = elements.get(rand2).getRow();
            int col2 = elements.get(rand2).getCol();

            callers.add(0, new Person(jacks[row][col], jacks[row2][col2], this));
            jacks[row][col].setOtherJack(jacks[row2][col2]);
            jacks[row2][col2].setOtherJack(jacks[row][col]);
            Content.getSound("ring").play(0.3f);
            break;

        } while (true);

    }

    @Override
    public void onFinish() {
        Content.getSound("point").play(0.7f);
    }

    @Override
    public void onResume() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {

        time += dt;

        for (int i = 0; i < callers.size(); i++) {
            Person caller = callers.get(i);
            caller.setDest(caller.getxdest(), i * caller.getHeight() + caller.getHeight() / 2);
        }

        for (int i = callers.size() - 1; i >= 0; i--) {
            Person caller = callers.get(i);
            caller.update(dt);
            if (caller.isCleared()) {
                callers.remove(i);
                createCaller();
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(0.92f, 0.92f, 0.92f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        sb.setColor(Color.WHITE);
        sb.draw(bg, 0, 0);

        for (Jack[] jackArray : jacks) {
            for (Jack jack : jackArray) {
                jack.render(sb);
            }
        }

        for (Cord[] cordArray : cords) {
            for (Cord cord : cordArray) {
                cord.render(sb);
            }
        }

        for (Person caller : callers) {
            caller.render(sb);
        }

        font.setColor(Color.BLACK);

        int h = Vars.HEIGHT - 25;
        font.draw(sb, "To play, you", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "must connect", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "two cords from", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "the same", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "column at the", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "bottom to the", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "correct jacks", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "at the top", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "using drag", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "and drop.", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "The correct", Vars.PANEL_WIDTH + 5, h -= 20);
        font.draw(sb, "jacks are", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "shown by the", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "incoming calls", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "on this panel.", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "Each call has", Vars.PANEL_WIDTH + 5, h -= 20);
        font.draw(sb, "a time limit", Vars.PANEL_WIDTH + 5, h -= 10);
        font.draw(sb, "so be quick!", Vars.PANEL_WIDTH + 5, h -= 10);

        button.render(sb);

        sb.end();
    }

    @Override
    public boolean touchDragged(int x, int y, int p) {
        m.x = x;
        m.y = y;
        unproject();

        if (draggingCord != null) {
            draggingCord.setDragging(m.x, m.y);
        }

        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int p, int b) {
        m.x = x;
        m.y = y;
        unproject();

        if (button.contains(m.x, m.y)) {
            gsm.setState(new TransitionState(gsm, this, new MenuState(gsm)));
        }

        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[row].length; col++) {
                Cord cord = cords[row][col];
                if (cord.contains(m.x, m.y)) {
                    draggingCord = cord;
                    draggingElement = new PlayState.Element(row, col);
                    Jack jack = draggingCord.getJack();
                    if (jack != null) {
                        jack.setCord(null);
                    }
                    draggingCord.setJack(null);
                }
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int p, int b) {
        m.x = x;
        m.y = y;
        unproject();

        if (draggingCord != null) {

            boolean hit = false;
            for (int row = 0; row < jacks.length; row++) {
                for (int col = 0; col < jacks[row].length; col++) {
                    Jack jack = jacks[row][col];
                    if (jack.contains(m.x, m.y)) {

                        if (jack.getOtherJack() != null && jack.getCord() == null) {

                            Cord matchingCord;
                            int draggingCordRow = draggingElement == null ? 0 : draggingElement.getRow();
                            int draggingCordCol = draggingElement == null ? 0 : draggingElement.getCol();
                            if (draggingCordRow == 0) {
                                matchingCord = cords[1][draggingCordCol];
                            } else {
                                matchingCord = cords[0][draggingCordCol];
                            }

                            if (jack.getOtherJack().getCord() == null && matchingCord.getJack() == null) {
                                draggingCord.setJack(jack);
                                jack.setCord(draggingCord);
                                hit = true;
                                break;
                            } else {
                                if (matchingCord == jack.getOtherJack().getCord()) {
                                    draggingCord.setJack(jack);
                                    jack.setCord(draggingCord);
                                    hit = true;
                                    break;
                                } else {
                                    draggingCord.setToOriginalPosition();
                                }
                            }
                        } else {
                            draggingCord.setToOriginalPosition();
                        }
                    } else {
                        draggingCord.setToOriginalPosition();
                    }
                }
                if (hit) {
                    break;
                }

            }

            if (hit) {
                Content.getSound("plugin").play(0.2f);
            } else {
                Content.getSound("miss").play(0.5f);
            }

            draggingCord.setDraggingFalse();
            draggingCord = null;
        }

        return true;
    }

}
