package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.GameStateManager;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.managers.BackgroundManager;
import com.thevnkid93.game.managers.DecorationManager;
import com.thevnkid93.game.sprites.Ground;
import com.thevnkid93.game.sprites.Plane;

public class PlayState extends State {

    private Plane plane;
    private Ground ground;
    private DecorationManager decorationManager;
    private BackgroundManager backgroundManager;

    public PlayState(GameStateManager gsm){
        super(gsm);
        plane = new Plane(MyGame.WIDTH/4, MyGame.HEIGHT/2);
        ground = new Ground();
        decorationManager = new DecorationManager(Ground.GROUND_HEIGHT*2, 5);
        backgroundManager = new BackgroundManager();
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
        ground.update(dt);
        decorationManager.update(dt);
        backgroundManager.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        backgroundManager.draw(sb);
        ground.draw(sb);
        decorationManager.draw(sb);
        plane.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        plane.dispose();
        ground.dispose();
        backgroundManager.dispose();
        decorationManager.dispose();
    }
}
