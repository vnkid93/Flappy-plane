package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.BasicSprite;

/**
 * The manager for frawing Get Ready and Game Over title
 */
public class TitleManager extends SpriteManager {

    // Title status
    public enum Title{
        GET_READY, GAME_OVER, NOTHING;
    }
    private static final int TITLE_COUNT = 2; // frame count
    private Texture texture;
    private Array<TextureRegion> frames; // frame list
    private Title title;
    private BasicSprite sprite;

    public TitleManager(){
        texture = new Texture(ImgCons.NAVIGATION_TITLES);
        frames = new Array<TextureRegion>();
        int frameWidth = texture.getWidth()/TITLE_COUNT;
        int frameHeight = texture.getHeight();

        for (int i = 0; i < TITLE_COUNT; i++) {
            frames.add(new TextureRegion(texture,  i * frameWidth, 0, frameWidth, frameHeight));
        }

        int drawingWidth = (int) (MyGame.WIDTH * 0.8);
        int drawingHeight = (int) (frameHeight * drawingWidth / frameWidth);

        sprite = new BasicSprite(MyGame.WIDTH/2 - drawingWidth/2, MyGame.HEIGHT * 5 / 6, drawingWidth, drawingHeight, frames.get(0));
        title = Title.GET_READY;
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
