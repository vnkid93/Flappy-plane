package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Sprite {

    protected Vector2 position;

    public Sprite(float x, float y){
        position = new Vector2(x, y);

    }

    public abstract void update(float dt);

    public Vector2 getPosition() {
        return position;
    }




    public abstract void dispose();
    public abstract void draw(SpriteBatch sb);
}
