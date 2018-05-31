package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BasicSprite extends Sprite {

    private TextureRegion frame;

    public BasicSprite(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        // nothing to do here
    }

    @Override
    public void dispose() {
        // nothing here either
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }

    public void setFrame(TextureRegion frame) {
        this.frame = frame;
    }
}
