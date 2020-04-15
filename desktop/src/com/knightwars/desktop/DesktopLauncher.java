package com.knightwars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.knightwars.KnightWars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 640;
		config.width = 960;
		config.forceExit = false; // to prevent non-zero exit value when the window is closed
		new LwjglApplication(new KnightWars(), config);
	}
}
