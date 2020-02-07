package com.gen.parsing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.gen.boids.Boid;
import com.gen.camera.FirstPersonCamera;
import com.gen.component.DecorationComponent;
import com.gen.component.RoomComponent;
import com.gen.game.RAssetManager;
import com.gen.object.GameObject;
import com.gen.world.World;
import com.google.gson.*;

import java.util.ArrayList;


public class RoomParser
{
	private Gson gson = new Gson();

	public RoomRecord parse(String filename)
	{
		String readInData;
		RoomRecord roomRecord = new RoomRecord();
		// get a string from the filename
		try
		{
			readInData = Gdx.files.internal(filename).readString();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}

		// then we can parse the read in data
		// get the json object that we need
		JsonParser parser = new JsonParser();
		JsonArray rootObject = parser.parse(readInData).getAsJsonArray();
		JsonElement element = rootObject.get(0);
		JsonObject object = element.getAsJsonObject();

		ArrayList<DecorationRecord> decorationRecords = new ArrayList<>();

		// begin gathering data
		roomRecord.roomWidth = object.get("w").getAsFloat();
		roomRecord.roomHeight = object.get("h").getAsFloat();
		roomRecord.roomDepth = object.get("l").getAsFloat();

		roomRecord.roomX = object.get("x").getAsFloat();
		roomRecord.roomY = object.get("y").getAsFloat();
		roomRecord.roomZ = object.get("z").getAsFloat();
		roomRecord.roomTexture = object.get("texture").toString().replace("\"", "");

		// then get the floor json element
		JsonObject floor = object.getAsJsonObject("floor");
		roomRecord.floorClass = floor.get("class").toString();
		roomRecord.floorTexture = floor.get("texture").toString().replace("\"", "");

		// then get the ceiling json element
		JsonObject ceiling = object.getAsJsonObject("ceiling");
		roomRecord.ceilingClass = ceiling.get("class").toString();
		roomRecord.ceilingTexture = ceiling.get("texture").toString().replace("\"", "");

		// then get the decorations array
		JsonArray decorationArray = object.getAsJsonArray("decorations");
		for (JsonElement currentElement : decorationArray)
		{
			// get the decoration and create a record for it
			JsonObject decoration = currentElement.getAsJsonObject();
			DecorationRecord record = new DecorationRecord();
			record.texture = decoration.get("texture").toString().replace("\"", "");

			// getting each of the vertices
			try
			{
				JsonArray vertices = decoration.get("coords").getAsJsonArray();
				record.topRight.set(vertices.get(0).getAsFloat(), vertices.get(1).getAsFloat(), vertices.get(2).getAsFloat());
				record.bottomRight.set(vertices.get(3).getAsFloat(), vertices.get(4).getAsFloat(), vertices.get(5).getAsFloat());
				record.topLeft.set(vertices.get(6).getAsFloat(), vertices.get(7).getAsFloat(), vertices.get(8).getAsFloat());
				record.bottomLeft.set(vertices.get(9).getAsFloat(), vertices.get(10).getAsFloat(), vertices.get(11).getAsFloat());
			} catch (NullPointerException e)
			{
				System.out.println(filename + " format incorrect " + e.getMessage());
				return null;
			}
			decorationRecords.add(record);
		}

		roomRecord.decorationRecords = decorationRecords;

		return roomRecord;
	}

	public void parseAndGeneratorRoom(World world, String filename)
	{
		RoomRecord record = parse(filename);

		// create new texture materials for the textures presented
		Material wallMaterial = new Material(TextureAttribute.createDiffuse((Texture) RAssetManager.manager.get(record.roomTexture)));
		Material floorMaterial = new Material(TextureAttribute.createDiffuse((Texture) RAssetManager.manager.get(record.floorTexture)));
		Material ceilingMaterial = new Material(TextureAttribute.createDiffuse((Texture) RAssetManager.manager.get(record.ceilingTexture)));

		// create a new room in the world class
		GameObject room = new GameObject(world.getParentScene(), "ParsedRoom");
		RoomComponent roomComponent = new RoomComponent(room, world, record.roomWidth, record.roomHeight,
			   record.roomDepth, record.roomX, record.roomY, record.roomZ, wallMaterial, floorMaterial, ceilingMaterial);
		room.addComponent(roomComponent);

		world.addGameObject(room);

		for (DecorationRecord decRecord : record.decorationRecords)
		{
			Material decorationMaterial = new Material(TextureAttribute.createDiffuse((Texture) RAssetManager.manager.get(decRecord.texture)));

			DecorationComponent decorationComponent = new DecorationComponent(room, world, new Vector3().set(decRecord.bottomLeft),
				   new Vector3().set(decRecord.bottomRight), new Vector3().set(decRecord.topLeft), new Vector3().set(decRecord.topRight),
				   decorationMaterial);
			room.addComponent(decorationComponent);
		}

		FirstPersonCamera camera = (FirstPersonCamera) world.getCamera();
		Vector3 center = roomComponent.getCenter();
		camera.position.set(center.x, center.y + 3, center.z);
		camera.setCameraBounds(center.x - record.roomWidth / 2, center.z - record.roomDepth / 2,
			   record.roomWidth, record.roomDepth, center.y + 3.0f);
		Boid.worldMin.add(center);
		Boid.worldMax.add(center);
		Boid.xBoundsFromOrigin = record.roomWidth / 2;
		Boid.zBoundsFromOrigin = record.roomDepth / 2;

		Boid.setWorldBounds(center, record.roomWidth / 2, record.roomDepth / 2);
	}
}
