package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Animation;
import com.thevnkid93.game.sprites.Plane;
import com.thevnkid93.game.sprites.Tip;

public class TipsManager extends SpriteManager{
    private static final int FRAME_COUNT = 4;
    private static final float ANIMATION_CYCLE_TIME = 1.f;
    private static final int ANIMATION_FRAMES[][] = {{0, 0},{1, 0}};
    private Texture texture;
    private Array<TextureRegion> frames;
    private Tip leftTip, rightTip, clickingTip;
    private int frameWidth, frameHeight, drawingWidth, drawingHeight;
    private boolean visible;
    private Animation animation;

    public TipsManager(){
        texture = new Texture(ImgCons.NAVIGATION_TIPS);
        frameWidth = texture.getWidth()/FRAME_COUNT;
        frameHeight = texture.getHeight();
        drawingWidth = (int)(Plane.PLANE_WIDTH * .8);
        drawingHeight = frameHeight * drawingWidth/frameWidth;


        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i*frameWidth, 0, frameWidth, frameHeight));
        }

        int padding = drawingWidth/2;



        leftTip = new Tip(MyGame.WIDTH/2 - padding - drawingWidth, MyGame.HEIGHT/3, drawingWidth, drawingHeight, frames.get(0));
        rightTip = new Tip(MyGame.WIDTH/2 + padding, MyGame.HEIGHT/3, drawingWidth, drawingHeight, frames.get(1));
        clickingTip = new Tip(MyGame.WIDTH/2, MyGame.HEIGHT/3 - padding - drawingHeight, drawingWidth, drawingHeight, frames.get(2));

        animation = new Animation(frames.get(2), frameWidth, frameHeight, ANIMATION_CYCLE_TIME, ANIMATION_FRAMES);
        visible = true;
    }


    @Override
    public void update(float dt) {
        animation.update(dt);
    }

    @Override
    public void draw(SpriteBatch sb) {
        clickingTip.setFrame(animation.getFrame(), animation.getFrameIndex());

        leftTip.draw(sb);
        rightTip.draw(sb);
        clickingTip.draw(sb);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void setVisible(boolean visible){
        this.visible = visible;
    }
}
