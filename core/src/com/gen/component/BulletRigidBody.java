package com.gen.component;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.gen.object.GameObject;

public class BulletRigidBody extends Component
{
	private final Matrix4 transform;
	btMotionState motionState;
	public btRigidBody.btRigidBodyConstructionInfo bodyConstructionInfo;
	public btCollisionObject rigidBody;

	public BulletRigidBody(GameObject owner, Model model, final Matrix4 transform)
	{
		super(owner);
		this.transform = transform;

		final BoundingBox boundingBox = new BoundingBox();
		model.calculateBoundingBox(boundingBox);
		Vector3 tmpV = new Vector3();
		btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f,
			   boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));
		bodyConstructionInfo = new btRigidBody.btRigidBodyConstructionInfo(0,
			   null, col, Vector3.Zero);

	}

	public Matrix4 getWorldTransform()
	{
		Matrix4 matrix = new Matrix4();
		motionState.getWorldTransform(matrix);
		return matrix;
	}
	public void setWorldTransform(Matrix4 matrix)
	{
		motionState.setWorldTransform(matrix);
	}

}
