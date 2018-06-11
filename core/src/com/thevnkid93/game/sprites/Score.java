package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Score extends Sprite {

    private int score;
    private Array<TextureRegion> frames;

    /**
     * Constructor of Score sprite object
     * @param x position of the ground sprite object
     * @param y position of the ground sprite object
     * @param width the drawing width of the sprite object
     * @param height the height width of the sprite object
     * @param frames the textures of the sprite to draw (numbers)
     */
    public Score(float x, float y, int width, int height, Array<TextureRegion> frames) {
        super(x, y, width, height);
        this.frames = frames;
        this.score = 0;
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frames.get(score), position.x, position.y, width, height);
    }

    public void setScore(int score){
        this.score = score;
    }

}

