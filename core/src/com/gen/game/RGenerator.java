package com.gen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gen.networking.Client;
import com.gen.scene.GameScene;
import com.gen.scene.Scene;

public class RGenerator extends AbstractGame
{

	private static int width;
	private static int height;

	private Scene gameScene;

	// constructor and create methods
	public RGenerator(int width, int height)
	{
		super();
		RGenerator.width = width;
		RGenerator.height = height;
	}

	@Override
	public void create()
	{
		// load all data here
		RAssetManager.manager.load(RAssetManager.woodOne, Texture.class);
		RAssetManager.manager.load(RAssetManager.woodTwo, Texture.class);
		RAssetManager.manager.load(RAssetManager.plastic, Texture.class);
		RAssetManager.manager.load(RAssetManager.concrete, Texture.class);
		RAssetManager.manager.load(RAssetManager.defaultUISkin, Skin.class);
		RAssetManager.manager.load(RAssetManager.chatBox, Texture.class);
		RAssetManager.manager.load(RAssetManager.cursor, Texture.class);
		RAssetManager.manager.finishLoading();

		super.create();

		// set the screen
		setActiveScreen(gameScene = new GameScene());

		// create the client so we can communicate with the server
		Client client = new Client();
		new Thread(client).start();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render();
	}

	public static int getWidth() { return width; }
	public static int getHeight() { return height; }
}
