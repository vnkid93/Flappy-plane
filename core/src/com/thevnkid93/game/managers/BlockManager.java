package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Block;
import com.thevnkid93.game.sprites.Sprite;

public class BlockManager extends SpriteManager{

    public static final int WAVE_COUNT = 4;
    private Sprite topBlocks[][];
    private Sprite bottomBlocks[][];

    private float spacing, visibleHeight, verticalSpacing;
    private int blockPerGroup; // how many block in half of group (top + bottom = group)
    private int indexOfFirstGroup;
    private Texture texture;
    private int x;


    public BlockManager(float verticalSpacing, float spacingBetweenBlocks, float visibleHeight){
        this.spacing = spacingBetweenBlocks;
        this.visibleHeight = visibleHeight;
        this.verticalSpacing = verticalSpacing;
        texture = new Texture(ImgCons.BOX);
        blockPerGroup = (int)Math.ceil((visibleHeight - this.verticalSpacing) / Block.BLOCK_HEIGHT);
        generateBlocks();
        x=0;
    }

    private void generateBlocks(){
        //TODO count block group count - depends on screen height
        topBlocks = new Sprite[WAVE_COUNT][blockPerGroup];
        bottomBlocks = new Sprite[WAVE_COUNT][blockPerGroup];
        indexOfFirstGroup = 0;
        int startingX = MyGame.WIDTH * 3 / 2;
        for (int i = 0; i < WAVE_COUNT; i++) {
            int randY = getRandPosY();
            for (int j = 0; j < blockPerGroup; j++) {
                topBlocks[i][j] = new Block(startingX+(i*spacing), randY + verticalSpacing + (j + blockPerGroup) * Block.BLOCK_HEIGHT, texture);
                bottomBlocks[i][j] = new Block(startingX+(i*spacing), randY + j * Block.BLOCK_HEIGHT, texture);

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

    private void repositions(){
        int width = topBlocks[0][0].getWidth();
        int height = topBlocks[0][0].getHeight();
        int lastGroup = indexOfFirstGroup - 1 < 0 ? WAVE_COUNT - 1 : indexOfFirstGroup - 1;
        float newPosX = topBlocks[lastGroup][0].getPosition().x + width + spacing;
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

}
