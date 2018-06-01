package com.thevnkid93.game.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.sprites.BasicSprite;

public class MenuBoardManager extends SpriteManager {
    public enum Button{
        PLAY_BTN, QUIT_BTN, NOTHING;
    }

    private static final int BTN_FRAME_COUNT = 6;
    private BasicSprite board, playBtn, quitBtn;
    private Texture textureBtn, boardTexture;


    private Array<TextureRegion> btnFrames;

    public MenuBoardManager(){
        boardTexture = new Texture(ImgCons.BOARD);
        int boardWidth = MyGame.WIDTH * 3/4;
        board = new BasicSprite(MyGame.WIDTH/2 - boardWidth/2, MyGame.HEIGHT/2, boardWidth, boardWidth *3/4, new TextureRegion(boardTexture));


        textureBtn = new Texture(ImgCons.MENU_BTNS);
        int btnFrameWidth = textureBtn.getWidth()/6;
        int btnFrameHeight = textureBtn.getHeight();
        int drawingBtnWidth = MyGame.WIDTH/4;
        int drawingBtnHeight = btnFrameHeight * drawingBtnWidth / btnFrameWidth;

        btnFrames = new Array<TextureRegion>();
        for (int i = 0; i < BTN_FRAME_COUNT; i++) {
            btnFrames.add(new TextureRegion(textureBtn, i*btnFrameWidth, 0, btnFrameWidth, btnFrameHeight));
        }

        int padding = drawingBtnHeight/4;
        playBtn = new BasicSprite(MyGame.WIDTH/2 - drawingBtnWidth/2, MyGame.HEIGHT/2 - padding - drawingBtnHeight, drawingBtnWidth, drawingBtnHeight, btnFrames.get(0));
        quitBtn = new BasicSprite(MyGame.WIDTH/2 - drawingBtnWidth/2, MyGame.HEIGHT/2 - 2*drawingBtnHeight - 2*padding, drawingBtnWidth, drawingBtnHeight, btnFrames.get(4));
    }

    @Override
    public void update(float dt) {
        //nothing
    }

    public void clickPlayBtn(){

    }

    @Override
    public void draw(SpriteBatch sb) {
        playBtn.draw(sb);
        quitBtn.draw(sb);
        board.draw(sb);
    }

    @Override
    public void dispose() {
        textureBtn.dispose();
        boardTexture.dispose();
    }

    public Button click(float x, float y){
        if(playBtn.collide(x, y)){
            return Button.PLAY_BTN;
        }else if(quitBtn.collide(x, y)){
            return Button.QUIT_BTN;
        }else {
            return Button.NOTHING;
        }
    }
}
