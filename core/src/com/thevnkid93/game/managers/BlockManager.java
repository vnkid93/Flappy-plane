package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Block;
import com.thevnkid93.game.sprites.Plane;

/**
 * Manager class of blocks/boxes for collision testing and scrolling boxes
 */
public class BlockManager extends SpriteManager{

    public static final int SCROLLING_SPEED = GroundManager.SCROLLING_SPEED;
    private static final int BLOCK_WIDTH = Plane.PLANE_WIDTH * 3 / 2; // 1.5x
    private static final int BLOCK_HEIGHT = BLOCK_WIDTH;
    private static final int SPACING = BLOCK_WIDTH * 3;

    private int waveCount; // how many groups
    private Block topBlocks[][];
    private Block bottomBlocks[][];

    private float visibleHeight, verticalSpacing;
    private int blockPerGroup; // how many block in half of group (top + bottom = group)
    private int indexOfFirstGroup; // the incoming group of boxes
    private Texture texture;
    private TextureRegion frame;


    public BlockManager(float visibleHeight){
        this.visibleHeight = visibleHeight;
        this.verticalSpacing = Plane.PLANE_HEIGHT * 5 / 2;
        texture = new Texture(ImgCons.BOX);
        frame = new TextureRegion(texture);
        blockPerGroup = (int)Math.ceil((visibleHeight - this.verticalSpacing) / BLOCK_HEIGHT);
        generateBlocks();
        indexOfFirstGroup = 0;
    }

    /**
     * Box initialization. It calculate how many groups and how many boxes are needed
     */
    private void generateBlocks(){
        waveCount = (int) (Math.ceil((double)MyGame.WIDTH / (BLOCK_WIDTH + SPACING)) + 1);

        topBlocks = new Block[waveCount][blockPerGroup];
        bottomBlocks = new Block[waveCount][blockPerGroup];
        int startingX = MyGame.WIDTH * 3 / 2;
        for (int i = 0; i < waveCount; i++) {
            int randY = getRandPosY();
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j] = new Block(startingX+(i*SPACING), randY + verticalSpacing + (j + blockPerGroup) * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, frame);
                bottomBlocks[i][j] = new Block(startingX+(i*SPACING), randY + j * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, frame);

            }
        }
    }

    /**
     * Scrolling boxes
     * @param dt the changing time
     */
    @Override
    public void update(float dt){
        for (int i = 0; i < waveCount; i++) {
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j].update(dt);
                bottomBlocks[i][j].update(dt);
            }
        }
        if(topBlocks[indexOfFirstGroup][0].getPosition().x < -BLOCK_WIDTH){
            repositions();
        }

    }

    /**
     * Test if plane have passed those blocks
     * @param index The index of the incoming block group
     * @param planePosX The x position of the plane to considering as passed
     * @return true if the plane has passed, otherwise false
     */
    public boolean isPassed(int index, float planePosX){
        if(topBlocks[index][0].getPosition().x + BLOCK_WIDTH/2 < planePosX){
            return true;
        }
        return false;
    }

    /**
     * Repositioning blocks so that they will appear again after getting out of the screen
     */
    private void repositions(){
        int width = topBlocks[0][0].getWidth();
        int height = topBlocks[0][0].getHeight();
        int lastGroup = indexOfFirstGroup - 1 < 0 ? waveCount - 1 : indexOfFirstGroup - 1;
        float newPosX = topBlocks[lastGroup][0].getPosition().x + SPACING;
        float newPosY = getRandPosY();
        for (int i = 0; i < blockPerGroup; i++) {
            topBlocks[indexOfFirstGroup][i].getPosition().x = newPosX;
            topBlocks[indexOfFirstGroup][i].getPosition().y = newPosY + verticalSpacing + ((blockPerGroup+i)*height);
            bottomBlocks[indexOfFirstGroup][i].getPosition().x = newPosX;
            bottomBlocks[indexOfFirstGroup][i].getPosition().y = newPosY + (i*height);
        }
        indexOfFirstGroup = (indexOfFirstGroup + 1) % waveCount;
    }

    /**
     * Drawing boxes. Top and bottom boxes
     * @param sb drawing instance
     */
    @Override
    public void draw(SpriteBatch sb){
        for (int i = 0; i < waveCount; i++) {
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j].draw(sb);
                bottomBlocks[i][j].draw(sb);
            }
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    /**
     *
     * @return the Y position of the left bottom corner of the lowest block
     */
    private int getRandPosY(){
        double padding = visibleHeight * 0.1;
        double rand = Math.random() * (visibleHeight - padding - verticalSpacing);
        double result = rand + (MyGame.HEIGHT - visibleHeight) + (padding/2);
        result = result - (blockPerGroup * BLOCK_HEIGHT);
        return (int) result;
    }

    /**
     * Collision testing.
     * @param bounds the bounds of object we want to test
     * @return true if the collision was detected, otherwise false
     */
    public boolean collide(Rectangle bounds[]){
        int firstWave = indexOfFirstGroup;
        int secondWave = (firstWave+1) % waveCount;
        for (int i = 0; i < blockPerGroup; i++) {
            for (Rectangle bound : bounds) {
                if (
                        topBlocks[firstWave][i].getBound().overlaps(bound) ||
                        bottomBlocks[firstWave][i].getBound().overlaps(bound) ||
                        topBlocks[secondWave][i].getBound().overlaps(bound) ||
                        bottomBlocks[secondWave][i].getBound().overlaps(bound)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getIndexOfFirstGroup() {
        return indexOfFirstGroup;
    }

    /**
     * Calculate and return new watching index
     * @param watchingIndex the actual watching index
     * @return new watching index
     */
    public int getNewWatchingIndex(int watchingIndex){
        watchingIndex = (watchingIndex+1) % waveCount;
        return watchingIndex;
    }
}
