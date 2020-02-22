package com.gen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.gen.game.RAssetManager;
import com.gen.networking.Client;
import com.gen.object.SimpleActor;

import javax.naming.NameNotFoundException;
import java.util.Random;

public class TextBoxScreen extends Stage implements InputProcessor
{
	private Label textReceivedLabel;
	private TextField textBox;
	private TextField offBox;
	private ChatLog chatLog;
	public SimpleActor textBoxBackground;
	public static BitmapFont font;
	public static TextField.TextFieldStyle style;

	public TextBoxScreen()
	{
		super();

		CreateStyles();

		chatLog = new ChatLog(0, 178, 800, 900);
		addActor(chatLog);

	}

	private void CreateStyles()
	{
		// parameters for generating a custom bitmap font
		FreeTypeFontGenerator fontGenerator =
			   new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kenney Pixel.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameters.size = 40;
		fontParameters.color = Color.WHITE;
		fontParameters.borderWidth = 0;
		fontParameters.borderColor = Color.WHITE;
		fontParameters.borderStraight = true;
		fontParameters.minFilter = Texture.TextureFilter.Linear;
		fontParameters.magFilter = Texture.TextureFilter.Linear;

		BitmapFont customFont = font = fontGenerator.generateFont(fontParameters);

		SimpleActor actor = textBoxBackground = new SimpleActor(0,0,800,40,"TextBox", RAssetManager.chatBox);
		actor.setPosition(0, 138);
		addActor(actor);
		NinePatch ninePatch = new NinePatch((Texture)RAssetManager.manager.get(RAssetManager.cursor));
		NinePatchDrawable drawable = new NinePatchDrawable(ninePatch);

		textBoxBackground.addAction(Actions.fadeOut(0.5f));

		style = new TextField.TextFieldStyle(customFont, Color.WHITE, drawable, null, null);
		textBox = new TextField("", style);
		offBox = new TextField("", style);
		offBox.setPosition(-100, -100);

		addActor(textBox);

		textBox.setWidth(800);
		textBox.setHeight(64);
		textBox.setPosition(10, 128);
	}

	@Override
	public void act(float delta) {
		super.act(delta);


		if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
		{
			acceptInput();
			chatLog.WakeUp();
		}

		String testString = Client.GetInputString();

		if (!testString.equals(""))
		{
			// then take the input to the chat log
			chatLog.AddEntry(testString);
		}
	}

	private void acceptInput() {

		String input = textBox.getText();
		if (input != null && input.length() > 0) {
			textBox.setText("");

			// then take the input to the chat log
			chatLog.AddEntry(input);
			Client.SetOutputString(input);
		}
	}

	@Override
	public boolean keyTyped(char character)
	{
		return super.keyTyped(character);
	}

	public void Enter()
	{
		this.setKeyboardFocus(textBox);
		textBox.addAction(Actions.fadeIn(0.5f));

		chatLog.addAction(Actions.fadeIn(0.5f));
		textBoxBackground.addAction(Actions.fadeIn(0.5f));
	}

	public void Clear()
	{
		textBox.addAction(Actions.fadeOut(0.5f));
		chatLog.FadeOut();
		textBoxBackground.addAction(Actions.fadeOut(0.5f));

		unfocus(textBox);
	}

}
