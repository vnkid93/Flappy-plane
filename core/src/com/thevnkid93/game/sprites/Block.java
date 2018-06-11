package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.thevnkid93.game.managers.BlockManager;
import com.thevnkid93.game.managers.GroundManager;

/**
 * Sprite of single box
 */
public class Block extends Sprite {

    private Rectangle bound;

    /**
     * @see Sprite#Sprite(float, float, int, int, TextureRegion)
     */
    public Block(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height, frame);
        bound = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float dt) {
        position.x -= BlockManager.SCROLLING_SPEED * dt;
        bound.setPosition(position);
    }

    public Rectangle getBound() {
        return bound;
    }

}
