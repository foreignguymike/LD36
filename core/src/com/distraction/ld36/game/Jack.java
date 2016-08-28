package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Jack extends GameObject {

    public interface JackListener {
        void onTalk(Person person);
    }

    private Person caller;
    private int id;
    private JackListener jackListener;

    private TextureRegion image;

    private boolean lit;
    private Cord cord;
    private Jack callingJack;

    private float pickupTime;
    private float pickupTimer;

    private float talkTime;
    private float talkTimer;

    private boolean ringing;
    private boolean pickedUp;
    private boolean linked;
    private boolean talking;

    private BitmapFont font;

    public Jack(int x, int y, int id, JackListener jackListener) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.jackListener = jackListener;

        image = new TextureRegion(Content.getTexture("test"));
        width = image.getRegionWidth();
        height = image.getRegionHeight();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    public boolean isAvailable() {
        return !lit && callingJack == null;
    }

    public void talk() {
        if (caller == null) {
            return;
        }
        jackListener.onTalk(caller);
        ringing = false;
    }

    public void link() {
        if ((callingJack == null || ringing) && caller == null) {
            return;
        }
        linked = true;
        if (callingJack.isLinked()) {
            setTalkTimer((float) (Math.random() * 6 + 2));
            callingJack.setTalkTimer(talkTimer);
            setTalking(true);
            callingJack.setTalking(true);
        }
    }

    public void ring() {
        if (pickedUp) {
            return;
        }
        if (callingJack != null) {
            ringing = true;
            pickupTimer = (float) (Math.random() * 2 + 2);
        }
    }

    public void setCaller(Person caller) {
        if (caller == null) {
            lit = false;
            callingJack.setCallingJack(null);
        } else {
            lit = true;
            setCallingJack(caller.getCallingJack());
            callingJack.setCallingJack(this);
        }
        this.caller = caller;
    }

    public void setCallingJack(Jack callingJack) {
        this.callingJack = callingJack;
    }

    public void setCord(Cord cord) {
        this.cord = cord;
    }

    public void setTalking(boolean talking) {
        this.talking = talking;
    }

    public void setTalkTimer(float talkTimer) {
        this.talkTimer = talkTimer;
    }

    public boolean isLinked() {
        return linked;
    }

    public boolean isCaller() {
        return caller != null;
    }

    public Jack getCallingJack() {
        return callingJack;
    }

    public Cord getCord() {
        return cord;
    }

    public void update(float dt) {
        if (ringing) {
            pickupTime += dt;
            if (pickupTime >= pickupTimer) {
                ringing = false;
                pickedUp = true;
            }
        }
        if (talking) {
            talkTime += dt;
            if (talkTime >= talkTimer) {
                if (caller != null) {
                    caller.remove();
                }
                caller = null;
                clear();
            }
        }
    }

    private void clear() {
        lit = false;
        cord = null;
        callingJack = null;
        pickupTime = 0;
        talkTime = 0;
        ringing = false;
        pickedUp = false;
        linked = false;
        talking = false;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(image, x - width / 2, y - height / 2);
        if (talking) {
            sb.setColor(Color.RED);
        } else if (lit) {
            sb.setColor(Color.GREEN);
        } else if (ringing) {
            sb.setColor(Color.YELLOW);
        } else if (pickedUp) {
            sb.setColor(Color.CORAL);
        } else if (callingJack != null) {
            sb.setColor(Color.ORANGE);
        }
        sb.draw(image, x - width / 2 + 5, y - height / 2 + 20);
        sb.setColor(Color.BLACK);
        font.draw(sb, "" + id, x - width / 2 - 5, y - height / 2 + 30);
    }

}
