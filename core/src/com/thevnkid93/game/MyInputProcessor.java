package com.thevnkid93.game;

import com.badlogic.gdx.InputProcessor;

/**
 * Our own input processor for full control of touch down/up events.
 * We need the down/up events for the button animations
 */
public abstract class MyInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * When the screen is touched
     * @param screenX the x position of the touch point
     * @param screenY the y position of the touch point
     * @param pointer unkown
     * @param button unkown
     * @return true if we is going to use this method
     */
    @Override
    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

    /**
     * When the touch is released
     * @param screenX the x position of the releasing point
     * @param screenY the y position of the releasing point
     * @param pointer
     * @param button
     * @return true if we want of use this method
     */
    @Override
    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
