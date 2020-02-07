package com.gen.boids;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.gen.camera.FirstPersonCamera;
import com.gen.world.World;

import java.util.ArrayList;

public class Flock {

	private ArrayList<Boid> boids;
	private ModelBuilder modelBuilder = new ModelBuilder();
	private Vector3 averageVelocity = new Vector3();
	private float minBoidDistance = 0.5f;
	private float updateTimer = 0;
	private float updateTime = 0.25f;
	private Vector3 center = new Vector3();
	private Vector3 inertia = new Vector3();
	private Vector3 pull = new Vector3();
	private Vector3 auxVel = new Vector3();
	private float inertiaFactor = 0.1f;
	private float maxVelocity = 5.0f;
	private FirstPersonCamera camera;

	public Flock(int numBoids, World world) {
		Boid boid = null;
		boids = new ArrayList<>();
		for (int num = 0; num < numBoids; num++) {

			Model model = modelBuilder.createCone(1,1,1,15, new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				   VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

			boid = new Boid(new Vector3(0,0,0), model, world);
			boid.randomPos();
			boids.add(boid);
			world.addGameObject(boid.getGameObject());
		}
		camera = (FirstPersonCamera)world.getCamera();
	}

	public void updateBoids() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		updateTimer += Gdx.graphics.getDeltaTime();

		center.set(0, 0, 0);
		inertia.set(0, 0, 0);

		averageVelocity.x = 0;
		averageVelocity.y = 0;
		averageVelocity.z = 0;
		// get the average velocity
		for (Boid boid : boids) {
			averageVelocity.x += boid.velocity.x;
			averageVelocity.y += boid.velocity.y;
			averageVelocity.z += boid.velocity.z;
		}

		// also account for the players position
		//Vector3 cameraVelocity = CameraManipulator.getCameraPosition();
		averageVelocity.x += 150 * camera.getCameraVelocity().x;
		averageVelocity.y += 150 * camera.getCameraVelocity().y;
		averageVelocity.z += 150 * camera.getCameraVelocity().z;

		averageVelocity.x /= boids.size() + 1;
		averageVelocity.y /= boids.size() + 1;
		averageVelocity.z /= boids.size() + 1;

		for (Boid thisBoid : boids) {
			center.add(thisBoid.position);
			inertia.add(thisBoid.velocity);
		}

		center.x /= boids.size();
		center.y /= boids.size();
		center.z /= boids.size();
		inertia.x /= boids.size();
		inertia.y /= boids.size();
		inertia.z /= boids.size();
		inertia.x /= inertiaFactor * deltaTime;
		inertia.y /= inertiaFactor * deltaTime;
		inertia.z /= inertiaFactor * deltaTime;

		for (Boid boid : boids) {
			if (updateTimer >= updateTime) {
				// set the average velocity
				pull.set(center);
				pull.sub(boid.position);
				boid.velocity.add(pull);

				boid.velocity.add(inertia);
				boid.velocity.x = averageVelocity.x + (((float) Math.random() > 0.5f) ? -1 : 1) * 0.1f * (float) Math.random();
				boid.velocity.y = averageVelocity.y + (((float) Math.random() > 0.5f) ? -1 : 1) * 0.1f * (float) Math.random();
				boid.velocity.z = averageVelocity.z + (((float) Math.random() > 0.5f) ? -1 : 1) * 0.1f * (float) Math.random();

				if (boid.velocity.len() > maxVelocity)
					boid.velocity.scl(maxVelocity / boid.velocity.len());

				// Calculate new position
				auxVel.set(boid.velocity);
				auxVel.scl(deltaTime);
				boid.translate(auxVel);
			}

			// if we are too close to any of the other boids
			for (Boid otherBoid : boids) {
				if (otherBoid != boid) {
					if (Math.abs(otherBoid.position.x - boid.position.x) <= minBoidDistance &&
						   Math.abs(otherBoid.position.y - boid.position.y) <= minBoidDistance &&
						   Math.abs(otherBoid.position.z - boid.position.z) <= minBoidDistance) {
						boid.velocity.x = -boid.velocity.x;
						boid.velocity.y = -boid.velocity.y;
						boid.velocity.z = -boid.velocity.z;
					}
				}
			}
			boid.update();
			boid.worldConstraints();
		}

		if (updateTimer >= updateTime) {
			updateTimer = 0;
		}
	}
}
