package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.sprites.Decoration;
import com.thevnkid93.game.sprites.Ground;

public class DecorationManager extends SpriteManager{

    public static final int DECORATION_SCROLL_SPEED = Ground.SCROLLING_SPEED;
    private static final int DECORATION_COUNT = 8;
    private int frameWidth;
    private Array<TextureRegion> textures;
    private Texture texture;
    private Decoration decorations[];

    public DecorationManager(int screenWidth, int groundHeight, int count){
        super(screenWidth);
        textures = new Array<TextureRegion>();
        texture = new Texture(ImgCons.GROUND_DECORATIONS);
        frameWidth = texture.getWidth()/DECORATION_COUNT;
        for (int i = 0; i < DECORATION_COUNT; i++) {
            textures.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }
        decorations = new Decoration[count];
        for (int i = 0; i < count; i++) {
            decorations[i] = new Decoration(0, groundHeight - 15, getRandomTexture());
            reposition(i);
        }
    }

    @Override
    public void dispose(){
        texture.dispose();
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < decorations.length; i++) {
            decorations[i].update(dt);
            if(decorations[i].getPosition().x < -frameWidth){
                changeDecoration(i);
                reposition(i);
            }
        }
    }

    @Override
    public void draw(SpriteBatch sb){
        for (int i = 0; i < decorations.length; i++) {
            decorations[i].draw(sb);
        }
    }

    private TextureRegion getRandomTexture(){
        return textures.get((int) (Math.random()*DECORATION_COUNT));
    }

    private void changeDecoration(int index){
        decorations[index].setFrame(getRandomTexture());
    }

    private void reposition(int index){
        decorations[index].getPosition().x = (float)((Math.random()*4*screenWidth) + screenWidth*2);
    }
}
