package com.pigmassacre.twinstick.Shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by Pigmassacre on 2015-05-19.
 */
public class StandardShader implements Shader {

	ShaderProgram program;
	Camera camera;
	RenderContext context;

	private int modelMatrix;
	private int modelViewMatrix;
	private int modelViewProjectionMatrix;
	private int viewProjectionMatrix;

	private int normalMatrix;
	private int texture;
	private int viewSpaceLightPosition;

	private int material_shininess;
	private int material_diffuse_color;
	private int material_specular_color;
	private int material_emissive_color;

	private int scene_ambient_light;
	private int scene_light;

	@Override
	public void init() {
		program = new ShaderProgram(
				Gdx.files.internal("standard.vert").readString(),
				Gdx.files.internal("standard.frag").readString());
		if (!program.isCompiled()) {
			throw new GdxRuntimeException(program.getLog());
		}
		modelMatrix = program.getUniformLocation("modelMatrix");
		modelViewMatrix = program.getUniformLocation("modelViewMatrix");
		modelViewProjectionMatrix = program.getUniformLocation("modelViewProjectionMatrix");
		viewProjectionMatrix = program.getUniformLocation("viewProjectionMatrix");
		normalMatrix = program.getUniformLocation("normalMatrix");
		texture = program.getUniformLocation("texture");
		viewSpaceLightPosition = program.getUniformLocation("viewSpaceLightPosition");

		material_shininess = program.getUniformLocation("material_shininess");
		material_diffuse_color = program.getUniformLocation("material_diffuse_color");
		material_specular_color = program.getUniformLocation("material_specular_color");
		material_emissive_color = program.getUniformLocation("material_emissive_color");

		scene_ambient_light = program.getUniformLocation("scene_ambient_light");
		scene_light = program.getUniformLocation("scene_light");
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return true;
	}

	@Override
	public void begin(Camera camera, RenderContext context) {
		this.camera = camera;
		this.context = context;
		program.begin();
		program.setUniformMatrix(viewProjectionMatrix, camera.combined);
		context.setDepthTest(GL20.GL_LEQUAL);
		context.setCullFace(GL20.GL_BACK);
	}

	@Override
	public void render(Renderable renderable) {
		program.setUniformf(material_shininess, 25f);
		program.setUniformf(material_diffuse_color, 1f, 1f, 1f);
		program.setUniformf(material_specular_color, 0f, 0f, 0f);
		program.setUniformf(material_emissive_color, 0f, 0f, 0f);

		program.setUniformf(scene_ambient_light, 0.5f, 0.5f, 0.5f);
		program.setUniformf(scene_light, 0.6f, 0.6f, 0.6f);

		program.setUniformMatrix(modelMatrix, renderable.worldTransform);
		program.setUniformMatrix(modelViewMatrix, camera.view.cpy().mul(renderable.worldTransform));
		program.setUniformMatrix(modelViewProjectionMatrix, camera.projection.cpy().mul(camera.view).mul(renderable.worldTransform));
		program.setUniformMatrix(normalMatrix, renderable.worldTransform.cpy().mul(camera.view).inv().tra());
		program.setUniformi(texture, context.textureBinder.bind(((TextureAttribute) renderable.material.get(TextureAttribute.Diffuse)).textureDescription));
		program.setUniformf(viewSpaceLightPosition, camera.project(new Vector3(0f, 5f, 0f)));
		renderable.mesh.render(program,
				renderable.primitiveType,
				renderable.meshPartOffset,
				renderable.meshPartSize);
	}

	@Override
	public void end() {
		program.end();
	}

	@Override
	public void dispose() {
		program.dispose();
	}

}
