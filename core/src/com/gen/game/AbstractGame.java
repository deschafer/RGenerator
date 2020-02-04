package com.gen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.gen.scene.Scene;

// singleton class structure
public abstract class AbstractGame extends Game
{
	private static AbstractGame game;

	public AbstractGame()
	{
		game = this;
	}

	public void create()
	{
		// prepare for multiple classes/stages/actors to receive discrete input
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor( im );
	}

	public static void setActiveScreen(Scene s)
	{
		s.initialize();
		game.setScreen(s);
	}
}