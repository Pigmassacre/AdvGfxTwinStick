package com.pigmassacre.twinstick;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerInputAdapter extends InputAdapter {

	private Entity controlledEntity;

	public PlayerInputAdapter(Entity controlledEntity) {
		this.controlledEntity = controlledEntity;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.LEFT:
				controlledEntity.getVelocity().add(-0.05f, 0f, 0f);
				break;
			case Input.Keys.RIGHT:
				controlledEntity.getVelocity().add(0.05f, 0f, 0f);
				break;
			case Input.Keys.UP:
				controlledEntity.getVelocity().add(0f, 0.05f, 0f);
				break;
			case Input.Keys.DOWN:
				controlledEntity.getVelocity().add(0f, -0.05f, 0f);
				break;
		}
		return true;
	}
}
