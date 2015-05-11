package com.pigmassacre.twinstick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class GameScreen extends AbstractScreen {

	private OrthographicCamera camera;
	private ScreenViewport viewport;

	private CameraInputController cameraInputController;

	private Environment environment;
	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance instance;

	public GameScreen() {
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);

		camera.position.set(5f, 5f, 5f);
		camera.zoom = 0.05f;
		camera.lookAt(0f, 0f, 0f);
		camera.update();

		cameraInputController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraInputController);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		instance = new ModelInstance(model);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		modelBatch.begin(camera);
		modelBatch.render(instance, environment);
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
		model.dispose();
	}
}
