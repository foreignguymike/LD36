package com.distraction.ld36.game;

public abstract class GameObject {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public float getx() {
        return x;
    }

    public float gety() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean contains(float x, float y) {
        return x > this.x - width / 2 &&
                x < this.x + width / 2 &&
                y > this.y - height / 2 &&
                y < this.y + height / 2;
    }

}
