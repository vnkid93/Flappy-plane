package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StatusTitle extends Sprite {

    private TextureRegion frame;

    public StatusTitle(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        //nothing
    }

    @Override
    public void dispose() {
        //nothing
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }
}
