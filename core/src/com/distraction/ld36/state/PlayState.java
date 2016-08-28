package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Cord;
import com.distraction.ld36.game.Jack;
import com.distraction.ld36.game.Manual;
import com.distraction.ld36.game.Person;
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

    private Switch draggingSwitch;
    private int draggingSwitchy;

    private Cord draggingCord;
    private int draggingCordRow;
    private int draggingCordCol;

    private TextureRegion bg;

    public PlayState(GSM gsm) {
        super(gsm);

        manual = new Manual();

        initJacks();
        initCords();
        initSwitches();

        callers = new ArrayList<Person>();

        bg = Content.getAtlas("main").findRegion("bg");

        Gdx.input.setInputProcessor(this);
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

    private void createCaller() {

        boolean enough = false;
        int numAvailable = 0;
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                if (jacks[row][col].isAvailable()) {
                    numAvailable++;
                    if (numAvailable >= 2) {
                        enough = true;
                        break;
                    }
                }
            }
            if (enough) {
                break;
            }
        }
        if(!enough) {
            nextTime = randomNextTime();
            return;
        }

        do {

            int row = (int) (Math.random() * jacks.length);
            int col = (int) (Math.random() * jacks[0].length);

            if (jacks[row][col].isAvailable()) {

                do {

                    int row2 = (int) (Math.random() * jacks.length);
                    int col2 = (int) (Math.random() * jacks[0].length);

                    if (row == row2 && col == col2) {
                        continue;
                    }

                    if (jacks[row2][col2].isAvailable()) {
                        String areaCode = manual.getAreaCode(row2, col2);
                        Manual.Element element = manual.getCoordinatesFromAreaCode(areaCode);
                        Person caller = new Person(areaCode, jacks[element.getRow()][element.getCol()]);
                        jacks[row][col].setCaller(caller);
                        break;
                    }

                } while (true);

                break;
            }
        } while (true);

    }

    private float randomNextTime() {
        return (float) (Math.random() * Vars.CALL_RAND + Vars.CALL_MIN_TIME);
    }

    @Override
    public void onTalk(Person caller) {
        if (!callers.contains(caller)) {
            callers.add(caller);
            System.out.println("jack id: " + manual.getJackIdentifierFromAreaCode(caller.getAreaCode()));
        }
    }

    @Override
    public void update(float dt) {

        // next caller timer
        time += dt;

        // create a new caller
        if (time >= nextTime) {
            time = 0;
            nextTime = randomNextTime();
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

    }

    @Override
    public void render(SpriteBatch sb) {
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
                    draggingCord.setDragging(m.x, m.y);
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
                                }
                            } else {
                                Jack matchingJack = matchingCord.getJack();
                                if (matchingJack.getCallingJack() == jack) {
                                    draggingCord.setJack(jack);
                                    jack.setCord(draggingCord);
                                }
                            }
                        }
                    }
                }
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
