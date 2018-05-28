package com.thevnkid93.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;

public class Plane extends Sprite{
    enum PlaneState{
        STOPPING, FLYING;
    }
    private static final int FRAME_WIDTH = 88, FRAME_HEIGHT = 73;
    private static final float ANIMATION_CYCLE_TIME = 0.3f;
    public static final int PLANE_WIDTH = MyGame.WIDTH/6;
    public static final int PLANE_HEIGHT = PLANE_WIDTH * FRAME_HEIGHT / FRAME_WIDTH;
    private Vector2 velocity;

    private static final int GRAVITY = -20;
    private Texture plane;
    // yDown = true
    private final int planeSprites[][][] = {
            {{0,6}, {1,4}, {1,0}},
            {{0,5},{0,1},{0,0}},
            {{0,4},{0,3},{0,2}},
            {{1,3},{1,2},{1,1}}};
    private PlaneState state;
    private Animation animation;

    public Plane(float x, float y){
        super(x, y, PLANE_WIDTH, PLANE_HEIGHT);
        plane = new Texture(ImgCons.PLANES);
        int planeIndex = (int) (Math.random()*planeSprites.length);
        state = PlaneState.FLYING;
        velocity = new Vector2();
        animation = new Animation(new TextureRegion(plane), FRAME_WIDTH, FRAME_HEIGHT, ANIMATION_CYCLE_TIME, planeSprites[planeIndex]);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        if(position.y >= 0){
            velocity.add(0, GRAVITY);
        }
        velocity.scl(dt);
        position.add(0, velocity.y);

        if(position.y < 0){
            position.y = 0;
        }

        velocity.scl(1/dt);
    }

    @Override
    public void dispose() {
        plane.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        if(state == PlaneState.FLYING){
        }

        sb.draw(animation.getFrame(), position.x, position.y, PLANE_WIDTH, PLANE_HEIGHT);
    }


    public void jump(){
        velocity.y = 400;
    }


}
