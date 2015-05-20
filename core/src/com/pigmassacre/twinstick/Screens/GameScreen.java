package com.pigmassacre.twinstick.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

		camera.position.set(Level.INSTANCE.getBounds().x + Level.INSTANCE.getBounds().width / 2f,
				40f,
				Level.INSTANCE.getBounds().y + Level.INSTANCE.getBounds().height / 2f);
		camera.zoom = 1f / 16f;
		camera.lookAt(Level.INSTANCE.getBounds().x + Level.INSTANCE.getBounds().width / 2f,
				0f,
				Level.INSTANCE.getBounds().y);
		camera.near = 0f;
		camera.far = 300f;
		camera.update();

		cameraInputController = new CameraInputController(camera);
		inputMultiplexer.addProcessor(cameraInputController);

		ModelBuilder modelBuilder = new ModelBuilder();

		Texture playerTex = new Texture(Gdx.files.internal("dirt.png"));
		playerTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Model playerModel = modelBuilder.createBox(3f, 3f, 1f,
				new Material(TextureAttribute.createDiffuse(playerTex)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);


		Texture boxTex = new Texture(Gdx.files.internal("wall.png"));
		boxTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Model box = modelBuilder.createBox(5f, 5f, 5f,
				new Material(TextureAttribute.createDiffuse(boxTex)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		Texture floorTex = new Texture(Gdx.files.internal("floor.png"));
		floorTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Model floorModel = modelBuilder.createBox(5f, 1f, 5f,
				new Material(TextureAttribute.createDiffuse(floorTex)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		Texture bulletTex = new Texture(Gdx.files.internal("floor.png"));
		bulletTex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Model bulletModel = modelBuilder.createBox(0.5f, 0.5f, 0.5f,
				new Material(TextureAttribute.createDiffuse(bulletTex)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		Level.INSTANCE.setPlayerEntity(new PlayerEntity(playerModel, bulletModel, Level.INSTANCE.getBounds().width / 2f, Level.INSTANCE.getBounds().height / 2f, 0f));

		for (int x = 0; x < Level.INSTANCE.getBounds().width / 5f; x++) {
			Level.INSTANCE.addEntity(new Entity(box, (5f * x), -5f, 0));
			Level.INSTANCE.addEntity(new Entity(box, (5f * x), Level.INSTANCE.getBounds().height, 0));
		}

		for (int y = 0; y < Level.INSTANCE.getBounds().height / 5f; y++) {
			Level.INSTANCE.addEntity(new Entity(box, -5f, (5f * y), 0));
			Level.INSTANCE.addEntity(new Entity(box, Level.INSTANCE.getBounds().width, (5f * y), 0));
		}

		for (int x = 0; x < Level.INSTANCE.getBounds().width / 5f; x++) {
			for (int y = 0; y < Level.INSTANCE.getBounds().height / 5f; y++) {
				Level.INSTANCE.addEntity(new Entity(floorModel, 5f * x, 5f * y, -3f));
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
