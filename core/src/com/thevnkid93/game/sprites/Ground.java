package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.managers.BackgroundManager;
import com.thevnkid93.game.managers.GroundManager;

public class Ground extends Sprite {

    private TextureRegion frame;

    public Ground(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        this.frame = frame;
    }

    @Override
    public void update(float dt) {
        // scrolling
        position.x -= GroundManager.SCROLLING_SPEED * dt;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }
}
