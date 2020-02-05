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

	public DecorationComponent(GameObject owner, World world, Vector3 vertexA, Vector3 vertexB, Vector3 vertexC, Vector3 vertexD, Material material)
	{
		super(owner);

		ModelBuilder modelBuilder = new ModelBuilder();
		Vector3 normal = new Vector3().set(vertexA);
		normal = normal.crs(vertexB).nor();
		// create a new plane at this location
		Model model = modelBuilder.createRect(vertexA.x, vertexA.y, vertexA.z, vertexB.x, vertexB.y, vertexB.z,
			   vertexC.x, vertexC.y, vertexB.z, vertexD.x, vertexD.y, vertexD.z, normal.x, normal.y, normal.z, material,
			   VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		// create an object for this model
		GameObject3D object3D = new GameObject3D(owner.getParentScene(), "Decoration", new Matrix4().setToTranslation(vertexA.x, vertexA.y, vertexA.z), model);

		world.addGameObject(object3D);
	}

	@Override
	public void execute(float delta)
	{

	}
}
