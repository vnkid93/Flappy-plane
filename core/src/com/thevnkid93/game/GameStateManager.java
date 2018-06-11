package com.thevnkid93.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.states.State;

import java.util.Stack;

/**
 * A manager for managing our states. It has Stack structure
 */
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
        pop().dispose();
        push(state);
    }

    /**
     * Update for the state on the top
     * @param dt the changed time - delta time
     */
    public boolean update(float dt){
        if(states.isEmpty()){
            return false;
        }else {
            states.peek().update(dt);
            return true;
        }
    }

    /**
     * Render the top state
     * @param sb rendering instance
     */
    public boolean render(SpriteBatch sb){
        if(states.isEmpty()){
            return false;
        }else {
            states.peek().render(sb);
            return true;
        }
    }
}
