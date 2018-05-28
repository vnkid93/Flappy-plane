package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Background;


public class BackgroundManager extends SpriteManager {

    public static final int BACKGROUND_SCROLLING_SPEED = 20;
    private static final int FRAME_COUNT = 4;
    private Background backgrounds[];
    private Texture texture;
    private Array<TextureRegion> frames;
    private int frameWidth, frameHeight;
    private int drawingWidth, drawingHeight;
    private int firstBackgroundIndex;


    public BackgroundManager(){
        firstBackgroundIndex = 0;
        texture = new Texture(ImgCons.BACKGROUNDS);
        // true image size
        frameWidth = texture.getWidth()/FRAME_COUNT;
        frameHeight = texture.getHeight();
        // drawing size
        drawingHeight = MyGame.HEIGHT;
        drawingWidth = frameWidth * drawingHeight / frameHeight;


        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }
        int count = (int)Math.ceil((float)MyGame.WIDTH /frameWidth + 1);
        backgrounds = new Background[count];
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i] = new Background(i*drawingWidth, drawingWidth, drawingHeight, frames.get(0));
        }
        randBackground();
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].update(dt);
        }

        if(backgrounds[firstBackgroundIndex].getPosition().x < -drawingWidth){
            reposition();
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].draw(sb);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    private void reposition(){
        int lastBackgroundIndex = firstBackgroundIndex-1;
        if(firstBackgroundIndex == 0){
            lastBackgroundIndex = backgrounds.length-1;
        }
        backgrounds[firstBackgroundIndex].getPosition().x = backgrounds[lastBackgroundIndex].getPosition().x + drawingWidth;
        firstBackgroundIndex = (firstBackgroundIndex+1) % backgrounds.length;
    }

    private void randBackground(){
        int index = (int)(Math.random()*FRAME_COUNT);
        TextureRegion newBackground = frames.get(index);
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].setFrame(newBackground);
        }
    }
}
