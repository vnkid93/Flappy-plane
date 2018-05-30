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

    private Plane plane;
    private GroundManager groundManager;
    private DecorationManager decorationManager;
    private BackgroundManager backgroundManager;
    private BlockManager blockManager;
    private ScoreManager scoreManager;
    private int watchingIndex;

    public PlayState(GameStateManager gsm){
        super(gsm);
        plane = new Plane(MyGame.WIDTH/4, MyGame.HEIGHT/2);
        groundManager = new GroundManager();
        decorationManager = new DecorationManager(GroundManager.GROUND_HEIGHT*2, 5);
        backgroundManager = new BackgroundManager();
        blockManager = new BlockManager(Plane.PLANE_HEIGHT * 3, MyGame.HEIGHT - GroundManager.GROUND_HEIGHT*2);
        scoreManager = new ScoreManager();
        watchingIndex = blockManager.getIndexOfFirstGroup(); // should be 0
        cam.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            plane.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
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
            gsm.set(new PlayState(gsm));
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
        blockManager.draw(sb);
        groundManager.draw(sb);
        decorationManager.draw(sb);
        plane.draw(sb);
        scoreManager.draw(sb);
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
