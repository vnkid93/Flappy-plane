package com.thevnkid93.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thevnkid93.game.*;
import com.thevnkid93.game.managers.*;
import com.thevnkid93.game.sprites.Plane;

public class PlayState extends State {
    /**
     * Game state
     */
    enum GameState{
        PAUSING, PLAYING, ENDING;
    }

    public static GameState gameState;

    private Plane plane; // plane object
    private GroundManager groundManager; // ground manager
    private DecorationManager decorationManager; // manager of trees, rocks, etc.
    private BackgroundManager backgroundManager; // background manager
    private BlockManager blockManager; // blocks/boxes manager
    private ScoreManager scoreManager; // score number manager
    private TipsManager tipsManager; // tool tip manager
    private TitleManager titleManager; // title drawing manager
    private MenuBoardManager menuBoardManager; // score board manager
    private Sound clickSound;

    // The index of the incoming block group. For collision testing
    private int incomingBlockIndex;

    // Contain touch point - to test where the finger has touched
    private Vector3 touchPoint;
    // true if the finger isn still down (not released)
    private boolean isTouching;


    public PlayState(GameStateManager gsm){
        super(gsm);
        clickSound = Gdx.audio.newSound(Gdx.files.internal(SoundCons.CLICK_SOUND));
        plane = new Plane();
        groundManager = new GroundManager();
        decorationManager = new DecorationManager(GroundManager.GROUND_HEIGHT*2, 5);
        backgroundManager = new BackgroundManager(true);
        blockManager = new BlockManager(MyGame.HEIGHT - GroundManager.GROUND_HEIGHT*2);
        scoreManager = new ScoreManager(Plane.PLANE_WIDTH/3);
        tipsManager = new TipsManager();
        titleManager = new TitleManager();
        menuBoardManager = new MenuBoardManager();

        incomingBlockIndex = blockManager.getIndexOfFirstGroup(); // should be 0
        gameState = GameState.PAUSING;
        cam.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        touchPoint = new Vector3();

        Gdx.input.setInputProcessor(new MyInputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(gameState == GameState.ENDING){
                    isTouching = true;
                    cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    PlayState.this.touchDown(touchPoint.x, touchPoint.y);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(gameState == GameState.ENDING){
                    if(isTouching){
                        cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                        PlayState.this.touchUp(touchPoint.x, touchPoint.y);
                    }
                    isTouching = false;
                }
                return true;
            }
        });
        isTouching = false;
    }

    /**
     * Method is called when screen was touched
     * @param x position of touchpoint
     * @param y position of touchpoint
     */
    private void touchDown(float x, float y){
        MenuBoardManager.Button clickResult = menuBoardManager.click(x, y);
        if(clickResult == MenuBoardManager.Button.PLAY_BTN){
            clickSound.play();
            menuBoardManager.clickPlayBtn();
        }else if(clickResult == MenuBoardManager.Button.QUIT_BTN){
            clickSound.play();
            menuBoardManager.clickQuitBtn();
        }
    }
    /**
     * Method is called when the touched screen was released
     * @param x position of touchpoint
     * @param y position of touchpoint
     */
    private void touchUp(float x, float y){
        MenuBoardManager.Button clickResult = menuBoardManager.click(x, y);
        if(clickResult == MenuBoardManager.Button.PLAY_BTN){
            gsm.set(new PlayState(gsm));
            dispose();
        }else if(clickResult == MenuBoardManager.Button.QUIT_BTN){
            gsm.set(new MenuState(gsm));
            dispose();
        }
        menuBoardManager.releaseQuitBtn();
        menuBoardManager.releasePlayBtn();
    }

    /**
     * A simple touch listener
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            if(gameState == GameState.PAUSING){
                plane.setState(Plane.PlaneState.FLYING);
                gameState = GameState.PLAYING;
                plane.jump();
                titleManager.setTitle(TitleManager.Title.NOTHING);
            }else if(gameState == GameState.PLAYING){
                plane.jump();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(gameState == GameState.PLAYING){
            plane.update(dt);
            groundManager.update(dt);
            decorationManager.update(dt);
            backgroundManager.update(dt);
            blockManager.update(dt);

            if(blockManager.isPassed(incomingBlockIndex, plane.getPosition().x)){
                scoreManager.increase();
                scoreManager.update(dt);
                incomingBlockIndex = blockManager.getNewWatchingIndex(incomingBlockIndex);
            }
            if(testCollision()){
                titleManager.setTitle(TitleManager.Title.GAME_OVER);
                gameState = GameState.ENDING;
                //menuBoardManager.setScore(scoreManager.getScore());
                menuBoardManager.setAppearing(true);

                menuBoardManager.setScore(scoreManager.getScore());
            }


        }else if(gameState == GameState.PAUSING) {
            // before starting
            plane.update(dt);
            groundManager.update(dt);
            backgroundManager.update(dt);
            tipsManager.update(dt);
        }else {
            // gameover... nothing move
            menuBoardManager.update(dt);

        }

    }

    private boolean testCollision(){
        return blockManager.collide(plane.getBounds()) ||
                plane.getPosition().y < GroundManager.GROUND_HEIGHT*2;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        backgroundManager.draw(sb);
        plane.draw(sb);

        if(gameState == GameState.PLAYING){
            blockManager.draw(sb);
            groundManager.draw(sb);
            decorationManager.draw(sb);
            scoreManager.draw(sb);

        }else if(gameState == GameState.PAUSING){
            groundManager.draw(sb);
            tipsManager.draw(sb);
            titleManager.draw(sb);
        }else{
            blockManager.draw(sb);
            groundManager.draw(sb);
            decorationManager.draw(sb);
            menuBoardManager.draw(sb);
            titleManager.draw(sb);
        }

        sb.end();
    }

    /**
     * Cleaning all texture and sounds and calling managers dispose methods
     */
    @Override
    public void dispose() {
        plane.dispose();
        groundManager.dispose();
        backgroundManager.dispose();
        decorationManager.dispose();
        titleManager.dispose();
        tipsManager.dispose();
        blockManager.dispose();
        scoreManager.dispose();
        menuBoardManager.dispose();
        clickSound.dispose();
    }
}
