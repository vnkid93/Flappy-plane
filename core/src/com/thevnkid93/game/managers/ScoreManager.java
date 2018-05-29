package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Plane;
import com.thevnkid93.game.sprites.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager extends SpriteManager {
    private static final int FRAME_COUNT = 10;
    private List<Score> scores;
    private Texture texture;
    private Array<TextureRegion> frames;
    private int frameWidth, frameHeight, drawingWidth, drawingHeight;
    private int score;

    public ScoreManager(){
        texture = new Texture(ImgCons.NUMBERS);
        frameWidth = texture.getWidth()/FRAME_COUNT;
        frameHeight = texture.getHeight();
        drawingWidth = Plane.PLANE_WIDTH/3;
        drawingHeight = frameHeight * drawingWidth/frameWidth;
        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i*frameWidth, 0, frameWidth, frameHeight));
        }
        scores = new ArrayList<Score>();
        scores.add(new Score(MyGame.WIDTH/2 - drawingWidth/2, MyGame.HEIGHT * 5 / 6, drawingWidth, drawingHeight, frames));

    }

    @Override
    public void update(float dt) {
        // nothing
    }

    public void increase(){
        score++;
        String scoreStr = score+"";
        if(scoreStr.length() > scores.size()){
            // expand new cipher
            scores.add(new Score(0, MyGame.HEIGHT * 5/6, drawingWidth, drawingHeight, frames));
        }
        int startingX = MyGame.WIDTH/2 - (scores.size()*drawingWidth)/2;
        for (int i = 0; i < scores.size(); i++) {
            scores.get(i).setScore(scoreStr.charAt(i));
            scores.get(i).getPosition().x = startingX + i*drawingWidth;
        }
    }

    public void resetScore(){
        score = 0;
        scores = new ArrayList<Score>();
        scores.add(new Score(MyGame.WIDTH/2 - drawingWidth/2, MyGame.HEIGHT * 5 / 6, drawingWidth, drawingHeight, frames));
    }

    @Override
    public void draw(SpriteBatch sb) {
        for (Score sc :
                scores) {
            sc.draw(sb);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
