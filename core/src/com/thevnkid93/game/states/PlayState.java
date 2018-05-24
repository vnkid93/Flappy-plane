package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.GameStateManager;
import com.thevnkid93.game.ImgCons;
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
        plane = new Plane(MyGame.WIDTH/3, MyGame.HEIGHT/2);
        ground = new Ground();
        decorationManager = new DecorationManager(MyGame.WIDTH, Ground.GROUND_HEIGHT*2, 5);
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
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(backgroundTx, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        ground.draw(sb);
        decorationManager.draw(sb);
        plane.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        plane.dispose();
        ground.dispose();
        backgroundTx.dispose();
        decorationManager.dispose();
    }
}
