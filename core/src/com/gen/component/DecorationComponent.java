package com.gen.component;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gen.object.GameObject;
import com.gen.object.GameObject3D;
import com.gen.world.World;

public class DecorationComponent extends Component
{
	public DecorationComponent(GameObject owner, World world, Vector3 a, Vector3 b, Vector3 c, Vector3 d, Material material) throws Exception
	{
		super(owner);

		ModelBuilder modelBuilder = new ModelBuilder();
		float width = 0;
		float height = 0;
		float depth = 0;
		Vector3 vertexA = new Vector3(a);
		Vector3 vertexB = new Vector3(b);
		Vector3 vertexC = new Vector3(c);
		Vector3 vertexD = new Vector3(d);

		// within the x plane
		if (vertexA.x == vertexB.x && vertexB.x == vertexC.x &&  vertexC.x == vertexD.x)
		{
			width = 0.001f;
			height = Math.abs(vertexA.y - vertexB.y);
			depth = Math.abs(vertexA.z - vertexB.z);
		}
		// within the y plane
		else if (vertexA.y == vertexB.y && vertexB.y == vertexC.y &&  vertexC.y == vertexD.y)
		{
			width = Math.abs(vertexA.x - vertexB.x);
			height = 0.001f;
			depth = Math.abs(vertexA.z - vertexC.z);
		}
		// within the z plane
		else if (vertexA.z == vertexB.z && vertexB.z == vertexC.z &&  vertexC.z == vertexD.z)
		{
			width = Math.abs(vertexA.x - vertexB.x);
			height = Math.abs(vertexA.y - vertexB.y);
			depth = 0.001f;
		}
		else
		{
			throw new Exception("Quad is not defined as a Quad");
		}



		Vector3 center = new Vector3();
		//center.set()

		Model model = modelBuilder.createBox(width, height, depth, material, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		// create an object for this model
		GameObject3D object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexA.x + width / 2, vertexA.y  + height / 2, vertexA.z + depth / 2), model);
		world.addGameObject(object3D);

		// to test this, lets create more objects, one at each corner
		model = modelBuilder.createBox(0.1f, 0.1f, 0.1f, material, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexA.x , vertexA.y  , vertexA.z), model);
		world.addGameObject(object3D);

		// to test this, lets create more objects, one at each corner
		model = modelBuilder.createBox(0.1f, 0.1f, 0.1f, material, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexB.x , vertexB.y  , vertexB.z), model);
		world.addGameObject(object3D);

		// to test this, lets create more objects, one at each corner
		model = modelBuilder.createBox(0.1f, 0.1f, 0.1f, material, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexC.x , vertexC.y  , vertexC.z), model);
		world.addGameObject(object3D);

		// to test this, lets create more objects, one at each corner
		model = modelBuilder.createBox(0.1f, 0.1f, 0.1f, material, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexD.x , vertexD.y  , vertexD.z), model);
		world.addGameObject(object3D);

	}

	@Override
	public void execute(float delta)
	{

	}

	public void determineCorners(Vector3 vertexA, Vector3 vertexB, Vector3 vertexC, Vector3 vertexD)
	{
		if (vertexA.y == vertexB.y)
		{
			if (vertexA.x > vertexA.x)
			{

			}
		}
		else if (vertexA.y == vertexC.y)
		{

		}
		else if (vertexA.y == vertexD.y)
		{

		}
	}
}
