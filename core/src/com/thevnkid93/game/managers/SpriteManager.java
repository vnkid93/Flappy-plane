package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The sprite manager for managing and drawing sprite objects in package - sprites
 */
public abstract class SpriteManager {

    /**
     * For updating before calling draw method
     * @param dt the changing time
     */
    public void update(float dt){
        // default = nothing
    }

    /**
     * For drawing sprites
     * @param sb drawing instance
     */
    public abstract void draw(SpriteBatch sb);

    /**
     * For deallocating resources
     */
    public abstract void dispose();
}
