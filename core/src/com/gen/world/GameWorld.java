package com.gen.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.gen.camera.FirstPersonCamera;
import com.gen.component.RoomComponent;
import com.gen.game.RGenerator;
import com.gen.object.GameObject;
import com.gen.scene.Scene;

public class GameWorld extends World
{

	private FirstPersonCamera camera;

	public GameWorld(Scene scene)
	{
		super(scene);
		Material texturedWallMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE));
		GameObject newObject = new GameObject(scene, "Room");
		newObject.addComponent(new RoomComponent(newObject, this, 10,10,10,0,0,
			   0,texturedWallMaterial,texturedWallMaterial,texturedWallMaterial));

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
		// TODO: replace this camera with our first person camera
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
	}
}
