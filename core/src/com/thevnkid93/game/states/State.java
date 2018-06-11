package com.thevnkid93.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thevnkid93.game.GameStateManager;

public abstract class State {
    /**
     * Our orthographic camera
     */
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();


    }

    /**
     * Handle input. Clicking/touching
     */
    protected abstract void handleInput();

    /**
     * Update method. It is called in loop
     * @param dt the changed time
     */
    public abstract void update(float dt);

    /**
     * Rendering method. Called after every update method
     * @param sb
     */
    public abstract void render(SpriteBatch sb);

    /**
     * cleaning method
     */
    public abstract void dispose();

}
