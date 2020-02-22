package com.gen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gen.object.SimpleActor;

import java.util.ArrayList;


public class ChatLog extends Group
{

	// this class has a scroll pane
	private Table scrollPaneTable = new Table();
	private ScrollPane scrollPane;
	private Label.LabelStyle addedLabelStyle;
	private ArrayList<Label> labels = new ArrayList<>();
	private Vector2 labelsPosition = new Vector2();
	private SimpleActor background;
	private int maxTextLength = 45;

	boolean visible = false;

	float timer = 15.0f;
	float time = 15.0f;

	public ChatLog(float x, float y, float w, float h)
	{
		setPosition(x,y);
		setWidth(w);
		setHeight(h);

		CreateStyles();

		visible = true;

		addAction(Actions.fadeOut(0.0f));
	}

	@Override
	public void act(float delta)
	{
		// if we ran out of time
		if ((timer += delta) >= time)
		{
			addAction(Actions.fadeOut(0.5f));
			background.addAction(Actions.fadeOut(0.5f));
			visible = false;
		}


		super.act(delta);
	}

	private void CreateStyles()
	{
		SimpleActor actor = background = new SimpleActor(0, 0, getWidth(), getHeight(),
			   "ScrollPaneBackground", "chatBox.png");
		addActor(actor);

		// then create the labelstyle
		addedLabelStyle = new Label.LabelStyle(TextBoxScreen.font, Color.WHITE);
	}

	public void AddEntry(String text)
	{
		// reset the interaction timer
		timer = 0;

		// make sure the screen is now visible
		if (!visible)
		{
			visible = true;
			addAction(Actions.fadeIn(0.5f));
			background.addAction(Actions.fadeIn(0.5f));
		}

		ArrayList<String> strings = new ArrayList<>();

		// if we need more than one label
		if (text.length() > maxTextLength)
		{
			int startingIndex = 0;
			int endingIndex = 0;
			int numberDivisions = (int)Math.ceil((float)text.length() / (float)maxTextLength);

			for (int i = 0; i < numberDivisions; i++)
			{
				endingIndex = startingIndex + maxTextLength;
				if (endingIndex > text.length())
				{
					endingIndex = text.length();
				}

				strings.add(text.substring(startingIndex, endingIndex));

				startingIndex += maxTextLength;
			}
		}
		else
		{
			strings.add(text);
		}

		for (String string : strings)
		{
			// create a new label
			Label newLabel = new Label(string, addedLabelStyle);
			labels.add(newLabel);
			newLabel.setPosition(10,0);
			addActor(newLabel);

			for (Label label : labels)
			{
				label.setPosition(10, label.getY() + 30);
			}
		}
	}

	public void WakeUp()
	{
		background.addAction(Actions.fadeIn(0.5f));
		addAction(Actions.fadeIn(0.5f));
		timer = 0 ;
	}

	public void FadeOut()
	{
		background.addAction(Actions.fadeOut(0.5f));
		addAction(Actions.fadeOut(0.5f));
	}
}
