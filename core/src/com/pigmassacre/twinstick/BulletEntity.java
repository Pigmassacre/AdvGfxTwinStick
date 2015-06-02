package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class BulletEntity extends DecalEntity {

	public BulletEntity(Decal bulletDecal, float x, float y, float z) {
		super(bulletDecal, x, y, z);
	}

	@Override
	public void updateCameraPos(Camera camera) {
		super.updateCameraPos(camera);
		decal.rotateX(MathUtils.atan2(velocity.y - position.y, velocity.x - position.x));
	}

	public boolean killWhenOutOfBounds() {
		return true;
	}

}
