package com.distraction.ld36.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;
import com.distraction.ld36.game.GameObject;

public class TransitionState extends State {

    private static class Expand extends GameObject {

        private boolean expanding;
        private boolean contracting;

        private float totalWidth;
        private float totalHeight;
        private float time;
        private float timer = 0.5f;

        private TextureRegion pixel;

        public Expand(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.totalWidth = width;
            this.totalHeight = height;
            expanding = true;

            pixel = Content.getAtlas("main").findRegion("pixel");
        }

        public boolean isDoneExpanding() {
            return width == totalWidth && height == totalHeight;
        }
        public boolean isDoneContracting() {
            return width == 0 && height == 0;
        }

        public void setContracting(float timer) {
            this.time = timer;
            expanding = false;
            contracting = true;
        }

        public void setTime(float t) {
            time = t;
        }

        public void update(float dt) {
            if(expanding) {
                if(width < totalWidth && height < totalHeight) {
                    time += dt;
                    width = (time / timer) * totalWidth;
                    height = (time / timer) * totalHeight;
                    if(width < 0) width = 0;
                    if(height < 0) height = 0;
                    if(width > totalWidth) width = totalWidth;
                    if(height > totalHeight) height = totalHeight;
                }
            }
            else if(contracting) {
                time += dt;
                width = totalWidth * (1 - (time / timer));
                height = totalHeight * (1 - (time / timer));
                if(width < 0) width = 0;
                if(height < 0) height = 0;
                if(width > totalWidth) width = totalWidth;
                if(height > totalHeight) height = totalHeight;
            }
        }

        public void render(SpriteBatch sb) {
            sb.setColor(0, 0, 0, 1);
            sb.draw(pixel, x - width / 2, y - height / 2, width, height);
            sb.setColor(1, 1, 1, 1);
        }
    }

    private State prev;
    private State next;

    private Expand[][] expands;
    private boolean doneExpanding;
    private boolean doneContracting;

    public TransitionState(GSM gsm, State prev, State next) {

        super(gsm);

        this.prev = prev;
        this.next = next;
        int size = 50;
        expands = new Expand[Vars.HEIGHT / size][Vars.WIDTH / size];
        for (int row = 0; row < expands.length; row++) {
            for (int col = 0; col < expands[0].length; col++) {
                expands[row][col] = new Expand(
                        col * size + size / 2,
                        row * size + size / 2,
                        size,
                        size);
                expands[row][col].setTime(
                        (-(expands.length - row) - col) * 0.05f);
            }
        }

    }

    @Override
    public void onResume() {

    }

    public void update(float dt) {
        if (!doneExpanding) {
            System.out.println("expanding");
            boolean okay = true;
            for (int row = 0; row < expands.length; row++) {
                for (int col = 0; col < expands[0].length; col++) {
                    expands[row][col].update(dt);
                    if (!expands[row][col].isDoneExpanding()) {
                        okay = false;
                    }
                }
            }
            if (okay && !doneExpanding) {
                doneExpanding = true;
                for (int row = 0; row < expands.length; row++) {
                    for (int col = 0; col < expands[0].length; col++) {
                        expands[row][col].setContracting(
                                (-(expands.length - row) - col) * 0.05f);
                    }
                }
            }
        } else {
            boolean okay = true;
            for (int row = 0; row < expands.length; row++) {
                for (int col = 0; col < expands[0].length; col++) {
                    expands[row][col].update(dt);
                    if (!expands[row][col].isDoneContracting()) {
                        okay = false;
                    }
                }
            }
            if (okay && !doneContracting) {
                doneContracting = true;
                gsm.setState(next);
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (!doneExpanding) {
            prev.render(sb);
        } else {
            next.render(sb);
        }
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for (int row = 0; row < expands.length; row++) {
            for (int col = 0; col < expands[0].length; col++) {
                expands[row][col].render(sb);
            }
        }
        sb.end();

    }

}
