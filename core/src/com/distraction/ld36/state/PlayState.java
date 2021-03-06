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
import com.distraction.ld36.game.Manual;
import com.distraction.ld36.game.Person;
import com.distraction.ld36.game.ScrollingText;
import com.distraction.ld36.game.Switch;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State implements Jack.JackListener {

    private Manual manual;

    private Jack[][] jacks;
    private Switch[][] switches;
    private Cord[][] cords;

    private List<Person> callers;

    private float time;
    private float nextTime;
    private int callCount = 0;

    private Switch draggingSwitch;
    private int draggingSwitchy;

    private Cord draggingCord;
    private int draggingCordRow;
    private int draggingCordCol;

    private TextureRegion bg;
    private BitmapFont font;

    private Button manualButton;
    private Button finishButton;

    private int points;
    private boolean done;

    private ScrollingText scrollingText;

    public PlayState(GSM gsm) {
        super(gsm);

        manual = new Manual();

        initJacks();
        initCords();
        initSwitches();

        callers = new ArrayList<Person>();

        bg = Content.getAtlas("main").findRegion("bg");
        font = Content.getFont("bigFont");

        nextTime = getNextTime();

        scrollingText = new ScrollingText("START");

        manualButton = new Button("MANUAL", Vars.HEIGHT - 15);
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
                        row * jacks[0].length + col,
                        this);
            }
        }
        nextTime = 0;
    }

    private void initSwitches() {
        switches = new Switch[2][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / switches[0].length;
        for (int row = 0; row < switches.length; row++) {
            for (int col = 0; col < switches[0].length; col++) {
                switches[row][col] = new Switch(col * width + width / 2 - 11, Vars.HEIGHT / 5 + 25 - row * 50, cords[row][col]);
            }
        }
    }

    private void initCords() {
        cords = new Cord[2][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / cords[0].length;
        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[0].length; col++) {
                cords[row][col] = new Cord(col * width + width / 2 + 11, Vars.HEIGHT / 5 + 25 - row * 50);
            }
        }
    }

    private float getNextTime() {
        if (callCount == Vars.CALL_TIMES.length) {
            scrollingText = new ScrollingText("FINISHED!");
            finishButton = new Button("FINISH", Vars.HEIGHT - 45);
            done = true;
            return -1;
        }
        if (callCount == 5) {
            scrollingText = new ScrollingText("RUSH!");
        } else if (callCount == 10) {
            scrollingText = new ScrollingText("BULLET!");
        }
        return Vars.CALL_TIMES[callCount++];
    }

    private void createCaller() {

        List<Manual.Element> elements = new ArrayList<Manual.Element>();
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                if (jacks[row][col].isAvailable()) {
                    elements.add(new Manual.Element(row, col));
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

            String areaCode = manual.getAreaCode(row2, col2);
            Manual.Element element = manual.getCoordinatesFromAreaCode(areaCode);
            Person caller = new Person(areaCode, jacks[row][col], jacks[element.getRow()][element.getCol()]);
            jacks[row][col].setCaller(caller);
            break;

        } while (true);

    }

    @Override
    public void onTalk(Person caller) {
        if (!callers.contains(caller)) {
            callers.add(0, caller);
        }
    }

    @Override
    public void finish() {
        points++;
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

        for (Jack[] jackArray : jacks) {
            for (Jack jack : jackArray) {
                jack.update(dt);
            }
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

        Gdx.gl.glClearColor(0.4f, 0.2f, 0.2f, 1);
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

        for (Switch[] switchArray : switches) {
            for (Switch s : switchArray) {
                s.render(sb);
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

        manualButton.render(sb);
        if (finishButton != null) {
            finishButton.render(sb);
        }

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

        if (manualButton.contains(m.x, m.y)) {
            gsm.setUpdateDepth(2);
            gsm.pushState(new ManualState(gsm, manual));
        }

        if (finishButton != null && finishButton.contains(m.x, m.y)) {
            gsm.setState(new FinishState(gsm, points));
        }

        for (Jack[] jackArray : jacks) {
            for (Jack jack : jackArray) {
                if (jack.contains(m.x, m.y)) {
                }
            }
        }

        for (int row = 0; row < switches.length; row++) {
            for (int col = 0; col < switches[row].length; col++) {
                Switch s = switches[row][col];
                if (s.contains(m.x, m.y)) {
                    draggingSwitch = s;
                    draggingSwitchy = (int) m.y;
                }
            }
        }

        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[row].length; col++) {
                Cord cord = cords[row][col];
                if (cord.contains(m.x, m.y)) {
                    draggingCord = cord;
                    draggingCordRow = row;
                    draggingCordCol = col;
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
                        if (jack.getCord() == null) {
                            Cord matchingCord;
                            if (draggingCordRow == 0) {
                                matchingCord = cords[1][draggingCordCol];
                            } else {
                                matchingCord = cords[0][draggingCordCol];
                            }
                            if (matchingCord.getJack() == null) {
                                if (jack.isCaller()) {
                                    draggingCord.setJack(jack);
                                    jack.setCord(draggingCord);
                                    hit = true;
                                    break;
                                }
                            } else {
                                Jack matchingJack = matchingCord.getJack();
                                if (matchingJack.getCallingJack() == jack) {
                                    draggingCord.setJack(jack);
                                    jack.setCord(draggingCord);
                                    hit = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (!hit) {
                draggingCord.setToOriginalPosition();
            }

            draggingCord.setDraggingFalse();
            draggingCord = null;
        }

        if (draggingSwitch != null) {
            if (m.y > draggingSwitchy) {
                draggingSwitch.flipUp();
            } else if (m.y < draggingSwitchy) {
                draggingSwitch.flipDown();
            }
            draggingSwitch = null;
        }

        return true;
    }
}
