package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public enum Level {
	INSTANCE;

	private Environment environment;
	private ModelBatch modelBatch;

	private Rectangle bounds;

	private Array<Entity> entities;
	private PlayerEntity playerEntity;

	private PlayerControllerInputAdapter playerControllerInputAdapter;

	Level() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		modelBatch = new ModelBatch();

		bounds = new Rectangle(0f, 0f, 100f, 60f);

		entities = new Array<Entity>();
	}

	public void render(float delta, Camera camera) {
		playerControllerInputAdapter.update(delta);

		for (Entity entity : getEntities()) {
			entity.update(delta);
			if (!Intersector.overlaps(bounds, entity.getRectangle())) {
				entities.removeValue(entity, true);
			}
		}

		modelBatch.begin(camera);
		for (Entity entity : getEntities()) {
			entity.render(modelBatch, environment);
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
		modelBatch.dispose();
		for (Entity entity : getEntities()) {
			entity.getModel().dispose();
		}
	}
}
