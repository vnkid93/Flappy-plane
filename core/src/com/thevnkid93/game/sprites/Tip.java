package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tip extends Sprite{
    private TextureRegion frame;
    private int firstFrame;
    private Vector2 firstPosition;

    public Tip(float x, float y, int width, int height, TextureRegion frame) {
        super(x, y, width, height);
        firstPosition = new Vector2(x, y);
        this.frame = frame;
        this.firstFrame = 0;
    }

    @Override
    public void update(float dt) {
        // nothing here
    }

    @Override
    public void dispose() {
        // nothing here
    }

    public void setFrame(TextureRegion frame, int index) {
        this.frame = frame;
        if(this.firstFrame != index){
            position.x = firstPosition.x - Plane.PLANE_WIDTH/10;  // using some constant for optimalization
            position.y = firstPosition.y + Plane.PLANE_WIDTH/10;
        }else {
            position.set(firstPosition.x, firstPosition.y);
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(frame, position.x, position.y, width, height);
    }

}
