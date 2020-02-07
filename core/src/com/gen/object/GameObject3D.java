package com.gen.object;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.gen.scene.Scene;

// a gameobject with a 3d model associated with it
public class GameObject3D extends GameObject
{
	private ModelInstance instance;
	private boolean visible = true;

	public GameObject3D(Scene parent, String id, Matrix4 transform, Model model)
	{
		super(parent, id);
		instance = new ModelInstance(model, transform);
	}

	public void render(ModelBatch batch)
	{
		if (visible)
		{
			batch.render(instance);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}

	public void setPositon(Matrix4 position)
	{
		instance.transform = position;
	}

	public Matrix4 getPosition()
	{
		return instance.transform;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public Matrix4 getTransform()
	{
		return instance.transform;
	}
}
