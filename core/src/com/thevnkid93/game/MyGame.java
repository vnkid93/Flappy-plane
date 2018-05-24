package com.thevnkid93.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thevnkid93.game.states.MenuState;

public class MyGame extends ApplicationAdapter {
	public  static final int WIDTH = 480;
	public  static final int HEIGHT = 800;
	public  static final String GAME_TITLE = "Flappy plane";

	private GameStateManager gsm;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1); // clearing
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // wiping screen before rendering
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}


}
