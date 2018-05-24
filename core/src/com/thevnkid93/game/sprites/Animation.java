package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private int[][] positions;

    public Animation(TextureRegion region, int frameWidth, int frameHeight, float cycleTime, int[][] positions){
        frames = new Array<TextureRegion>();
        this.frameCount = positions.length;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, positions[i][0]*frameWidth, positions[i][1]*frameHeight, frameWidth, frameHeight));
        }
        this.positions = positions;
        maxFrameTime = cycleTime/frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame = (frame+1) % frameCount;
            currentFrameTime = 0;
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
