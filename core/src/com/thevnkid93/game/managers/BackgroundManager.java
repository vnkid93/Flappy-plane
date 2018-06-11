package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Background;

/**
 * The background manager class for drawing and scrolling background
 */
public class BackgroundManager extends SpriteManager {

    public static final int BACKGROUND_SCROLLING_SPEED = 20;
    private static final int FRAME_COUNT = 4;
    private Background backgrounds[];
    private Texture texture;
    private Array<TextureRegion> frames;
    private int drawingWidth, drawingHeight;
    private int firstBackgroundIndex;


    public BackgroundManager(boolean random){
        firstBackgroundIndex = 0;
        texture = new Texture(ImgCons.BACKGROUNDS);
        // true image size
        int frameWidth = texture.getWidth()/FRAME_COUNT;
        int frameHeight = texture.getHeight();
        // drawing size
        drawingHeight = MyGame.HEIGHT;
        drawingWidth = frameWidth * drawingHeight / frameHeight;


        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, frameHeight));
        }
        int count = (int)Math.ceil((float)MyGame.WIDTH /frameWidth + 1);
        backgrounds = new Background[count];
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i] = new Background(i*drawingWidth, drawingWidth, drawingHeight, frames.get(0));
        }
        if(random){
            randBackground();
        }else {
            TextureRegion newBackground = frames.get(2);
            for (Background background : backgrounds) {
                background.setFrame(newBackground);
            }
        }
    }

    /**
     * Scrolling backgrounds before they are drawn
     * @param dt the changing time
     */
    @Override
    public void update(float dt) {
        for (Background background : backgrounds) {
            background.update(dt);
        }

        if(backgrounds[firstBackgroundIndex].getPosition().x < -drawingWidth){
            reposition();
        }
    }

    /**
     * Drawing backgrounds
     * @param sb drawing instance
     */
    @Override
    public void draw(SpriteBatch sb) {
        for (Background background : backgrounds) {
            background.draw(sb);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    /**
     * Repositioning background so that it will scroll forever
     */
    private void reposition(){
        int lastBackgroundIndex = firstBackgroundIndex-1;
        if(firstBackgroundIndex == 0){
            lastBackgroundIndex = backgrounds.length-1;
        }
        backgrounds[firstBackgroundIndex].getPosition().x = backgrounds[lastBackgroundIndex].getPosition().x + drawingWidth;
        firstBackgroundIndex = (firstBackgroundIndex+1) % backgrounds.length;
    }

    /**
     * Choosing random background image/texture
     */
    private void randBackground(){
        int index = (int)(Math.random()*FRAME_COUNT);
        TextureRegion newBackground = frames.get(index);
        for (Background background : backgrounds) {
            background.setFrame(newBackground);
        }
    }
}
