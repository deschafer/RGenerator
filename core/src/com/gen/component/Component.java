package com.gen.component;

import com.gen.object.GameObject;

public abstract class Component {

	protected GameObject parentObject;

	public Component(GameObject parentObject)
	{
		this.parentObject = parentObject;
	}

	public abstract void execute(float delta);
}
