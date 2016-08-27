package com.distraction.ld36.game;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(float x, float y) {
        return x > this.x - width / 2 &&
                x < this.x + width / 2 &&
                y > this.y - height / 2 &&
                y < this.y + height / 2;
    }

}
