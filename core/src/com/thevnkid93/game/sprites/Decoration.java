package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thevnkid93.game.managers.DecorationManager;


public class Decoration extends Sprite {

    private TextureRegion frame;

    public Decoration(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        position.x -= DecorationManager.DECORATION_SCROLL_SPEED * dt;
    }

    @Override
    public void dispose() {
        // no need. Texture disposed in its manager
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }

    public void setFrame(TextureRegion frame){
        this.frame = frame;
    }
}
