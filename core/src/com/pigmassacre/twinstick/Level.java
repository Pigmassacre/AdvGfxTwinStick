package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pigmassacre.twinstick.Shaders.BillboardShader;
import com.pigmassacre.twinstick.Shaders.StandardShader;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public enum Level {
	INSTANCE;

	private ModelBatch modelBatch;
	private StandardShader stdShader;
	private BillboardShader bbShader;

	private DecalBatch decalBatch;

	private Rectangle bounds;

	private Array<Entity> entities;
	private PlayerEntity playerEntity;

	private PlayerControllerInputAdapter playerControllerInputAdapter;

	Level() {
		stdShader = new StandardShader();
		stdShader.init();

		bbShader = new BillboardShader();
		bbShader.init();

		modelBatch = new ModelBatch();

		bounds = new Rectangle(0f, 0f, 100f, 60f);

		entities = new Array<Entity>();
	}

	public void setupDecalBatch(Camera camera) {
		decalBatch = new DecalBatch(new CameraGroupStrategy(camera));
	}

	public void render(float delta, Camera camera) {
		playerControllerInputAdapter.update(delta);

		for (Entity entity : getEntities()) {
			entity.update(delta);
			if (entity instanceof DecalEntity) {
				((DecalEntity) entity).updateCameraPos(camera);
			}
			if (!Intersector.overlaps(entity.getRectangle(), bounds)) {
				if (entity.killWhenOutOfBounds()) {
					entities.removeValue(entity, true);
				} else {
					entity.getVelocity().setZero();
				}
			}
		}

		modelBatch.begin(camera);
		for (Entity entity : getEntities()) {
			if (entity instanceof DecalEntity) {
				decalBatch.add(((DecalEntity) entity).decal);
			} else {
				entity.render(modelBatch, stdShader);
			}

		}
		modelBatch.end();
		decalBatch.flush();
	}

	public void setPlayerEntity(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
		addEntity(playerEntity);
		stdShader.setLightEntity(playerEntity);
		if (Controllers.getControllers().size > 0) {
			playerControllerInputAdapter = new PlayerControllerInputAdapter(playerEntity);
			Controllers.getControllers().get(0).addListener(playerControllerInputAdapter);
		}
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Array<Entity> getEntities() {
		return entities;
	}

	public void dispose() {
		stdShader.dispose();
		modelBatch.dispose();
		for (Entity entity : getEntities()) {
			entity.getModel().dispose();
		}
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
