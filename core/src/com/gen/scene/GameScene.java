package com.gen.scene;

import com.gen.world.GameWorld;

public class GameScene extends Scene
{

	private GameWorld gameWorld;

	@Override
	public void initialize()
	{
		gameWorld = new GameWorld();
	}

	@Override
	public void update(float dt)
	{
		gameWorld.update(dt);

		gameWorld.render(dt);
	}
}
