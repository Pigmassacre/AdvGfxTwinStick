package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class DecalEntity extends Entity {

	public Decal decal;

	public DecalEntity(Decal decal, float x, float y, float z) {
		this.decal = decal;
		position = new Vector3(x, y, z);
		velocity = new Vector3();
		rectangle = new Rectangle(x, y, decal.getWidth(), decal.getWidth());
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		decal.setPosition(position.x, position.z, position.y);
	}

	public void updateCameraPos(Camera camera) {
		decal.lookAt(camera.position, camera.up);
	}

}
