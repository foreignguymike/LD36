package com.distraction.ld36.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld36.Content;

public class Jack extends GameObject {

    private Person person;
    private int id;

    private TextureRegion image;

    private boolean lit;
    private boolean taken;
    private Cord cord;

    private BitmapFont font;

    public Jack(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;

        image = new TextureRegion(Content.getTexture("test"));
        width = image.getRegionWidth();
        height = image.getRegionHeight();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isAvailable() {
        return !lit && !taken;
    }

    public void setPerson(Person person) {
        lit = true;
        this.person = person;
    }

    public void setCord(Cord cord) {
        this.cord = cord;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.BLACK);
        sb.draw(image, x - width / 2, y - height / 2);
        if(lit) {
            sb.setColor(Color.GREEN);
        }
        sb.draw(image, x - width / 2 + 5, y - height / 2 + 20);
        sb.setColor(Color.BLACK);
        font.draw(sb, "" + id, x - width / 2 - 5, y - height / 2 + 30);
    }

}
