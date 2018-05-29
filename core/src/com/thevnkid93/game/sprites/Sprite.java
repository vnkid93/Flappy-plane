package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Sprite {

    protected Vector2 position;
    protected int width, height; // the drawing size, not the true texture size

    public Sprite(float x, float y, int width, int height){
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void update(float dt);

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void dispose();
    public abstract void draw(SpriteBatch sb);
}
