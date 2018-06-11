package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Ground;

/**
 * Ground manager class for drawing and scrolling grounds
 */
public class GroundManager extends SpriteManager{
    public static final int SCROLLING_SPEED = 8*BackgroundManager.BACKGROUND_SCROLLING_SPEED;
    private static final int GROUND_WIDTH = MyGame.HEIGHT/9;
    public static final int GROUND_HEIGHT = GROUND_WIDTH;
    private static final int GROUND_COUNT = (int)Math.ceil((float)MyGame.WIDTH/GROUND_WIDTH) + 1;
    private static final int FRAME_COUNT = 2;

    private Texture texture;
    private Ground[] topGrounds, bottomGrounds;
    private int firstWave;

    /**
     * Constructor of groundmanager
     */
    public GroundManager(){
        texture = new Texture(ImgCons.GROUNDS);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int frameWidth = texture.getWidth()/FRAME_COUNT;
        int frameHeight = texture.getHeight();

        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, frameHeight));
        }
        firstWave = 0;
        topGrounds = new Ground[GROUND_COUNT];
        bottomGrounds = new Ground[GROUND_COUNT];
        for (int i = 0; i < GROUND_COUNT; i++) {
            topGrounds[i] = new Ground(i*GROUND_WIDTH, GROUND_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT, frames.get(0));
            bottomGrounds[i] = new Ground(i*GROUND_WIDTH, 0, GROUND_WIDTH, GROUND_HEIGHT, frames.get(1));
        }
    }

    /**
     * Repositioning ground blocks to make it continuously scroll
     */
    private void reposition(){
        int lastIndex = firstWave-1 < 0 ? GROUND_COUNT-1 : firstWave-1;
        topGrounds[firstWave].getPosition().x = topGrounds[lastIndex].getPosition().x + GROUND_WIDTH;
        bottomGrounds[firstWave].getPosition().x = bottomGrounds[lastIndex].getPosition().x + GROUND_WIDTH;
        firstWave = (firstWave+1) % GROUND_COUNT;
    }

    /**
     * Scrolling ground sprites
     * @param dt the changing time
     */
    @Override
    public void update(float dt) {
        for (int i = 0; i < GROUND_COUNT; i++) {
            topGrounds[i].update(dt);
            bottomGrounds[i].update(dt);
        }
        if(topGrounds[firstWave].getPosition().x < -GROUND_WIDTH){
            reposition();
        }
    }

    /**
     * Drawing ground blocks
     * @param sb drawing instance
     */
    @Override
    public void draw(SpriteBatch sb) {
        for (int i = 0; i < GROUND_COUNT; i++) {
            topGrounds[i].draw(sb);
            bottomGrounds[i].draw(sb);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}
