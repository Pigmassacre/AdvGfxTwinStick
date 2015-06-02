package com.pigmassacre.twinstick;

import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class Entity {

	protected Vector3 position;
	protected Vector3 velocity;

	protected Rectangle rectangle;

	private ModelInstance instance;

	public Entity(Model model, float x, float y, float z) {
		position = new Vector3(x, y, z);
		velocity = new Vector3();
		BoundingBox boundingBox = model.calculateBoundingBox(new BoundingBox());
		rectangle = new Rectangle(x, y, boundingBox.getWidth(), boundingBox.getDepth());

		instance = new ModelInstance(model);
	}

	public Entity() {
	}

	public void update(float delta) {
		position.add(velocity);
		rectangle.setPosition(position.x, position.y);
	}

	public void render(ModelBatch batch, Environment environment) {
		instance.transform.setToTranslation(position.x, position.z, position.y);
		batch.render(instance, environment);
	}

	public void render(ModelBatch batch, Shader shader) {
		instance.transform.setToTranslation(position.x, position.z, position.y);
		batch.render(instance, shader);
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

	public ModelInstance getInstance() {
		return instance;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public boolean killWhenOutOfBounds() {
		return false;
	}
}
