package com.gen.scene;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gen.ui.TextBoxScreen;
import com.gen.world.GameWorld;

public class GameScene extends Scene
{

	private GameWorld gameWorld;
	private TextBoxScreen uiStage = new TextBoxScreen();
	private boolean focusOnUI = false;

	@Override
	public void initialize()
	{
		gameWorld = new GameWorld(this);
	}

	@Override
	public void update(float dt)
	{
		gameWorld.update(dt);
		uiStage.act(dt);

		gameWorld.render(dt);
		uiStage.draw();
	}

	@Override
	public boolean keyTyped(char character)
	{
		if (focusOnUI)
		{
			uiStage.keyTyped(character);
		}
		else
		{
			gameWorld.keyTyped(character);
		}

		return super.keyTyped(character);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		// look for the enter key
		if (keycode == Input.Keys.ENTER)
		{
			// then we move into the ui state
			focusOnUI = true;
			gameWorld.clearInput();
			uiStage.Enter();
		}
		if (keycode == Input.Keys.ESCAPE)
		{
			focusOnUI = false;
			uiStage.Clear();
		}

		if (focusOnUI)
		{
			uiStage.keyDown(keycode);
		}
		else
		{
			gameWorld.keyDown(keycode);
		}

		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (focusOnUI)
		{
			uiStage.keyUp(keycode);
		}
		else
		{
			gameWorld.keyUp(keycode);
		}

		return super.keyUp(keycode);
	}
}
