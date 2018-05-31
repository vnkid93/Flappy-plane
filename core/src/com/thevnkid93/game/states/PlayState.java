package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.GameStateManager;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.managers.*;
import com.thevnkid93.game.sprites.Block;
import com.thevnkid93.game.sprites.Ground;
import com.thevnkid93.game.sprites.Plane;

public class PlayState extends State {
    enum GameState{
        PAUSING, PLAYING, ENDING;
    }
    public static GameState gameState;

    private Plane plane;
    private GroundManager groundManager;
    private DecorationManager decorationManager;
    private BackgroundManager backgroundManager;
    private BlockManager blockManager;
    private ScoreManager scoreManager;
    private TipsManager tipsManager;
    private TitleManager titleManager;
    private int watchingIndex;

    public PlayState(GameStateManager gsm){
        super(gsm);
        plane = new Plane();
        groundManager = new GroundManager();
        decorationManager = new DecorationManager(GroundManager.GROUND_HEIGHT*2, 5);
        backgroundManager = new BackgroundManager();
        blockManager = new BlockManager(Plane.PLANE_HEIGHT * 3, MyGame.HEIGHT - GroundManager.GROUND_HEIGHT*2);
        scoreManager = new ScoreManager();
        watchingIndex = blockManager.getIndexOfFirstGroup(); // should be 0
        tipsManager = new TipsManager();
        titleManager = new TitleManager();
        gameState = GameState.PAUSING;
        cam.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            if(gameState == GameState.PAUSING){
                plane.setState(Plane.PlaneState.FLYING);
                gameState = GameState.PLAYING;
                plane.jump();
                titleManager.setTitle(TitleManager.Title.NOTHING);
            }else if(gameState == GameState.PLAYING){
                plane.jump();
            }else {
                // dont jump
            }


        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        if(gameState == GameState.PLAYING){
            plane.update(dt);
            groundManager.update(dt);
            decorationManager.update(dt);
            backgroundManager.update(dt);
            blockManager.update(dt);

            if(blockManager.isPassed(watchingIndex, plane.getPosition().x)){
                scoreManager.increase();
                scoreManager.update(dt);
                watchingIndex = blockManager.getNewWatchingIndex(watchingIndex);
            }
            if(testCollision()){
                titleManager.setTitle(TitleManager.Title.GAME_OVER);
                gsm.set(new PlayState(gsm));
            }


        }else if(gameState == GameState.PAUSING) {
            // before starting
            plane.update(dt);
            groundManager.update(dt);
            backgroundManager.update(dt);
            tipsManager.update(dt);
        }else {
            // gameover... nothing move

        }




    }

    private boolean testCollision(){
        return blockManager.collide(plane.getBounds());
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        backgroundManager.draw(sb);
        plane.draw(sb);

        if(gameState == GameState.PLAYING){
            blockManager.draw(sb);
            groundManager.draw(sb);
            decorationManager.draw(sb);
            scoreManager.draw(sb);

        }else if(gameState == GameState.PAUSING){
            groundManager.draw(sb);
            tipsManager.draw(sb);
            titleManager.draw(sb);
        }else{
            groundManager.draw(sb);
            titleManager.draw(sb);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        plane.dispose();
        groundManager.dispose();
        backgroundManager.dispose();
        decorationManager.dispose();
    }
}
