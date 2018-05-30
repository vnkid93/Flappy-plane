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
import com.thevnkid93.game.managers.GroundManager;

import java.util.List;

public class Plane extends Sprite{
    enum PlaneState{
        STOPPING, FLYING;
    }
    private static final int FRAME_WIDTH = 88, FRAME_HEIGHT = 73;
    private static final float ANIMATION_CYCLE_TIME = 0.3f;
    public static final int PLANE_WIDTH = MyGame.WIDTH/6;
    public static final int PLANE_HEIGHT = FRAME_HEIGHT * PLANE_WIDTH / FRAME_WIDTH;
    private Vector2 velocity;

    private static final int GRAVITY = -20;
    private static final int JUMP_VALUE = 400;
    private Texture plane;
    // yDown = true
    private final int planeSprites[][][] = {
            {{0,6}, {1,4}, {1,0}},
            {{0,5},{0,1},{0,0}},
            {{0,4},{0,3},{0,2}},
            {{1,3},{1,2},{1,1}}};
    private PlaneState state;
    private Animation animation;

    private Rectangle bounds[];
    private float boundsRelYPositions[];
    private Sound flapSound;
    //private ShapeRenderer shapeRenderer;

    public Plane(float x, float y){
        super(x, y, PLANE_WIDTH, PLANE_HEIGHT);
        //shapeRenderer = new ShapeRenderer();
        flapSound = Gdx.audio.newSound(Gdx.files.internal("flap.mp3"));

        plane = new Texture(ImgCons.PLANES);
        int planeIndex = (int) (Math.random()*planeSprites.length);
        state = PlaneState.FLYING;
        velocity = new Vector2();
        animation = new Animation(new TextureRegion(plane), FRAME_WIDTH, FRAME_HEIGHT, ANIMATION_CYCLE_TIME, planeSprites[planeIndex]);
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

    @Override
    public void update(float dt) {
        animation.update(dt);
        if(position.y >= GroundManager.GROUND_HEIGHT*2){
            velocity.add(0, GRAVITY);
        }
        velocity.scl(dt);
        position.add(0, velocity.y);
        for (int i = 0; i < bounds.length; i++) {
            bounds[i].y = position.y + boundsRelYPositions[i];
        }

        if(position.y < GroundManager.GROUND_HEIGHT*2){
            position.y = GroundManager.GROUND_HEIGHT*2;
            for (int i = 0; i < bounds.length; i++) {
                bounds[i].y = position.y + boundsRelYPositions[i];
            }
        }

        velocity.scl(1/dt);
    }

    @Override
    public void dispose() {
        plane.dispose();
        flapSound.dispose();
    }

    @Override
    public void draw(SpriteBatch sb) {
        if(state == PlaneState.FLYING){
        }

        sb.draw(animation.getFrame(), position.x, position.y, PLANE_WIDTH, PLANE_HEIGHT);
        /*sb.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i < bounds.length; i++) {
            shapeRenderer.rect(bounds[i].x, bounds[i].y, bounds[i].width, bounds[i].height);

        }
        shapeRenderer.end();
        sb.begin();*/
    }


    public Rectangle[] getBounds() {
        return bounds;
    }


    public void jump(){
        flapSound.play();
        velocity.y = JUMP_VALUE;
    }



}
