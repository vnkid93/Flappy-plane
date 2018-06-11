package com.thevnkid93.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.BasicSprite;
import com.thevnkid93.game.sprites.Score;

import java.util.ArrayList;
import java.util.List;

public class MenuBoardManager extends SpriteManager {
    public enum Button{
        PLAY_BTN, QUIT_BTN, NOTHING;
    }

    private static final int BTN_FRAME_COUNT = 6;
    private BasicSprite board, playBtn, quitBtn, scoreTitle;
    private Texture textureBtn, boardTexture, numberTexture, scoreTitleTexture;
    private Array<TextureRegion> btnFrames;
    private boolean appearing; // if the board will appear
    private Vector2 fallingVelocity;

    private Sound gameOverSound;
    private int btnPadding;

    private static final int CIPHER_COUNT = 10;
    private int cipherFrameWidth, cipherFrameHeight;
    private Array<TextureRegion> scoreFrames;
    private List<Score> scoreList;


    /**
     * Constructor for menu board manager class
     */
    public MenuBoardManager(){
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("game_over.mp3"));
        boardTexture = new Texture(ImgCons.BOARD);
        int boardWidth = MyGame.WIDTH * 3/4;
        board = new BasicSprite(MyGame.WIDTH/2 - boardWidth/2, MyGame.HEIGHT*3/2, boardWidth, boardWidth *2/3, new TextureRegion(boardTexture));

        scoreTitleTexture = new Texture(ImgCons.SCORE_TITLE);
        final int titleWidth = boardWidth*9/10;
        final int titleHeight = scoreTitleTexture.getHeight() * titleWidth / scoreTitleTexture.getWidth();
        scoreTitle = new BasicSprite(MyGame.WIDTH/2 - titleWidth/2, board.getPosition().y + board.getHeight() - titleHeight * 3/2, titleWidth, titleHeight, new TextureRegion(scoreTitleTexture));



        numberTexture = new Texture(ImgCons.NUMBERS);
        cipherFrameHeight = numberTexture.getHeight();
        cipherFrameWidth = numberTexture.getWidth()/CIPHER_COUNT;
        scoreFrames = new Array<TextureRegion>();
        for (int i = 0; i < CIPHER_COUNT; i++) {
            scoreFrames.add(new TextureRegion(numberTexture, i*cipherFrameWidth, 0, cipherFrameWidth, cipherFrameHeight));
        }

        initButtons();

        fallingVelocity = new Vector2();
        appearing = false;
    }

    /**
     * Init method for buttons
     */
    private void initButtons(){
        textureBtn = new Texture(ImgCons.MENU_BTNS);
        int btnFrameWidth = textureBtn.getWidth()/6;
        int btnFrameHeight = textureBtn.getHeight();
        int drawingBtnWidth = MyGame.WIDTH/2;
        int drawingBtnHeight = btnFrameHeight * drawingBtnWidth / btnFrameWidth;

        btnFrames = new Array<TextureRegion>();
        for (int i = 0; i < BTN_FRAME_COUNT; i++) {
            btnFrames.add(new TextureRegion(textureBtn, i*btnFrameWidth, 0, btnFrameWidth, btnFrameHeight));
        }

        btnPadding = drawingBtnHeight/4;
        playBtn = new BasicSprite(MyGame.WIDTH/2 - drawingBtnWidth/2, board.getPosition().y - drawingBtnHeight - btnPadding, drawingBtnWidth, drawingBtnHeight, btnFrames.get(0));
        quitBtn = new BasicSprite(MyGame.WIDTH/2 - drawingBtnWidth/2, playBtn.getPosition().y - drawingBtnHeight - btnPadding, drawingBtnWidth, drawingBtnHeight, btnFrames.get(4));

    }

    /**
     * Set score when the game is over
     * @param score the players score
     */
    public void setScore(int score) {
        gameOverSound.play(.1f);
        int drawingWidth = board.getWidth()/6;
        int drawingHeight = cipherFrameHeight * drawingWidth/cipherFrameWidth;
        String scoreStr = score+"";
        scoreList = new ArrayList<Score>();
        for (int i = 0; i < scoreStr.length(); i++) {
            scoreList.add(new Score(0, board.getPosition().y + board.getHeight()/2 - drawingHeight, drawingWidth, drawingHeight, scoreFrames));
        }
        int startingX = MyGame.WIDTH/2 - (scoreList.size()*drawingWidth)/2;
        for (int i = 0; i < scoreList.size(); i++) {
            scoreList.get(i).setScore(scoreStr.charAt(i)-'0');
            scoreList.get(i).getPosition().x = startingX + i*drawingWidth;
        }
    }

    /**
     * Updating menu falling animation.
     * @param dt the changing time
     */
    @Override
    public void update(float dt) {
        if(appearing){
            fallingVelocity.add(0, -20);
            fallingVelocity.scl(dt);
            board.getPosition().add(fallingVelocity);
            scoreTitle.getPosition().add(fallingVelocity);
            playBtn.getPosition().add(fallingVelocity);
            quitBtn.getPosition().add(fallingVelocity);
            for (Score s:
                 scoreList) {
                s.getPosition().add(fallingVelocity);
            }


            if(board.getPosition().y < MyGame.HEIGHT/2){
                board.getPosition().y = MyGame.HEIGHT/2;
                scoreTitle.getPosition().y = board.getPosition().y + board.getHeight() - scoreTitle.getHeight() * 3/2;
                playBtn.getPosition().y = board.getPosition().y - playBtn.getHeight() - btnPadding;
                quitBtn.getPosition().y = playBtn.getPosition().y - quitBtn.getHeight() - btnPadding;
                for (Score s:
                        scoreList) {
                    s.getPosition().y = board.getPosition().y + board.getHeight()/2 - s.getHeight();
                }
                appearing = false;
            }

            fallingVelocity.scl(1/dt);
        }
    }

    /**
     * Method that is called when the play button is touched
     */
    public void clickPlayBtn(){
        playBtn.setFrame(btnFrames.get(1));
    }

    /**
     * Method that is called when the hold play button was released
     */
    public void releasePlayBtn(){
        playBtn.setFrame(btnFrames.get(0));

    }

    /**
     * Method that is called when the quit button was clicked
     */
    public void clickQuitBtn(){
        quitBtn.setFrame(btnFrames.get(5));

    }

    /**
     * Method that is called when the hold quit button was released
     */
    public void releaseQuitBtn(){
        quitBtn.setFrame(btnFrames.get(4));
    }

    @Override
    public void draw(SpriteBatch sb) {
        playBtn.draw(sb);
        quitBtn.draw(sb);
        board.draw(sb);
        scoreTitle.draw(sb);
        for (Score s :
                scoreList) {
            s.draw(sb);
        }
    }

    @Override
    public void dispose() {
        textureBtn.dispose();
        boardTexture.dispose();
        gameOverSound.dispose();
        numberTexture.dispose();
        scoreTitleTexture.dispose();
    }

    /**
     * Testing touchpoints
     * @param x position of the touchpoint
     * @param y position of the touchpoint
     * @return the touched button or Button.NOTHING
     */
    public Button click(float x, float y){
        if(playBtn.collide(x, y)){
            return Button.PLAY_BTN;
        }else if(quitBtn.collide(x, y)){
            return Button.QUIT_BTN;
        }else {
            return Button.NOTHING;
        }
    }

    /**
     * Setting appearing attribute
     * @param appearing true if menu board is going to appear
     */
    public void setAppearing(boolean appearing) {
        this.appearing = appearing;
    }
}
