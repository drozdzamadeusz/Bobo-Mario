package com.bobo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.bobo.game.BoboMain;

public class DesktopLauncher {

	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main(String[] arg) {

		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = true;
			settings.debug = drawDebugOutline;
			
			TexturePacker.process(settings, "assets-raw/images/Tileset", "../android/assets/images", "tileset.pack");
			TexturePacker.process(settings, "assets-raw/images/Characters", "../android/assets/images", "characters.pack");
			TexturePacker.process(settings, "assets-raw/images/Enemies", "../android/assets/images", "enemies.pack");
			TexturePacker.process(settings, "assets-raw/images/Objects", "../android/assets/images", "objects.pack");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 512;
		config.height = 448;

		
		/*config.vSyncEnabled = false; // Setting to false disables vertical sync
		config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // Setting to 0 disables background fps throttling*/
	
		new LwjglApplication(new BoboMain(), config);
	}
}
