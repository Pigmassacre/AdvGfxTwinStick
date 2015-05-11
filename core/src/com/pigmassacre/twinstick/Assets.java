package com.pigmassacre.twinstick;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class Assets {

	private static AssetManager manager = new AssetManager();

	public static AssetManager getAssetManager() {
		return manager;
	}

	public static void loadAtlas() {
		if (!manager.isLoaded("atlas.atlas")) {
			manager.load("atlas.atlas", TextureAtlas.class);
		}
	}

	public static void unloadAtlas() {
		manager.unload("atlas.atlas");
	}

	public static TextureAtlas getAtlas() {
		return manager.get("atlas.atlas", TextureAtlas.class);
	}

}
