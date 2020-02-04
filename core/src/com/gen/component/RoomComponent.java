package com.gen.component;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.gen.object.GameObject;
import com.gen.object.GameObject3D;
import com.gen.scene.Scene;
import com.gen.world.World;

public class RoomComponent extends Component
{
	private Scene parentScene;
	private World world;
	private GameObject3D negativeXWall;
	private GameObject3D negativeZWall;
	private GameObject3D positiveXWall;
	private GameObject3D positiveZWall;
	private GameObject3D ceiling;
	private GameObject3D floor;
	private ModelBuilder modelBuilder = new ModelBuilder();

	private static final float wallDepth = 0.001f;

	public RoomComponent(GameObject owner, World world, float w, float h, float d, float centerX, float centerY, float centerZ, Material wallMaterial,
					 Material floorMaterial, Material ceilMaterial)
	{
		super(owner);
		this.parentScene = owner.getParentScene();
		this.world = world;
		// create each of our objects
		float xOffset = w / 2;
		float yOffset = h / 2;
		float zOffset = d / 2;

		// create each of our walls
		Model negXWallModel = modelBuilder.createBox(wallDepth, h, d, wallMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);
		Model negZWallModel = modelBuilder.createBox(w, h, wallDepth, wallMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);
		Model posXWallModel = modelBuilder.createBox(wallDepth, h, d, wallMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);
		Model posZWallModel = modelBuilder.createBox(w, h, wallDepth, wallMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);
		Model floorModel = modelBuilder.createBox(w, wallDepth, d, floorMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);
		Model ceilingModel = modelBuilder.createBox(w, wallDepth, d, ceilMaterial, VertexAttributes.Usage.Position |
			   VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal);

		// then creating each of our objects that contains these wall models
		negativeXWall = new GameObject3D(parentScene, "negativeXWall", new Matrix4().setToTranslation(centerX - xOffset, centerY + yOffset, centerZ), negXWallModel);
		negativeZWall = new GameObject3D(parentScene, "negativeZWall", new Matrix4().setToTranslation(centerX, centerY + yOffset, centerZ - zOffset), negZWallModel);
		positiveXWall = new GameObject3D(parentScene, "positiveXWall", new Matrix4().setToTranslation(centerX + xOffset, centerY + yOffset, centerZ), posXWallModel);
		positiveZWall = new GameObject3D(parentScene, "positiveZWall", new Matrix4().setToTranslation(centerX, centerY + yOffset, centerZ + zOffset), posZWallModel);
		floor = new GameObject3D(parentScene, "floor", new Matrix4().setToTranslation(centerX, centerY, centerZ), floorModel);
		ceiling = new GameObject3D(parentScene, "ceiling", new Matrix4().setToTranslation(centerX, centerY + h, centerZ), ceilingModel);

		// then we need to then add these objects to the parent scene
		world.addGameObject(negativeXWall);
		world.addGameObject(negativeZWall);
		world.addGameObject(positiveXWall);
		world.addGameObject(positiveZWall);
		world.addGameObject(floor);
		world.addGameObject(ceiling);
	}

	@Override
	public void execute(float delta)
	{

	}
}
