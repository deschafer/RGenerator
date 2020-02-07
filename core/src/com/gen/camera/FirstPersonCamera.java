package com.gen.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class FirstPersonCamera extends PerspectiveCamera
{
	float cameraVerticalHeight = 3;
	private final Vector3 tmp = new Vector3();
	private static Vector3 cameraVelocity = new Vector3();
	private static final float velocity = 5.0f;
	private Rectangle cameraBounds = new Rectangle();
	private float yFloatingDistance = 3.0f;
	private Vector3 oldPosition = new Vector3();

	public FirstPersonCamera(float fieldOfViewY, float viewportWidth, float viewportHeight)
	{
		super(fieldOfViewY, viewportWidth, viewportHeight);
	}

	public void updateCamera(float deltaTime)
	{
		// updates the position of the camera
		// poll for user input
		boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean down = Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean pageUp = Gdx.input.isKeyPressed(Input.Keys.PAGE_UP);
		boolean pageDown = Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN);

		 oldPosition.set(this.position);

		if (Gdx.input.isKeyPressed(Input.Keys.W))
		{
			tmp.set(direction).nor().scl(deltaTime * velocity);
			position.add(tmp);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S))
		{
			tmp.set(direction).nor().scl(-deltaTime * velocity);
			position.add(tmp);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A))
		{
			tmp.set(direction).crs(this.up).nor().scl(-deltaTime * velocity);
			position.add(tmp);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D))
		{
			tmp.set(direction).crs(this.up).nor().scl(deltaTime * velocity);
			position.add(tmp);
		}
		if (left)
		{
			rotate(Vector3.Y, 1);
			update();
		}
		if (right)
		{
			rotate(Vector3.Y, -1);
			update();
		}
		// looking direction
		if (up || pageUp)
		{
			rotate(direction.cpy().crs(Vector3.Y), -1);
		}

		if (down || pageDown)
		{
			rotate(direction.cpy().crs(Vector3.Y), 1);
		}

		// setting walls so the player cannot leave the room
		if (!cameraBounds.contains(position.x, position.z))
		{
			if (position.z > cameraBounds.y + cameraBounds.height - 1)
			{
				position.z = cameraBounds.y + cameraBounds.height;
			} else if (position.z < cameraBounds.y + 5)
			{
				position.z = cameraBounds.y + 5;
			}

			if (position.x > cameraBounds.x + cameraBounds.width - 1)
			{
				position.x = cameraBounds.x + cameraBounds.width;
			} else if (position.x < cameraBounds.x + 1)
			{
				position.x = cameraBounds.x;
			}
		}

		position.y = yFloatingDistance;

		// get the velocity of the camera
		cameraVelocity.x = position.x - oldPosition.x;
		cameraVelocity.y = position.y - oldPosition.y;
		cameraVelocity.z = position.z - oldPosition.z;
		update(true);
	}

	public void setCameraBounds(float centerX, float centerY, float width, float height, float yFloatingDistance)
	{
		cameraBounds.set(centerX, centerY, width, height);
		this.yFloatingDistance = yFloatingDistance;
	}

	public Vector3 getCameraVelocity()
	{
		return cameraVelocity;
	}
}
