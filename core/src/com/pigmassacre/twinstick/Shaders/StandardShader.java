package com.pigmassacre.twinstick.Shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.pigmassacre.twinstick.Entity;

/**
 * Created by Pigmassacre on 2015-05-19.
 */
public class StandardShader implements Shader {

	private ShaderProgram program;
	private Camera camera;
	private RenderContext context;

	private Entity lightEntity;

	private int modelMatrix;
	private int modelViewMatrix;
	private int modelViewProjectionMatrix;
	private int viewProjectionMatrix;

	private int normalMatrix;
	private int texture;
	private int normal_texture;
	private int useNormal;
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
		normal_texture = program.getUniformLocation("normal_texture");
		useNormal = program.getUniformLocation("useNormal");
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
		program.setUniformf(material_shininess, 5f);
		program.setUniformf(material_diffuse_color, 1f, 1f, 1f);
		program.setUniformf(material_specular_color, 0.1f, 0.1f, 0.1f);
		program.setUniformf(material_emissive_color, 0f, 0f, 0f);

		program.setUniformf(scene_ambient_light, 0.25f, 0.25f, 0.25f);
		program.setUniformf(scene_light, 0.75f, 0.75f, 0.75f);

		program.setUniformMatrix(modelMatrix, renderable.worldTransform);
		program.setUniformMatrix(modelViewMatrix, camera.view.cpy().mul(renderable.worldTransform));
		program.setUniformMatrix(modelViewProjectionMatrix, camera.projection.cpy().mul(camera.view).mul(renderable.worldTransform));
		program.setUniformMatrix(normalMatrix, camera.view.cpy().mul(renderable.worldTransform).tra().inv());

		program.setUniformi(texture, context.textureBinder.bind(((TextureAttribute) renderable.material.get(TextureAttribute.Diffuse)).textureDescription));
		Attribute normalAttribute = renderable.material.get(TextureAttribute.Normal);
		if (normalAttribute != null) {
			program.setUniformi(useNormal, 1);
			program.setUniformi(normal_texture, context.textureBinder.bind(((TextureAttribute) normalAttribute).textureDescription));
		} else {
			program.setUniformi(useNormal, 0);
		}

		if (lightEntity != null) {
			// We need to flip the z and y values since I use them differently in the game logic..
			Vector3 cpy = lightEntity.getPosition().cpy();
			float temp = cpy.z;
			cpy.z = cpy.y;
			cpy.y = temp;
			program.setUniformf(viewSpaceLightPosition, cpy.mul(camera.view));
		} else {
			program.setUniformf(viewSpaceLightPosition, new Vector3());
		}

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

	public Entity getLightEntity() {
		return lightEntity;
	}

	public void setLightEntity(Entity lightEntity) {
		this.lightEntity = lightEntity;
	}
}
