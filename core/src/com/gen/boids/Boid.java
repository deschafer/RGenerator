package com.gen.boids;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gen.object.GameObject3D;
import com.gen.world.World;

public class Boid extends Entity {

	Vector3 position;
	Vector3 velocity;
	public static float xBoundsFromOrigin = 35.0f;
	public static float zBoundsFromOrigin = 35.0f;
	private static float yMinPosition = 7.0f;
	private static float yMaxPosition = 35.0f;
	public static Vector3 worldMin = new Vector3(-1 * xBoundsFromOrigin, yMinPosition, -1 * xBoundsFromOrigin);
	public static Vector3 worldMax = new Vector3(xBoundsFromOrigin, yMaxPosition, xBoundsFromOrigin);
	private static float addedVelocity = 0.2f;
	private GameObject3D object3D;
	private Vector3 auxVector = new Vector3();

	public Boid(Vector3 position, Model model, World world)
	{
		object3D = new GameObject3D(world.getParentScene(), "Boid",
			   new Matrix4().setToTranslation(position), model);

		this.position = position;
		velocity = new Vector3(0,0,0);
	}

	public static void setWorldBounds(Vector3 center, float xBoundsFromOrigin, float zBoundsFromOrigin)
	{
		worldMin = new Vector3(-1 * xBoundsFromOrigin, yMinPosition, -1 * zBoundsFromOrigin).add(center);
		worldMax = new Vector3(xBoundsFromOrigin, yMaxPosition, zBoundsFromOrigin).add(center);
	}

	public void worldConstraints() {
		if (position.x > worldMax.x)
			velocity.x += -addedVelocity;
		if (position.x < worldMin.x)
			velocity.x += addedVelocity;
		if (position.y > worldMax.y)
			velocity.y += -addedVelocity;
		if (position.y < worldMin.y)
			velocity.y += addedVelocity;
		if (position.z > worldMax.z)
			velocity.z += -addedVelocity;
		if (position.z < worldMin.z)
			velocity.z += addedVelocity;
	}

	public void randomPos() {
		position.x = worldMin.x + ((worldMax.x - worldMin.x)*(float)Math.random());
		position.y = worldMin.y + ((worldMax.y - worldMin.y)*(float)Math.random());
		position.z = worldMin.z + ((worldMax.z - worldMin.z)*(float)Math.random());
		velocity.x = 0.2f * (float)Math.random();
		velocity.y = 0.2f * (float)Math.random();
		velocity.z = 0.2f * (float)Math.random();

		Vector3 oldPosition = object3D.getTransform().getTranslation(new Vector3());
		Vector3 translation = new Vector3();
		translation.x = position.x - oldPosition.x;
		translation.y = position.y - oldPosition.y;
		translation.z = position.z - oldPosition.z;
		object3D.getTransform().translate(translation);
	}

	public void translate(Vector3 translation) {
		object3D.getTransform().translate(translation);
		position = object3D.getTransform().getTranslation(position);
	}

	public void setPosition(Vector3 position) {
		this.position = position;
		object3D.getTransform().setToTranslation(position.x, position.y, position.z);
	}

	public void update()
	{
		auxVector.set(position);
		// setting walls so the player cannot leave the room
		if (position.x >= worldMax.x)
		{
			velocity.x = -velocity.x;
			auxVector.x = position.x - 0.1f;
		}
		else if (position.x <= worldMin.x)
		{
			velocity.x = -velocity.x;
			auxVector.x = position.x + 0.1f;
		}

		if (position.z >= worldMax.z)
		{
			velocity.z = -velocity.z;
			auxVector.z = position.z - 0.1f;
		}
		else if (position.z <= worldMin.z)
		{
			velocity.z = -velocity.z;
			auxVector.z = position.z + 0.1f;
		}

		if (position.y >= worldMax.y)
		{
			velocity.y = -velocity.y;
			auxVector.y =position.y - 0.1f;
		}
		else if (position.y <= worldMin.y)
		{
			velocity.y = -velocity.y;
			auxVector.y =position.y + 0.1f;
		}
		setPosition(auxVector);

		translate(velocity);
	}

	public GameObject3D getGameObject()
	{
		return object3D;
	}
}
