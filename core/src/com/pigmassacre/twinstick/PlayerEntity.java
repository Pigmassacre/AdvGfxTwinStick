package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerEntity extends DecalEntity {

	private Texture bulletTex;

	public PlayerEntity(Decal decal, Texture bulletTex, float x, float y, float z) {
		super(decal, x, y, z);
		this.bulletTex = bulletTex;
	}

	public void shoot(Vector3 direction) {
		Entity entity = new BulletEntity(
				Decal.newDecal(
						bulletTex.getWidth() / 3f,
						bulletTex.getHeight() / 3f,
						new TextureRegion(bulletTex), true),
				getPosition().x,
				getPosition().y,
				getPosition().z);
		entity.getVelocity().set(direction.nor());
		Level.INSTANCE.addEntity(entity);
	}

}
