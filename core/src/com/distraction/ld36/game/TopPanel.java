package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld36.Vars;

public class TopPanel {

    private Manual manual;

    private Jack[][] jacks;

    private float time;
    private float nextTime;

    public TopPanel(Manual manual) {
        this.manual = manual;

        jacks = new Jack[Vars.NUM_JACK_ROWS][Vars.NUM_JACK_COLS];

        int width = Vars.PANEL_WIDTH / jacks[0].length;
        int height = Vars.HEIGHT / 2 / jacks.length;

        for(int row = 0; row < jacks.length; row++) {
            for(int col = 0; col < jacks[0].length; col++) {
                jacks[row][col] = new Jack(col * width + width / 2, Vars.HEIGHT - (row * height + height / 2), row * jacks[0].length + col);
            }
        }

        nextTime = 5;
    }

    private void createCaller() {

        System.out.println("creating caller");

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

                        jacks[row][col].setPerson(new Person(number));

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

    public void update(float dt) {

        time += dt;

        if(time >= nextTime) {
            time = 0;
            nextTime = randomNextTime();
            createCaller();
        }

    }

    public void render(SpriteBatch sb) {

        for(int row = 0; row < jacks.length; row++) {
            for(int col = 0; col < jacks[0].length; col++) {
                jacks[row][col].render(sb);
            }
        }

    }

}
