package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pigmassacre.twinstick.Shaders.BillboardShader;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public enum Level {
	INSTANCE;

	private ModelBatch modelBatch;
	private Shader shader;

	private Rectangle bounds;

	private Array<Entity> entities;
	private PlayerEntity playerEntity;

	private PlayerControllerInputAdapter playerControllerInputAdapter;

	Level() {
		shader = new BillboardShader();
		shader.init();

		modelBatch = new ModelBatch();

		bounds = new Rectangle(-50f, -30f, 100f, 60f);

		entities = new Array<Entity>();
	}

	public void render(float delta, Camera camera) {
		playerControllerInputAdapter.update(delta);

		for (Entity entity : getEntities()) {
			entity.update(delta);
			if (!Intersector.overlaps(bounds, entity.getRectangle())) {
				//entities.removeValue(entity, true);
				//System.out.println("out of bounds");
			}
		}

		modelBatch.begin(camera);
		for (Entity entity : getEntities()) {
			entity.render(modelBatch, shader);
		}
		modelBatch.end();
	}

	public void setPlayerEntity(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
		addEntity(playerEntity);
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
		shader.dispose();
		modelBatch.dispose();
		for (Entity entity : getEntities()) {
			entity.getModel().dispose();
		}
	}
}
