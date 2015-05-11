package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class Entity {

	private Vector3 position;
	private Vector3 velocity;

	private ModelInstance instance;

	public Entity(Model model, float x, float y) {
		position = new Vector3(x, y, 0f);
		velocity = new Vector3();

		instance = new ModelInstance(model);
	}

	public Entity(Model model, float x, float y, float z) {
		this(model, x, y);
		getPosition().z = z;
	}

	public void render(ModelBatch batch, Environment environment) {
		position.add(velocity);

		instance.transform.setToTranslation(position.x, position.z, position.y);
		batch.render(instance, environment);
	}

	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getVelocity() {
		return velocity;
	}

	public Model getModel() {
		return instance.model;
	}
}
