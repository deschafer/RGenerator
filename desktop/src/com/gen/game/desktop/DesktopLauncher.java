package com.gen.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gen.game.RGenerator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 1920;
		config.height = 1080;
		config.title = "RGenerator";

		Game myGame = new RGenerator(config.width, config.height);
		LwjglApplication launcher = new LwjglApplication(myGame, config);
	}
}
