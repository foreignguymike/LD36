package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;
import com.distraction.ld36.Vars;

public class Jack extends GameObject {

    public interface JackListener {
        void onTalk(Person person);
    }

    private Person caller;
    private String id;
    private JackListener jackListener;

    private boolean lit;
    private Cord cord;
    private Jack callingJack;

    private float baseTime;
    private float callOperatorTime;
    private float pickupTime;
    private float pickupTimer;
    private float talkTime;
    private float talkTimer;

    private boolean talkingTo;
    private boolean ringing;
    private boolean pickedUp;
    private boolean linked;
    private boolean talking;

    private boolean talkedTo;

    private TextureRegion jackImage;
    private TextureRegion redLightImage;
    private TextureRegion greenLightImage;
    private TextureRegion yellowLightImage;
    private TextureRegion offLightImage;
    private TextureRegion pixel;

    private BitmapFont font;
    private float fontWidth;

    public Jack(int x, int y, int callingId, JackListener jackListener) {
        this.x = x;
        this.y = y;
        this.id = String.valueOf(callingId);
        this.jackListener = jackListener;

        jackImage = Content.getAtlas("main").findRegion("jack");
        redLightImage = Content.getAtlas("main").findRegion("jack_light_red");
        greenLightImage = Content.getAtlas("main").findRegion("jack_light_green");
        yellowLightImage = Content.getAtlas("main").findRegion("jack_light_yellow");
        offLightImage = Content.getAtlas("main").findRegion("jack_light_off");
        pixel = Content.getAtlas("main").findRegion("pixel");

        width = jackImage.getRegionWidth();
        height = jackImage.getRegionHeight();

        font = Content.getFont("mainFont");
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, String.valueOf(this.id));
        fontWidth = glyph.width;
    }

    public boolean isAvailable() {
        return !lit && callingJack == null;
    }

    public String getId() {
        return id;
    }

    public void talk() {
        if (caller == null) {
            return;
        }
        talkedTo = true;
        jackListener.onTalk(caller);
        ringing = false;
        linked = false;
        talkingTo = true;
    }

    public void link() {
        if (ringing) {
            ringing = false;
            if (caller == null) {
                return;
            }
        }
        if (caller == null && !pickedUp) {
            return;
        }
        if ((callingJack == null || ringing) && caller == null) {
            return;
        }
        if (caller != null && !talkedTo) {
            return;
        }
        linked = true;
        if (callingJack.isLinked()) {
            setTalkTimer((float) (Math.random() * 15 + 5));
            callingJack.setTalkTimer(talkTimer);
            setTalking(true);
            callingJack.setTalking(true);
        }
    }

    public void ring() {
        linked = false;
        if (pickedUp) {
            return;
        }
        if (callingJack != null && caller == null) {
            ringing = true;
            pickupTimer = (float) (Math.random() * Vars.PICKUP_RAND + Vars.PICKUP_MIN_TIME);
        }
    }

    public void setCaller(Person caller) {
        if (caller == null) {
            lit = false;
            callingJack.setCallingJack(null);
        } else {
            lit = true;
            talkedTo = false;
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

    private void clear() {
        System.out.println("cleared");
        talkedTo = false;
        talkingTo = false;
        lit = false;
        caller = null;
        callingJack = null;
        pickupTime = 0;
        talkTime = 0;
        ringing = false;
        pickedUp = false;
        linked = false;
        talking = false;
    }

    public void update(float dt) {
        baseTime += dt;

        if (caller != null && !talkedTo) {
            callOperatorTime += dt;
            if (callOperatorTime > Vars.CALLING_OPERATOR_TIME) {
                clear();
            }
        }

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
                System.out.println("finished");
                if (caller != null) {
                    caller.remove();
                }
                caller = null;
                clear();
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(jackImage, x - width / 2, y - height / 2);
        if (talking) {
            sb.draw(greenLightImage, x - greenLightImage.getRegionWidth() / 2, y + 12);
        } else if (caller != null && !talkedTo) {
            if (baseTime % 0.1f < 0.05f) {
                sb.draw(greenLightImage, x - greenLightImage.getRegionWidth() / 2, y + 12);
            } else {
                sb.draw(offLightImage, x - offLightImage.getRegionWidth() / 2, y + 12);
            }
        } else if (linked && !talking) {
            sb.draw(yellowLightImage, x - yellowLightImage.getRegionWidth() / 2, y + 12);
        } else if ((lit && talkingTo) || (caller == null && pickedUp)) {
            if (baseTime % 0.6f < 0.3f) {
                sb.draw(yellowLightImage, x - yellowLightImage.getRegionWidth() / 2, y + 12);
            } else {
                sb.draw(offLightImage, x - offLightImage.getRegionWidth() / 2, y + 12);
            }
        } else if (ringing) {
            if (pickupTime % 0.1f < 0.05f) {
                sb.draw(redLightImage, x - redLightImage.getRegionWidth() / 2, y + 12);
            } else {
                sb.draw(offLightImage, x - offLightImage.getRegionWidth() / 2, y + 12);
            }
        } else {
            sb.draw(offLightImage, x - redLightImage.getRegionWidth() / 2, y + 12);
        }
        if (ringing) {
            sb.setColor(Color.GREEN);
            sb.draw(pixel, x - width / 2, y + 12, width * (pickupTimer - pickupTime) / Vars.PICKUP_MAX_TIME, 2);
        }
        if (talking) {
            sb.setColor(Color.GREEN);
            sb.draw(pixel, x - width / 2, y + 12, width * (talkTimer - talkTime) / Vars.CALL_MAX_TIME, 2);
        }
        if (caller != null && !talkedTo) {
            sb.setColor(Color.GREEN);
            sb.draw(pixel, x - width / 2, y + 12, width * (Vars.CALLING_OPERATOR_TIME - callOperatorTime) / Vars.CALLING_OPERATOR_TIME, 2);
        }
        font.setColor(Color.WHITE);
        font.draw(sb, id, x - fontWidth / 2, y - height / 2 - 2);
    }

}
