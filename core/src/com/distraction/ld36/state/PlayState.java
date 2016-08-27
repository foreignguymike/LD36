package com.distraction.ld36.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.Cord;
import com.distraction.ld36.game.Jack;
import com.distraction.ld36.game.Manual;
import com.distraction.ld36.game.ManualUtils;
import com.distraction.ld36.game.Person;
import com.distraction.ld36.game.Switch;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

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

    public PlayState(GSM gsm) {
        super(gsm);

        manual = new Manual();

        initJacks();
        initSwitches();
        initCords();

        callers = new ArrayList<Person>();

        Gdx.input.setInputProcessor(this);
    }

    private void initJacks() {
        jacks = new Jack[Vars.NUM_JACK_ROWS][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / jacks[0].length;
        int height = Vars.HEIGHT / 2 / jacks.length;
        for (int row = 0; row < jacks.length; row++) {
            for (int col = 0; col < jacks[0].length; col++) {
                jacks[row][col] = new Jack(col * width + width / 2, Vars.HEIGHT - (row * height + height / 2), row * jacks[0].length + col);
            }
        }
        nextTime = 5;
    }

    private void initSwitches() {
        switches = new Switch[2][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / switches[0].length;
        for (int row = 0; row < switches.length; row++) {
            for (int col = 0; col < switches[0].length; col++) {
                switches[row][col] = new Switch(col * width + width / 2 - 10, Vars.HEIGHT / 2 / 2 + 20 - row * 40);
            }
        }
    }

    private void initCords() {
        cords = new Cord[2][Vars.NUM_JACK_COLS];
        int width = Vars.PANEL_WIDTH / cords[0].length;
        for (int row = 0; row < cords.length; row++) {
            for (int col = 0; col < cords[0].length; col++) {
                cords[row][col] = new Cord(col * width + width / 2 + 10, Vars.HEIGHT / 2 / 2 + 20 - row * 40);
            }
        }
    }

    private void createCaller() {

        do {
            int row = (int) (Math.random() * jacks.length);
            int col = (int) (Math.random() * jacks[0].length);

            if (jacks[row][col].isAvailable()) {

                do {

                    int row2 = (int) (Math.random() * jacks.length);
                    int col2 = (int) (Math.random() * jacks[0].length);

                    if (jacks[row2][col2].isAvailable()) {

                        String areaCode = manual.getAreaCode(row2, col2);
                        String number = ManualUtils.formatRandomNumber(areaCode);

                        Person caller = new Person(number);
                        callers.add(caller);
                        jacks[row][col].setPerson(caller);

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
    public void update(float dt) {

        // next caller timer
        time += dt;

        // create a new caller
        if (time >= nextTime) {
            time = 0;
            nextTime = randomNextTime();
            createCaller();
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

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

        for (Cord[] cordArray : cords) {
            for (Cord cord : cordArray) {
                if (cord.contains(m.x, m.y)) {
                    draggingCord = cord;
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

            for (Jack[] jackArray : jacks) {
                for (Jack jack : jackArray) {
                    if (jack.contains(m.x, m.y)) {
                        draggingCord.setJack(jack);
                        jack.setCord(draggingCord);
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
