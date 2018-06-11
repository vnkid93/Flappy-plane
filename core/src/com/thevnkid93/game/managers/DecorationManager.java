package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Decoration;
import com.thevnkid93.game.sprites.Ground;
import com.thevnkid93.game.sprites.Plane;

/**
 * Decoration manager for drawing and scrolling trees, rocks and other decoration objects
 */
public class DecorationManager extends SpriteManager{

    public static final int DECORATION_SCROLL_SPEED = GroundManager.SCROLLING_SPEED;
    private static final int DECORATION_COUNT = 8;
    private static final int DECORATION_WIDTH = Plane.PLANE_WIDTH*3/2;
    private int frameWidth;
    private Array<TextureRegion> textures;
    private Texture texture;
    private Decoration decorations[];

    public DecorationManager(int groundHeight, int count){
        textures = new Array<TextureRegion>();
        texture = new Texture(ImgCons.GROUND_DECORATIONS);
        frameWidth = texture.getWidth()/DECORATION_COUNT;
        for (int i = 0; i < DECORATION_COUNT; i++) {
            textures.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight()));
        }
        decorations = new Decoration[count];

        int decorationHeight = texture.getHeight() * DECORATION_WIDTH / frameWidth;
        for (int i = 0; i < count; i++) {
            decorations[i] = new Decoration(0, groundHeight - 15, DECORATION_WIDTH, decorationHeight, getRandomTexture());
            reposition(i);
        }
    }

    @Override
    public void dispose(){
        texture.dispose();
    }

    /**
     * Scrolling objects
     * @param dt the changing time
     */
    @Override
    public void update(float dt) {
        for (int i = 0; i < decorations.length; i++) {
            decorations[i].update(dt);
            if(decorations[i].getPosition().x < -frameWidth){
                changeDecoration(i);
                reposition(i);
            }
        }
    }

    @Override
    public void draw(SpriteBatch sb){
        for (int i = 0; i < decorations.length; i++) {
            decorations[i].draw(sb);
        }
    }

    /**
     * Get random object to draw
     * @return TextureRegion of the new object
     */
    private TextureRegion getRandomTexture(){
        return textures.get((int) (Math.random()*DECORATION_COUNT));
    }

    /**
     * Changing the sprite sheet. Drawing random object
     * @param index the object to be change
     */
    private void changeDecoration(int index){
        decorations[index].setFrame(getRandomTexture());
    }

    /**
     * Repositioning decorationg after they got out of the screen
     * @param index the object to repositioning
     */
    private void reposition(int index){
        decorations[index].getPosition().x = (float)((Math.random()*4*MyGame.WIDTH) + MyGame.WIDTH*2);
    }
}
