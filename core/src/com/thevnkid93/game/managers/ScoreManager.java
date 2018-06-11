package com.thevnkid93.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.SoundCons;
import com.thevnkid93.game.sprites.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager class for score drawing
 */
public class ScoreManager extends SpriteManager {
    private static final int FRAME_COUNT = 10;
    private List<Score> scores;
    private Texture texture;
    private Array<TextureRegion> frames;
    private int drawingWidth, drawingHeight;
    private int score;
    private Sound coinSound;

    /**
     * Constructor of the score manager class
     * @param cipherWidth the drawing width of a single cipher
     */
    public ScoreManager(int cipherWidth){
        coinSound = Gdx.audio.newSound(Gdx.files.internal(SoundCons.COIN));
        texture = new Texture(ImgCons.NUMBERS);
        int frameWidth = texture.getWidth()/FRAME_COUNT;
        int frameHeight = texture.getHeight();
        drawingWidth = cipherWidth;
        drawingHeight = frameHeight * drawingWidth/frameWidth;
        frames = new Array<TextureRegion>();
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames.add(new TextureRegion(texture, i*frameWidth, 0, frameWidth, frameHeight));
        }
        resetScore();
    }

    /**
     * Updating score and the cipher positions
     * @param dt the changing time
     */
    @Override
    public void update(float dt) {
        String scoreStr = score+"";
        if(scoreStr.length() > scores.size()){
            // expand new cipher
            scores.add(new Score(0, MyGame.HEIGHT * 5/6, drawingWidth, drawingHeight, frames));
        }
        int startingX = MyGame.WIDTH/2 - (scores.size()*drawingWidth)/2;
        for (int i = 0; i < scores.size(); i++) {
            scores.get(i).setScore(scoreStr.charAt(i)-'0');
            scores.get(i).getPosition().x = startingX + i*drawingWidth;
        }
    }

    /**
     * Changing scores
     */
    public void increase(){
        score++;
        coinSound.play(0.2f);
    }

    /**
     * Reset score.
     */
    private void resetScore(){
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
        coinSound.dispose();
    }

    public int getScore() {
        return score;
    }

}
