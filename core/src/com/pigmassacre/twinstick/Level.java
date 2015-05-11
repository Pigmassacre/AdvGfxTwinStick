package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public enum Level {
	INSTANCE;

	private Array<Entity> entities;
	private PlayerEntity playerEntity;

	private PlayerControllerInputAdapter playerControllerInputAdapter;

	Level() {
		entities = new Array<Entity>();
	}

	public void update(float delta) {
		playerControllerInputAdapter.update(delta);
	}

	public void setPlayerEntity(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
		addEntity(playerEntity);
		if (Controllers.getControllers().size > 0) {
			playerControllerInputAdapter = new PlayerControllerInputAdapter(this.playerEntity);
			Controllers.getControllers().get(0).addListener(playerControllerInputAdapter);
		}
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Array<Entity> getEntities() {
		return entities;
	}
}
