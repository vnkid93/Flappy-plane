package com.thevnkid93.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.states.State;

import java.util.Stack;

public class GameStateManager {
    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public State pop(){
        return states.pop();
    }

    public void set(State state){
        pop();
        push(state);
    }

    /**
     * Update for the state on the top
     * @param dt
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * Render the top state
     * @param sb
     */
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
