package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Score extends Sprite {

    private int score;
    private Array<TextureRegion> frames;

    public Score(float x, float y, int width, int height, Array<TextureRegion> frames) {
        super(x, y, width, height);
        this.frames = frames;
        this.score = 0;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frames.get(score), position.x, position.y, width, height);
    }

    public void setScore(int score){
        this.score = score;
    }

}

