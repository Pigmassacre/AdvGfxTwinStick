package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerEntity extends Entity {

	private Model bulletModel;

	public PlayerEntity(Model model, Model  bulletModel,  float x, float y) {
		super(model, x, y);
		this.bulletModel = bulletModel;
	}

	public PlayerEntity(Model model, Model bulletModel, float x, float y, float z) {
		super(model, x, y, z);
		this.bulletModel = bulletModel;
	}

	public void shoot(Vector3 direction) {
		Entity entity = new Entity(bulletModel, getPosition().x, getPosition().y, getPosition().z);
		entity.getVelocity().set(direction.nor());
		Level.INSTANCE.addEntity(entity);
	}

}
