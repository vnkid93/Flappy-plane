package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.BasicSprite;

public class TitleManager extends SpriteManager {

    public enum Title{
        GET_READY, GAME_OVER, NOTHING;
    }
    private static final int TITLE_COUNT = 2;
    private Texture texture;
    private Array<TextureRegion> frames;
    private Title title;
    private BasicSprite sprite;
    private int drawingWidth, drawingHeight, frameWidth, frameHeight;

    public TitleManager(){
        texture = new Texture(ImgCons.NAVIGATION_TITLES);
        frames = new Array<TextureRegion>();
        frameWidth = texture.getWidth()/TITLE_COUNT;
        frameHeight = texture.getHeight();

        for (int i = 0; i < TITLE_COUNT; i++) {
            frames.add(new TextureRegion(texture,  i * frameWidth, 0, frameWidth, frameHeight));
        }

        drawingWidth = (int) (MyGame.WIDTH * 0.8);
        drawingHeight = (int) (frameHeight * drawingWidth / frameWidth);

        sprite = new BasicSprite(MyGame.WIDTH/2 - drawingWidth/2, MyGame.HEIGHT * 5 / 6, drawingWidth, drawingHeight, frames.get(0));
        title = Title.GET_READY;
    }
    @Override
    public void update(float dt) {
        // nothing
    }

    @Override
    public void draw(SpriteBatch sb) {
        if(title == Title.GET_READY){
            sprite.setFrame(frames.get(0));
        }else {
            sprite.setFrame(frames.get(1));
        }
        sprite.draw(sb);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
