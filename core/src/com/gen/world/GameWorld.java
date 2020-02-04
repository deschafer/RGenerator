package com.gen.world;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

public class GameWorld extends World
{


	@Override
	protected void initEnvironment() {
		super.initEnvironment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
			   0.3f, 0.3f, 0.3f, 1f));
		environment.set(new
			   ColorAttribute(ColorAttribute.AmbientLight,
			   0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -
			   0, -0.8f, -0.0f));
	}

}
