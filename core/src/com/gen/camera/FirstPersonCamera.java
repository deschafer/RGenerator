package com.gen.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class FirstPersonCamera extends PerspectiveCamera implements InputProcessor
{
	float cameraVerticalHeight = 3;
	private final Vector3 tmp = new Vector3();
	private static Vector3 cameraVelocity = new Vector3();
	private static final float velocity = 15.0f;
	private Rectangle cameraBounds = new Rectangle();
	private float yFloatingDistance = 3.0f;
	private Vector3 oldPosition = new Vector3();

	private boolean dirUp = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean pageUp = false;
	private boolean pageDown = false;
	private boolean north = false;
	private boolean east = false;
	private boolean west = false;
	private boolean south = false;

	public FirstPersonCamera(float fieldOfViewY, float viewportWidth, float viewportHeight)
	{
		super(fieldOfViewY, viewportWidth, viewportHeight);
	}

	public void updateCamera(float deltaTime)
	{
		oldPosition.set(this.position);

		if (north)
		{
			tmp.set(direction).nor().scl(deltaTime * velocity);
			position.add(tmp);
		}
		if (south)
		{
			tmp.set(direction).nor().scl(-deltaTime * velocity);
			position.add(tmp);
		}
		if (west)
		{
			tmp.set(direction).crs(this.up).nor().scl(-deltaTime * velocity);
			position.add(tmp);
		}
		if (east)
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
		if (dirUp || pageUp)
		{
			rotate(direction.cpy().crs(Vector3.Y), 1);
		}

		if (down || pageDown)
		{
			rotate(direction.cpy().crs(Vector3.Y), -1);
		}

		float cameraZ = cameraBounds.y;
		float cameraX = cameraBounds.x;

		if (position.z > cameraZ + cameraBounds.height - 2)
		{
			position.z = cameraZ + cameraBounds.height - 2;
		} else if (position.z < cameraZ + 2)
		{
			position.z = cameraZ + 2;
		}

		if (position.x > cameraX + cameraBounds.width - 2)
		{
			position.x = cameraX + cameraBounds.width - 2;
		} else if (position.x < cameraX + 2)
		{
			position.x = cameraX + 2;
		}

		position.y = yFloatingDistance;

		// get the velocity of the camera
		cameraVelocity.x = position.x - oldPosition.x;
		cameraVelocity.y = position.y - oldPosition.y;
		cameraVelocity.z = position.z - oldPosition.z;
		update(true);
	}

	public void setCameraBounds(float centerX, float centerZ, float width, float height, float yFloatingDistance)
	{
		cameraBounds.set(centerX, centerZ, width, height);
		this.yFloatingDistance = yFloatingDistance;
	}

	public Vector3 getCameraVelocity()
	{
		return cameraVelocity;
	}

	public void clearInput()
	{
		dirUp = false;
		down = false;
		left = false;
		right = false;
		pageUp = false;
		pageDown = false;
		north = false;
		east = false;
		west = false;
		south = false;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if (keycode == Input.Keys.UP && !dirUp)
		{
			dirUp = true;
		}
		if (keycode == Input.Keys.DOWN && !down)
		{
			down = true;
		}
		if (keycode == Input.Keys.LEFT && !left)
		{
			left = true;
		}
		if (keycode == Input.Keys.RIGHT && !right)
		{
			right = true;
		}
		if (keycode == Input.Keys.PAGE_UP && !pageUp)
		{
			pageUp = true;
		}
		if (keycode == Input.Keys.PAGE_DOWN && !pageDown)
		{
			pageDown = true;
		}
		if (keycode == Input.Keys.W && !north)
		{
			north = true;
		}
		if (keycode == Input.Keys.A && !west)
		{
			west = true;
	}
		if (keycode == Input.Keys.S && !south)
		{
			south = true;
		}
		if (keycode == Input.Keys.D && !east)
		{
			east = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == Input.Keys.UP && dirUp)
		{
			dirUp = false;
		}
		if (keycode == Input.Keys.DOWN && down)
		{
			down = false;
		}
		if (keycode == Input.Keys.LEFT && left)
		{
			left = false;
		}
		if (keycode == Input.Keys.RIGHT && right)
		{
			right = false;
		}
		if (keycode == Input.Keys.PAGE_UP && pageUp)
		{
			pageUp = false;
		}
		if (keycode == Input.Keys.PAGE_DOWN && pageDown)
		{
			pageDown = false;
		}
		if (keycode == Input.Keys.W && north)
		{
			north = false;
		}
		if (keycode == Input.Keys.A && west)
		{
			west = false;
		}
		if (keycode == Input.Keys.S && south)
		{
			south = false;
		}
		if (keycode == Input.Keys.D && east)
		{
			east = false;
		}

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
