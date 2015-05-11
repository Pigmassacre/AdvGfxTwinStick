package com.pigmassacre.twinstick.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigmassacre.twinstick.Entity;
import com.pigmassacre.twinstick.PlayerInputAdapter;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class GameScreen extends AbstractScreen {

	private OrthographicCamera camera;
	private ScreenViewport viewport;

	private CameraInputController cameraInputController;

	private Environment environment;
	private ModelBatch modelBatch;

	private Array<Entity> entities;
	private Entity playerEntity;

	public GameScreen() {
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);

		camera.position.set(0f, 25f, -25f);
		camera.zoom = 1f / 16f;
		camera.lookAt(0f, 0f, 0f);
		camera.near = 0f;
		camera.far = 300f;
		camera.update();

		cameraInputController = new CameraInputController(camera);
		inputMultiplexer.addProcessor(cameraInputController);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();
		Model playerModel = modelBuilder.createBox(3f, 3f, 3f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		Model horizontalWallModel = modelBuilder.createBox(100f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		Model verticalWallModel = modelBuilder.createBox(5f, 5f, 60f,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		Model floorModel = modelBuilder.createBox(100f, 1f, 60f,
				new Material(ColorAttribute.createDiffuse(Color.RED)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

		entities = new Array<Entity>();

		playerEntity = new Entity(playerModel, 0f, 0f);
		entities.add(playerEntity);
		entities.add(new Entity(horizontalWallModel, 0f, 30f));
		entities.add(new Entity(verticalWallModel, -50f, 0f));
		entities.add(new Entity(verticalWallModel, 50f, 0f));
		entities.add(new Entity(horizontalWallModel, 0f, -30f));
		entities.add(new Entity(floorModel, 0f, 0f, -2f));

		inputMultiplexer.addProcessor(new PlayerInputAdapter(playerEntity));
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		modelBatch.begin(camera);
		for (Entity entity : entities) {
			entity.render(modelBatch, environment);
		}
		modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
		modelBatch.dispose();
		for (Entity entity : entities) {
			entity.getModel().dispose();
		}
	}
}
