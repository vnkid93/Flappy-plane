package com.thevnkid93.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.SoundCons;
import com.thevnkid93.game.managers.GroundManager;

import java.util.List;

public class Plane extends Sprite{
    /**
     * Plane state
     */
    public enum PlaneState{
        HOVERING, FLYING;
    }
    private PlaneState state;

    private static final int DEFAULT_X = MyGame.WIDTH/4;
    private static final int DEFAULT_Y = MyGame.HEIGHT/2;
    private static final int FRAME_WIDTH = 88, FRAME_HEIGHT = 73;
    private static final float ANIMATION_CYCLE_TIME = 0.3f;
    public static final int PLANE_WIDTH = MyGame.WIDTH/6;
    public static final int PLANE_HEIGHT = FRAME_HEIGHT * PLANE_WIDTH / FRAME_WIDTH;
    private static final int GRAVITY = -20;
    private static final int JUMP_VALUE = 400;

    private Vector2 velocity; // falling velocity
    private Texture plane;
    // the coordinates of plane textures (see planes.png)
    private final int planeSprites[][][] = {
            {{0,6}, {1,4}, {1,0}},
            {{0,5},{0,1},{0,0}},
            {{0,4},{0,3},{0,2}},
            {{1,3},{1,2},{1,1}}};
    private Animation animation;

    private Rectangle bounds[]; // plane bounds - for collision detection
    private float boundsRelYPositions[]; // relative positions of plane bounds
    private Sound flapSound;

    public Plane(){
        super(DEFAULT_X, DEFAULT_Y, PLANE_WIDTH, PLANE_HEIGHT);
        flapSound = Gdx.audio.newSound(Gdx.files.internal(SoundCons.FLAP));

        plane = new Texture(ImgCons.PLANES);
        int randomPlane = (int) (Math.random()*planeSprites.length);
        state = PlaneState.HOVERING;
        velocity = new Vector2();
        animation = new Animation(new TextureRegion(plane), FRAME_WIDTH, FRAME_HEIGHT, ANIMATION_CYCLE_TIME, planeSprites[randomPlane]);
        initBounds();
    }

    /**
     * Statically computed. That's why it contains 'magic' numbers
     */
    private void initBounds(){
        bounds = new Rectangle[4];
        bounds[0] = new Rectangle(18, 7, 59, 66);
        bounds[1] = new Rectangle(0, 33, 18, 29);
        bounds[2] = new Rectangle(48, 0, 20, 7);
        bounds[3] = new Rectangle(77, 7, 11, 48);
        boundsRelYPositions = new float[bounds.length];
        float resizeRatio = (float)width/FRAME_WIDTH;
        for (int i = 0; i < bounds.length; i++) {
            boundsRelYPositions[i] = bounds[i].y * resizeRatio;
            bounds[i].x = position.x + (bounds[i].x * resizeRatio);
            bounds[i].y = position.y + boundsRelYPositions[i];
            bounds[i].width *= resizeRatio;
            bounds[i].height *= resizeRatio;
        }

    }

    /**
     * @see Sprite#update(float)
     */
    @Override
    public void update(float dt) {
        if(state == PlaneState.HOVERING){
            animation.update(dt);
        }else {
            animation.update(dt);
            if(state == PlaneState.FLYING){
                velocity.add(0, GRAVITY);

                velocity.scl(dt);
                position.add(0, velocity.y);
                for (int i = 0; i < bounds.length; i++) {
                    bounds[i].y = position.y + boundsRelYPositions[i];
                }
                velocity.scl(1/dt);
            }
        }
    }

    /**
     * @see Sprite#dispose()
     */
    @Override
    public void dispose() {
        plane.dispose();
        flapSound.dispose();
    }

    /**
     * @see Sprite#draw(SpriteBatch)
     */
    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(animation.getFrame(), position.x, position.y, PLANE_WIDTH, PLANE_HEIGHT);
    }


    public Rectangle[] getBounds() {
        return bounds;
    }

    /**
     * The only one plane action.
     * Making plane and its bounds jump
     */
    public void jump(){
        if(getPosition().y + height < MyGame.HEIGHT){
            flapSound.play();
            velocity.y = JUMP_VALUE;
        }
    }

    public void setState(PlaneState state){
        this.state = state;
    }


}
