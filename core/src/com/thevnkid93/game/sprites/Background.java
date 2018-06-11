package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.thevnkid93.game.managers.BackgroundManager;

/**
 * Class for background
 */
public class Background extends Sprite {

    /**
     * @see Sprite#Sprite(float, float, int, int, TextureRegion)
     */
    public Background(float x, int width, int height, TextureRegion frame) {
        super(x, 0, width, height, frame);
    }

    @Override
    public void update(float dt) {
        position.x -= BackgroundManager.BACKGROUND_SCROLLING_SPEED * dt;
    }

    public Rectangle[] getBounds() {
        return new Rectangle[0];
    }
}
