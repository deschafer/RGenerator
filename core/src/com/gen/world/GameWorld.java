package com.gen.world;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.gen.boids.Boid;
import com.gen.boids.Flock;
import com.gen.camera.FirstPersonCamera;
import com.gen.game.RGenerator;
import com.gen.parsing.RoomParser;
import com.gen.scene.Scene;

public class GameWorld extends World implements InputProcessor
{
	private FirstPersonCamera camera;
	private Flock flock;

	public GameWorld(Scene scene)
	{
		super(scene);

		RoomParser parser = new RoomParser();
		parser.parseAndGeneratorRoom(this,"room.json");

		flock = new Flock(512, this);
	}

	@Override
	protected void initEnvironment() {
		super.initEnvironment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
			   0.3f, 0.3f, 0.3f, 1f));
		environment.set(new
			   ColorAttribute(ColorAttribute.AmbientLight,
			   0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -
			   0, -0.8f, -0.0f));
	}

	@Override
	protected void initCamera()
	{
		perspectiveCamera = camera = new FirstPersonCamera(80,
			   RGenerator.getWidth(), RGenerator.getHeight());
		perspectiveCamera.position.set(0, 0, 0);
		perspectiveCamera.lookAt(10f, 0f, 0f);
		perspectiveCamera.near = 1f;
		perspectiveCamera.far = 300f;
		perspectiveCamera.update();
	}

	@Override
	public void update(float delta)
	{
		super.update(delta);
		camera.updateCamera(delta);
		flock.updateBoids();
	}

	public void clearInput()
	{
		camera.clearInput();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		return camera.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode)
	{
		camera.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
