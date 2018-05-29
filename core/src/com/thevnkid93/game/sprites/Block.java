package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.thevnkid93.game.managers.GroundManager;

public class Block extends Sprite {

    public static final int SCROLLING_SPEED = GroundManager.SCROLLING_SPEED;
    public static final int BLOCK_WIDTH = Plane.PLANE_WIDTH * 3 / 2; // 1.5x
    public static final int BLOCK_HEIGHT = BLOCK_WIDTH;

    private Texture texture;
    private Rectangle bound;

    public Block(float x, float y, Texture texture) {
        super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
        this.texture = texture;

        bound = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(float dt) {
        position.x -= SCROLLING_SPEED * dt;
        bound.setPosition(position);
    }

    @Override
    public void dispose(){
        texture.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(texture, position.x, position.y, width, height);
    }


    public Rectangle getBound() {
        return bound;
    }

    @Override
    public String toString() {
        return position.x + ", "+ position.y;
    }
}
