package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.sprites.Background;


public class BackgroundManager extends SpriteManager {

    public static final int BACKGROUND_SCROLLING_SPEED = 20;
    private static final int FRAME_COUNT = 3;
    private Background backgrounds[];
    private Texture texture;
    private Array<TextureRegion> frames;
    private int frameWidth;
    private int firstBackgroundIndex;

    public BackgroundManager(int screenWidth){
        super(screenWidth);
        firstBackgroundIndex = 0;
        texture = new Texture(ImgCons.BACKGROUNDS);
        frameWidth = texture.getWidth();
        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }
        int count = (int)Math.ceil((float)screenWidth/frameWidth + 1);
        backgrounds = new Background[count];
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i] = new Background(i*frameWidth, frames.get(0));
        }
        randBackground();
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].update(dt);
        }

        if(backgrounds[firstBackgroundIndex].getPosition().x < -frameWidth){
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
        backgrounds[firstBackgroundIndex].getPosition().x = frameWidth * (backgrounds.length-1);
        firstBackgroundIndex = (firstBackgroundIndex+1) % backgrounds.length;
    }

    private void randBackground(){
        TextureRegion newBackground = frames.get((int)(Math.random()*backgrounds.length));
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].setFrame(newBackground);
        }
    }
}
