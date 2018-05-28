package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thevnkid93.game.managers.BackgroundManager;

public class Background extends Sprite {
    private TextureRegion frame;

    public Background(float x, int width, int height, TextureRegion frame) {
        super(x, 0, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        position.x -= BackgroundManager.BACKGROUND_SCROLLING_SPEED * dt;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }

    public void setFrame(TextureRegion frame){
        this.frame = frame;
    }
}
