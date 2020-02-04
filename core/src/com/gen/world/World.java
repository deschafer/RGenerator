package com.gen.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gen.game.RGenerator;
import com.gen.object.GameObject;

import java.util.ArrayList;

public class World
{
	protected ModelBatch modelBatch;
	protected Environment environment;
	protected PerspectiveCamera perspectiveCamera;
	private ArrayList<GameObject> gameObjects;

	private final static int FOV = 80;

	public World()
	{
		gameObjects = new ArrayList<>();

		initCamera();
		initEnvironment();
		initModelBatch();
	}

	protected void initCamera()
	{
		// TODO: replace this camera with our first person camera
		perspectiveCamera = new PerspectiveCamera(FOV,
			   RGenerator.getWidth(), RGenerator.getHeight());
		perspectiveCamera.position.set(0, 0, 0);
		perspectiveCamera.lookAt(10f, 0f, 0f);
		perspectiveCamera.near = 1f;
		perspectiveCamera.far = 300f;
		perspectiveCamera.update();
	}

	protected void initEnvironment()
	{
		environment = new Environment();
	}

	protected void initModelBatch()
	{
		modelBatch = new ModelBatch();
	}

	public void update(float delta)
	{
		for (GameObject object : gameObjects)
		{
			// object->update(delta);
		}
	}

	public void render(float delta)
	{
		modelBatch.begin(perspectiveCamera);
		for (GameObject object : gameObjects)
		{
			// get a render component and draw it
		}
		modelBatch.end();
	}
}
