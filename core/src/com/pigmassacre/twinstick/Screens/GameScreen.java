package com.pigmassacre.twinstick.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigmassacre.twinstick.Assets;
import com.pigmassacre.twinstick.Entity;
import com.pigmassacre.twinstick.Level;
import com.pigmassacre.twinstick.PlayerEntity;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class GameScreen extends AbstractScreen {

	private OrthographicCamera camera;
	private ScreenViewport viewport;

	private CameraInputController cameraInputController;

	public GameScreen() {
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);

		camera.position.set(25f, 40f, 25f);
		camera.zoom = 1f / 16f;
		camera.lookAt(25f, 0f, 25f);
		camera.near = 0f;
		camera.far = 300f;
		camera.update();

		cameraInputController = new CameraInputController(camera);
		inputMultiplexer.addProcessor(cameraInputController);

		ModelBuilder modelBuilder = new ModelBuilder();
		Model playerModel = modelBuilder.createBox(3f, 3f, 3f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		Model box = modelBuilder.createBox(5f, 5f, 5f,
				new Material(TextureAttribute.createDiffuse(Assets.getAtlas().findRegion("wall"))),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		Model floorModel = modelBuilder.createBox(5f, 1f, 5f,
				new Material(TextureAttribute.createDiffuse(Assets.getAtlas().findRegion("floor"))),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		Model bulletModel = modelBuilder.createBox(0.5f, 0.5f, 0.5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		Level.INSTANCE.setPlayerEntity(new PlayerEntity(playerModel, bulletModel, 0f, 0f, 0f));
		for (int x = 0; x < 12; x++) {
			Level.INSTANCE.addEntity(new Entity(box, (5f * x) - 5f, -5f, 0));
			Level.INSTANCE.addEntity(new Entity(box, (5f * x) - 5f, 30f, 0));
		}

		for (int y = 0; y < 6; y++) {
			Level.INSTANCE.addEntity(new Entity(box, -5f, (5f * y), 0));
			Level.INSTANCE.addEntity(new Entity(box, 50f, (5f * y), 0));
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				Level.INSTANCE.addEntity(new Entity(floorModel, 5f * i, 5f * j, -3f));
			}
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Level.INSTANCE.render(delta, camera);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		Level.INSTANCE.dispose();
	}
}
