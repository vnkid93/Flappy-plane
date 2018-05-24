package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.GameStateManager;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;

public class MenuState extends State {
    private Texture backgroundTx;
    private Texture playBtnTx;


    public MenuState(GameStateManager gsm){
        super(gsm);
        backgroundTx = new Texture(ImgCons.BACKGROUND);
        playBtnTx = new Texture(ImgCons.BTN_PLAY_INACTIVE);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(backgroundTx, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        sb.draw(playBtnTx, MyGame.WIDTH/2 - playBtnTx.getWidth()/2, MyGame.HEIGHT/2 - playBtnTx.getHeight()/2);

        sb.end();
    }

    @Override
    public void dispose() {
        backgroundTx.dispose();
        playBtnTx.dispose();
    }
}
