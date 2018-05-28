package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block extends Sprite {

    public static final int SCROLLING_SPEED = Ground.SCROLLING_SPEED;
    public static final int BLOCK_WIDTH = Plane.PLANE_WIDTH * 3 / 2; // 1.5x
    public static final int BLOCK_HEIGHT = BLOCK_WIDTH;

    private Texture texture;

    public Block(float x, float y, Texture texture) {
        super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.texture = texture;
    }

    @Override
    public void update(float dt) {
        position.x -= SCROLLING_SPEED * dt;
    }

    @Override
    public void dispose(){
        texture.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y, width, height);
    }

    @Override
    public String toString() {
        return position.x + ", "+ position.y;
    }
}
