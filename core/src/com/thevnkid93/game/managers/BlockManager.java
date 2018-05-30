package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Block;


public class BlockManager extends SpriteManager{

    private static final int SPACING = Block.BLOCK_WIDTH * 3;

    public static final int WAVE_COUNT = 4;
    private Block topBlocks[][];
    private Block bottomBlocks[][];

    private float visibleHeight, verticalSpacing;
    private int blockPerGroup; // how many block in half of group (top + bottom = group)
    private int indexOfFirstGroup;
    private Texture texture;


    public BlockManager(float verticalSpacing, float visibleHeight){
        this.visibleHeight = visibleHeight;
        this.verticalSpacing = verticalSpacing;
        texture = new Texture(ImgCons.BOX);
        blockPerGroup = (int)Math.ceil((visibleHeight - this.verticalSpacing) / Block.BLOCK_HEIGHT);
        generateBlocks();
        indexOfFirstGroup = 0;
    }

    private void generateBlocks(){
        //TODO count block group count - depends on screen height
        topBlocks = new Block[WAVE_COUNT][blockPerGroup];
        bottomBlocks = new Block[WAVE_COUNT][blockPerGroup];
        int startingX = MyGame.WIDTH * 3 / 2;
        for (int i = 0; i < WAVE_COUNT; i++) {
            int randY = getRandPosY();
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j] = new Block(startingX+(i*SPACING), randY + verticalSpacing + (j + blockPerGroup) * Block.BLOCK_HEIGHT, texture);
                bottomBlocks[i][j] = new Block(startingX+(i*SPACING), randY + j * Block.BLOCK_HEIGHT, texture);

            }
        }
    }

    @Override
    public void update(float dt){
        for (int i = 0; i < WAVE_COUNT; i++) {
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j].update(dt);
                bottomBlocks[i][j].update(dt);
            }
        }
        if(topBlocks[indexOfFirstGroup][0].getPosition().x < -Block.BLOCK_WIDTH){
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
        if(topBlocks[index][0].getPosition().x + Block.BLOCK_WIDTH/2 < planePosX){
            return true;
        }
        return false;
    }

    private void repositions(){
        int width = topBlocks[0][0].getWidth();
        int height = topBlocks[0][0].getHeight();
        int lastGroup = indexOfFirstGroup - 1 < 0 ? WAVE_COUNT - 1 : indexOfFirstGroup - 1;
        float newPosX = topBlocks[lastGroup][0].getPosition().x + SPACING;
        float newPosY = getRandPosY();
        for (int i = 0; i < blockPerGroup; i++) {
            topBlocks[indexOfFirstGroup][i].getPosition().x = newPosX;
            topBlocks[indexOfFirstGroup][i].getPosition().y = newPosY + verticalSpacing + ((blockPerGroup+i)*height);
            bottomBlocks[indexOfFirstGroup][i].getPosition().x = newPosX;
            bottomBlocks[indexOfFirstGroup][i].getPosition().y = newPosY + (i*height);
        }
        indexOfFirstGroup = (indexOfFirstGroup + 1) % WAVE_COUNT;
    }

    @Override
    public void draw(SpriteBatch sb){
        for (int i = 0; i < WAVE_COUNT; i++) {
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
        result = result - (blockPerGroup * Block.BLOCK_HEIGHT);
        return (int) result;
    }

    public boolean collide(Rectangle bounds[]){
        int firstWave = indexOfFirstGroup;
        int secondWave = (firstWave+1) % WAVE_COUNT;
        for (int i = 0; i < blockPerGroup; i++) {
            for (int j = 0; j < bounds.length; j++) {
                if(
                    topBlocks[firstWave][i].getBound().overlaps(bounds[j]) ||
                    bottomBlocks[firstWave][i].getBound().overlaps(bounds[j])||
                    topBlocks[secondWave][i].getBound().overlaps(bounds[j]) ||
                    bottomBlocks[secondWave][i].getBound().overlaps(bounds[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public int getIndexOfFirstGroup() {
        return indexOfFirstGroup;
    }

    public int getNewWatchingIndex(int watchingIndex){
        watchingIndex = (watchingIndex+1) % WAVE_COUNT;
        return watchingIndex;
    }
}
