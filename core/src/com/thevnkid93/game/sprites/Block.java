package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block extends Sprite {

    public static final int BLOCK_WIDTH = 2 * Plane.PLANE_WIDTH;
    public static final int BLOCK_HEIGHT = BLOCK_WIDTH;

    private Texture blockTx;

    public Block(float x) {
        super(x, 0, 0, 0);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        blockTx.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {

    }
}
