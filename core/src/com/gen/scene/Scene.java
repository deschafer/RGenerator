package com.gen.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class Scene implements Screen, InputProcessor
{
	protected Table uiTable;
	protected Stage uiStage;

	public Scene()
	{
		uiStage = new Stage();
		uiTable = new Table();
	}

	public abstract void initialize();

	public abstract void update(float dt);

	// Screen methods
	@Override
	public void show()
	{
		InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
		im.addProcessor(this);
		im.addProcessor(uiStage);
	}
	@Override
	public void hide()
	{
		InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
		im.removeProcessor(this);
		im.removeProcessor(uiStage);
	}

	@Override
	public void render(float delta)
	{
		uiStage.act(delta);
		// update our world class

		update(delta);

		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) { }
	@Override
	public void pause() { }
	@Override
	public void resume() { }
	@Override
	public void dispose() { }

	// InputProcessor methods
	public boolean isTouchDownEvent(Event e)
	{
		return (e instanceof InputEvent) &&
			   ((InputEvent)e).getType().equals(InputEvent.Type.touchDown);
	}
	@Override
	public boolean keyDown(int keycode)
	{ return false; }
	@Override
	public boolean keyUp(int keycode)
	{ return false; }
	@Override
	public boolean keyTyped(char character)
	{ return false; }
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{ return false; }
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{ return false; }
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{ return false; }
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{ return false; }
	@Override
	public boolean scrolled(int amount)
	{ return false; }
}
