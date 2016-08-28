package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Cord;
import com.distraction.ld36.game.Jack;
import com.distraction.ld36.game.Person;
import com.distraction.ld36.game.ScrollingText;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State implements Person.FinishListener {

    private Jack[][] jacks;
    private Cord[][] cords;

    private List<Person> callers;

    private float time;
    private float nextTime;
    private int callCount = 0;

    private Cord draggingCord;
    private Element draggingElement;

    private TextureRegion bg;
    private BitmapFont font;

    private int points;
    private boolean done;

    private ScrollingText scrollingText;

    public PlayState(GSM gsm) {
        super(gsm);

        initJacks();
        initCords();

        callers = new ArrayList<Person>();

        bg = Content.getAtlas("main").findRegion("bg");
        font = Content.getFont("mainFont");

        nextTime = getNextTime();

        scrollingText = new ScrollingText("START");
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
        nextTime = 0;
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

    private float getNextTime() {
        if (callCount == Vars.NUM_CALLS) {
            scrollingText = new ScrollingText("FINISHED!");
            Content.getSound("start").play();
            done = true;
            return -1;
        } else if (callCount == Vars.CALL_TIMES1.length) {
            scrollingText = new ScrollingText("RUSH!");
            Content.getSound("start").play();
        } else if (callCount == Vars.CALL_TIMES1.length + Vars.CALL_TIMES2.length) {
            scrollingText = new ScrollingText("BULLET!");
            Content.getSound("start").play();
        }
        return Vars.getNextTime(callCount++);
    }

    private void createCaller() {

        List<Element> elements = new ArrayList<Element>();
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                if (jacks[row][col].isAvailable()) {
                    elements.add(new Element(row, col));
                }
            }
        }
        if (elements.size() < 2) {
            nextTime = getNextTime();
            return;
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
        points++;
        Content.getSound("point").play();
    }

    @Override
    public void onResume() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void update(float dt) {

        // next caller timer
        time += dt;

        // create a new caller
        if (!done && time >= nextTime) {
            time = 0;
            nextTime = getNextTime();
            createCaller();
        }

        for (int i = 0; i < callers.size(); i++) {
            Person caller = callers.get(i);
            caller.setDest(caller.getxdest(), i * caller.getHeight() + caller.getHeight() / 2);
        }

        for (int i = callers.size() - 1; i >= 0; i--) {
            Person caller = callers.get(i);
            caller.update(dt);
            if (caller.isCleared()) {
                callers.remove(i);
            }
        }

        if (scrollingText != null) {
            scrollingText.update(dt);
            if (scrollingText.getx() < -scrollingText.getWidth()) {
                scrollingText = null;
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
        font.draw(sb, "TOTAL CALLERS:", Vars.PANEL_WIDTH + 5, Vars.HEIGHT - 5);
        font.draw(sb, "" + Vars.NUM_CALLS, Vars.PANEL_WIDTH + 5, Vars.HEIGHT - 15);
        font.draw(sb, "CONNECTED:", Vars.PANEL_WIDTH + 5, Vars.HEIGHT - 30);
        font.draw(sb, "" + points, Vars.PANEL_WIDTH + 5, Vars.HEIGHT - 40);

        if (scrollingText != null) {
            scrollingText.render(sb);
        }

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

        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[row].length; col++) {
                Cord cord = cords[row][col];
                if (cord.contains(m.x, m.y)) {
                    draggingCord = cord;
                    draggingElement = new Element(row, col);
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

            if(hit) {
                Content.getSound("plugin").play(0.2f);
            } else {
                Content.getSound("miss").play(0.5f);
            }

            draggingCord.setDraggingFalse();
            draggingCord = null;
        }

        return true;
    }

    public static class Element {
        private int row;
        private int col;

        public Element(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }
}
