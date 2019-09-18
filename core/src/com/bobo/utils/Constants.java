package com.bobo.utils;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class Constants {

	public static final String DESKTOP_PATH_PREFIX =  "../android/assets/";
	
	public static String getPath(String fileName) {
		return  ((Gdx.app.getType() == ApplicationType.Desktop) ? DESKTOP_PATH_PREFIX: "") + fileName;
	}
	
	public static final float VIEWPORT_WIDTH = 16.0f;

	public static final float VIEWPORT_HEIGHT = 16.0f;
	
	public static final String TILESET_TEXTURE_ATLAS_OBJECTS = "images/tileset.pack.atlas";
	
	//Worlds
	
	public static final String WORLD_1_1 = "worlds/world_1_1.png";
}
