package com.bobo.utils;

public class Constants {

	public static final String DESKTOP_PATH_PREFIX =  "../android/assets/";
	
	public static String getPath(String fileName) {
		return fileName;
		//return  ((Gdx.app.getType() == ApplicationType.Desktop) ? DESKTOP_PATH_PREFIX: "") + fileName;
	}
	
	
	public static final String FONT_DEFAULT_NORMAL = "fonts/emulogic_font.fnt";
	
	// World Width
	public static final float VIEWPORT_WIDTH = 16.0f;
	
	// World Height
	public static final float VIEWPORT_HEIGHT = 14.0f;
	
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 512.0f;
	
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 448.0f;
	
	//Sprites
	
	public static final String TILESET_TEXTURE_ATLAS_OBJECTS = "images/tileset.pack.atlas";
	
	public static final String CHARACTERS_TEXTURE_ATLAS_OBJECTS = "images/characters.pack.atlas";
	
	public static final String ENEMIES_TEXTURE_ATLAS_OBJECTS = "images/enemies.pack.atlas";

	public static final String OBJECTS_TEXTURE_ATLAS_OBJECTS = "images/objects.pack.atlas";
	
	//Worlds
	
	public static final String WORLD_1_1 = "worlds/world_1_1.png";
	
	public static final float TIME_DELAY_GAME_OVER = 3.0f;
}
