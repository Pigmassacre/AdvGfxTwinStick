package com.pigmassacre.twinstick.Shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by Pigmassacre on 2015-05-19.
 */
public class StandardShader implements Shader {

	ShaderProgram program;
	Camera camera;
	RenderContext context;

	private int u_projViewTrans;
	private int u_worldTrans;
	private int texture;

	@Override
	public void init() {
		program = new ShaderProgram(
				Gdx.files.internal("standard.vert").readString(),
				Gdx.files.internal("standard.frag").readString());
		if (!program.isCompiled()) {
			throw new GdxRuntimeException(program.getLog());
		}
		u_projViewTrans = program.getUniformLocation("u_projViewTrans");
		u_worldTrans = program.getUniformLocation("u_worldTrans");
		texture = program.getUniformLocation("texture");
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
		program.setUniformMatrix(u_projViewTrans, camera.combined);
		context.setDepthTest(GL20.GL_LEQUAL);
		context.setCullFace(GL20.GL_BACK);
	}

	@Override
	public void render(Renderable renderable) {
		program.setUniformMatrix(u_worldTrans, renderable.worldTransform);
		program.setUniformi(texture, context.textureBinder.bind(((TextureAttribute) renderable.material.get(TextureAttribute.Diffuse)).textureDescription));
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
