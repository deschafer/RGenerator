package com.gen.object;

import com.gen.component.Component;
import com.gen.scene.Scene;

import java.util.ArrayList;

public class GameObject
{

	private ArrayList<Component> components;
	private Scene parentScene;
	private String id;

	public GameObject(Scene parent, String id)
	{
		parentScene = parent;
		components = new ArrayList<>();
		this.id = id;
	}

	public void update(float deltaTime)
	{
		for (Component component : components)
		{
			component.execute(deltaTime);
		}
	}

	public void addComponent(Component component)
	{
		components.add(component);
	}

	public void removeComponent(Component component)
	{
		components.remove(component);
	}

	public Component getComponent (Class c)
	{
		Component result = null;
		for (Component component : components)
		{
			if (component != null && component.getClass() == c)
			{
				result = component;
				break;
			}
		}
		return result;
	}

	public Scene getParentScene()
	{
		return parentScene;
	}

	public String getId()
	{
		return id;
	}
}
