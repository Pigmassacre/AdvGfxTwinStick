package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class Entity {

	private Vector2 position;
	private Vector2 velocity;

	private ModelInstance instance;

	public Entity(Model model, float x, float y) {
		position = new Vector2(x, y);
		velocity = new Vector2();

		instance = new ModelInstance(model);
	}

	public void render(ModelBatch batch, Environment environment) {
		position.add(velocity);

		instance.transform.setToTranslation(-position.x, 0f, position.y);
		batch.render(instance, environment);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Model getModel() {
		return instance.model;
	}
}
