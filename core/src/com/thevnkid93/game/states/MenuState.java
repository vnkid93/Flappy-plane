package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.thevnkid93.game.GameStateManager;
import com.thevnkid93.game.ImgCons;
import com.thevnkid93.game.MyGame;
import com.thevnkid93.game.MyInputProcessor;
import com.thevnkid93.game.managers.BackgroundManager;
import com.thevnkid93.game.sprites.BasicSprite;
import com.thevnkid93.game.sprites.Plane;

public class MenuState extends State {

    private BackgroundManager backgroundManager;
    private Texture title;
    private int titleWidth, titleHeight;
    private Texture btnTexture;
    private int playBtnIndex, quitBtnIndex;
    private TextureRegion playBtn[], quitBtn[];
    private int btnWidth, btnHeight;

    private Rectangle playBtnBound, quitBtnBound;

    private Plane planes[];

    private Vector3 touchPoint;
    private BitmapFont font;
    private GlyphLayout author;
    private MyInputProcessor inputProcessor;
    private boolean isTouching;
    private Sound clickSound;




    public MenuState(GameStateManager gsm){
        super(gsm);
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        backgroundManager = new BackgroundManager(false);
        title = new Texture(ImgCons.TITLE);
        titleWidth = MyGame.WIDTH *7/8;
        titleHeight = title.getHeight() * titleWidth/title.getWidth();

        planes = new Plane[3];
        for (int i = 0; i < planes.length; i++) {
            planes[i] = new Plane();
            planes[i].getPosition().set((float)(Math.random() * MyGame.WIDTH - MyGame.WIDTH/2), (float)(Math.random()*MyGame.HEIGHT/2 + MyGame.HEIGHT/4));
        }
        initButtons();

        cam.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        touchPoint = new Vector3();
        font = new BitmapFont();
        author = new GlyphLayout(font, "Duc Vuong Tran");
        inputProcessor = new MyInputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                MenuState.this.touchDown();
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                MenuState.this.touchUp();
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputProcessor);
        isTouching = false;
    }


    private void initButtons(){
        final int FRAME_COUNT = 6;
        btnTexture = new Texture(ImgCons.MENU_BTNS);
        final int frameWidth = btnTexture.getWidth()/FRAME_COUNT;
        final int frameHeight = btnTexture.getHeight();
        btnWidth = MyGame.WIDTH/2;
        btnHeight = btnTexture.getHeight() * btnWidth / (btnTexture.getWidth()/6);


        playBtn = new TextureRegion[2];
        quitBtn = new TextureRegion[2];
        for (int i = 0; i < playBtn.length; i++) {
            playBtn[i] = new TextureRegion(btnTexture, i*frameWidth, 0, frameWidth, frameHeight);
            quitBtn[i] = new TextureRegion(btnTexture, (i+4)*frameWidth, 0, frameWidth, frameHeight);
        }

        playBtnBound = new Rectangle(MyGame.WIDTH/2 - btnWidth/2, MyGame.HEIGHT/2 - btnHeight/2,btnWidth, btnHeight);
        quitBtnBound = new Rectangle(MyGame.WIDTH/2 - btnWidth/2, (float) (MyGame.HEIGHT/2 - btnHeight/2 - btnHeight * 1.2),btnWidth, btnHeight);
        playBtnIndex = quitBtnIndex = 0;
    }


    public void touchDown(){
        isTouching = true;
        if(playBtnBound.contains(touchPoint.x, touchPoint.y)){
            playBtnIndex = 1;
            clickSound.play();
        }else if(quitBtnBound.contains(touchPoint.x, touchPoint.y)){
            quitBtnIndex = 1;
            clickSound.play();
        }else {
            playBtnIndex = quitBtnIndex = 0;
        }
    }

    public void touchUp(){
        if(isTouching){
            playBtnIndex = 0;
            quitBtnIndex = 0;
            if(playBtnBound.contains(touchPoint.x, touchPoint.y)){
                gsm.set(new PlayState(gsm));
                dispose();
            }else if(quitBtnBound.contains(touchPoint.x, touchPoint.y)){
                gsm.pop();
                dispose();
            }
        }
        isTouching = false;
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        //handleInput();
        backgroundManager.update(dt);
        for (int i = 0; i < planes.length; i++) {
            updatePlane(planes[i], dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        backgroundManager.draw(sb);
        sb.draw(title, MyGame.WIDTH/2 - titleWidth/2, MyGame.HEIGHT*3/4, titleWidth, titleHeight);
        sb.draw(playBtn[playBtnIndex], playBtnBound.x, playBtnBound.y, btnWidth, btnHeight);
        sb.draw(quitBtn[quitBtnIndex], quitBtnBound.x, quitBtnBound.y, btnWidth, btnHeight);
        for (int i = 0; i < planes.length; i++) {
            planes[i].draw(sb);
        }


        font.draw(sb, author, MyGame.WIDTH/2 - author.width/2, MyGame.HEIGHT/10);
        sb.end();
    }

    private void updatePlane(Plane plane, float dt){
        Vector2 pos = plane.getPosition();
        pos.add((float)(Math.random()*5 ), (float)(Math.random()*4 - 2));

        if(pos.x > MyGame.WIDTH){
            pos.x = -plane.getWidth();
        }
        if(pos.y < -plane.getHeight()){
            pos.y = MyGame.HEIGHT;
        }else if(pos.y > MyGame.HEIGHT){
            pos.y = -plane.getHeight();
        }
        plane.update(dt);

    }

    @Override
    public void dispose() {
        backgroundManager.dispose();
        btnTexture.dispose();
        for (int i = 0; i < planes.length; i++) {
            planes[i].dispose();
        }
        title.dispose();
        clickSound.dispose();
    }


}
