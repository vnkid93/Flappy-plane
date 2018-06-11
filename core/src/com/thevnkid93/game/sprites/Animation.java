package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Class for sprite animation based on spritesheet texture
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; // the appear time for single frame
    private float currentFrameTime; // the sum of changing time
    private int frameCount;
    private int frame; // the frame index

    /**
     * The constructor of the animation
     * @param region The whole spritesheet
     * @param frameWidth width of a single sprite
     * @param frameHeight height of single sprite
     * @param cycleTime the cycle time for the animation (loop time)
     * @param positions the coordinations of those frames
     */
    public Animation(TextureRegion region, int frameWidth, int frameHeight, float cycleTime, int[][] positions){
        frames = new Array<TextureRegion>();
        this.frameCount = positions.length;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, positions[i][0]*frameWidth, positions[i][1]*frameHeight, frameWidth, frameHeight));
        }
        maxFrameTime = cycleTime/frameCount;
        frame = 0;
    }

    /**
     * Updating frame
     * @param dt delta time (changing time)
     */
    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame = (frame+1) % frameCount;
            currentFrameTime = 0;
        }
    }

    /**
     * get frame via frame index
     * @return TextureRegion - the correct frame
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public int getFrameIndex(){
        return frame;
    }
}
