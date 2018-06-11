package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * The sprite object for undefined/generic object
 */
public class BasicSprite extends Sprite {

    /**
     * @see Sprite#Sprite(float, float, int, int, TextureRegion)
     */
    public BasicSprite(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height, frame);
    }

    /**
     * Method for collision testing.
     * @param x the position of the testing point
     * @param y the posision of the testing point
     * @return true if there is any collision, otherwise false.
     */
    public boolean collide(float x, float y){
        Rectangle r = new Rectangle(position.x, position.y, width, height);
        return r.contains(x, y);
    }
}
