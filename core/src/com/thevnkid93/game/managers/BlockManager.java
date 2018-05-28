package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.Block;
import com.thevnkid93.game.sprites.Sprite;

public class BlockManager {

    private Sprite topBlocks[][];
    private Sprite bottomBlocks[][];
    private Vector2 position;
    private float spacing, visibleHeight, verticalSpacing;
    private int blockGroupCount; // how many block in half of group (top + bottom = group)
    private int waveCount; // how many group
    private int indexOfFirstGroup;

    public BlockManager(float vecticalSpacing, float spacingBetweenBlocks, float visibleHeight){
        this.spacing = spacingBetweenBlocks;
        this.visibleHeight = visibleHeight;
        this.verticalSpacing = vecticalSpacing;

        generateBlocks();
    }

    private void generateBlocks(){
        //TODO count block group count - depends on screen height
        indexOfFirstGroup = 0;
        position.x = (float)((Math.random() + 1.5) * MyGame.WIDTH);
        for (int i = 0; i < blockGroupCount; i++) {

        }
    }

    private void update(float dt){
        for (int i = 0; i < waveCount; i++) {
            for (int j = 0; j < blockGroupCount; j++) {
                topBlocks[i][j].update(dt);
                bottomBlocks[i][j].update(dt);
            }
        }
        if(topBlocks[indexOfFirstGroup][0].getPosition().x < -Block.BLOCK_WIDTH){
            for (int i = 0; i < blockGroupCount; i++) {
                topBlocks[indexOfFirstGroup][i].getPosition().x = 0;
                bottomBlocks[indexOfFirstGroup][i].getPosition().x = 0;
            }
            indexOfFirstGroup = (indexOfFirstGroup + 1) % waveCount;
        }

    }

    private void repositions(float lastPosX){

    }

    private void draw(SpriteBatch sb){
        for (int i = 0; i < waveCount; i++) {
            for (int j = 0; j < blockGroupCount; j++) {
                topBlocks[i][j].draw(sb);
                bottomBlocks[i][j].draw(sb);
            }
        }
    }

}
