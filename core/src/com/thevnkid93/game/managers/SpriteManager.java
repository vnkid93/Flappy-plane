package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public abstract class SpriteManager {


    public abstract void update(float dt);
    public abstract void draw(SpriteBatch sb);
    public abstract void dispose();
}
