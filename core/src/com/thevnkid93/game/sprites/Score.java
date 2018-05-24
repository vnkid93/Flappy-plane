package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;

public class Score extends Sprite {

    private static final int FRAME_WIDTH = 50;
    private static final int FRAME_HEIGHT = 64;
    private static final int CIPHER_WIDTH = Plane.PLANE_WIDTH/4;
    private static final int CIPHER_HEIGHT= CIPHER_WIDTH * FRAME_WIDTH / FRAME_HEIGHT;


    private int score;
    private Texture texture;
    private Array<TextureRegion> frames;

    public Score(float x, float y) {
        super(x, y);
        texture = new Texture(ImgCons.NUMBERS);
        TextureRegion textureRegion = new TextureRegion(texture);
        final int cipherCount = 10;
        frames = new Array<TextureRegion>();
        for (int i = 0; i < cipherCount; i++) {
            frames.add(new TextureRegion(textureRegion, i*FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT));
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        String scoreStr = score+"";
        int startingX = MyGame.WIDTH/2 - (scoreStr.length()*CIPHER_WIDTH / 2);
        for (int i = 0; i < scoreStr.length(); i++) {
            int cipher = scoreStr.charAt(i);
            sb.draw(frames.get(cipher), startingX, 0, CIPHER_WIDTH, CIPHER_HEIGHT);
        }
    }

    public void increase(){
        score++;
    }

    public void resetScore(){
        score = 0;
    }
}

