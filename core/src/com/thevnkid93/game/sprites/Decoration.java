package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thevnkid93.game.managers.DecorationManager;


public class Decoration extends Sprite {

    /**
     * @see Sprite#Sprite(float, float, int, int, TextureRegion)
     */
    public Decoration(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        position.x -= DecorationManager.DECORATION_SCROLL_SPEED * dt; // scrolling same as ground
    }

}
