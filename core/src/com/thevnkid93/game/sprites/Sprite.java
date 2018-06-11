package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Sprite {

    protected TextureRegion frame; // the texture
    protected Vector2 position; // position of the sprite object
    protected int width, height; // the drawing size, not the true texture size

    /**
     * @see #Sprite(float, float, int, int, TextureRegion)
     */
    public Sprite(float x, float y, int width, int height){
        this(x, y, width, height, null);
    }

    /**
     * Constructor of the sprite object
     * @param x position of the ground sprite object
     * @param y position of the ground sprite object
     * @param width the drawing width of the sprite object
     * @param height the height width of the sprite object
     * @param frame the texture of the sprite to draw
     */
    public Sprite(float x, float y, int width, int height, TextureRegion frame){
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.frame = frame;
    }

    public void update(float dt){
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setFrame(TextureRegion frame){
        this.frame = frame;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Deallocate memory - textures and sounds
     * Need to be called in the end
     */
    public void dispose(){}

    /**
     * Drawing/rendering method
     * @param sb drawing instance
     */
    public void draw(SpriteBatch sb){
        sb.draw(frame, position.x, position.y, width, height);
    }
}
