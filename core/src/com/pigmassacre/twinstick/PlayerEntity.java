package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerEntity extends Entity {

	public Decal decal;

	private Model bulletModel;

	public PlayerEntity(Decal decal, Model bulletModel, float x, float y, float z) {
		this.decal = decal;
		this.bulletModel = bulletModel;
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

	public void shoot(Vector3 direction) {
		Entity entity = new BulletEntity(bulletModel, getPosition().x, getPosition().y, getPosition().z);
		entity.getVelocity().set(direction.nor());
		Level.INSTANCE.addEntity(entity);
	}

}
