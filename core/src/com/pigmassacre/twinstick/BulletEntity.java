package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class BulletEntity extends Entity{

	public BulletEntity(Model bulletModel, float x, float y, float z) {
		super(bulletModel, x, y, z);
	}

	public boolean killWhenOutOfBounds() {
		return true;
	}

}
