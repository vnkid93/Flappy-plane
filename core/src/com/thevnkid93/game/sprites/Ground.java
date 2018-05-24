package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;

public class Ground extends Sprite {

    public static final int SCROLLING_SPEED = 100;
    private Texture groundTop, groundBottom;
    public static final int GROUND_WIDTH = MyGame.HEIGHT/9;
    public static final int GROUND_HEIGHT = GROUND_WIDTH;
    private static final int FRAME_COUNT = (int)Math.ceil((float)MyGame.WIDTH/GROUND_WIDTH) + 1;


    public Ground() {
        super(0, 0);
        groundTop = new Texture(ImgCons.GROUND_TOP);
        groundBottom = new Texture(ImgCons.GROUND_BOTTOM);
    }

    @Override
    public void update(float dt) {
        // scrolling
        position.x -= SCROLLING_SPEED * dt;
        if(position.x < -GROUND_WIDTH){
            position.x = 0;
        }
    }

    @Override
    public void dispose() {
        groundTop.dispose();
        groundBottom.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        //drawing top grounds
        for (int i = 0; i < FRAME_COUNT; i++) {
            sb.draw(groundTop, i*GROUND_WIDTH + position.x, GROUND_HEIGHT, GROUND_WIDTH, GROUND_HEIGHT);
            sb.draw(groundBottom, i*GROUND_WIDTH + position.x, 0, GROUND_WIDTH, GROUND_HEIGHT);
        }
    }
}
