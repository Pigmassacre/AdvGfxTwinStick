package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerControllerInputAdapter extends ControllerAdapter {

	private static final float SHOOT_COOLDOWN_TIME = 0.1f;
	private static final float DEADZONE = 0.25f;

	private PlayerEntity controlledEntity;

	private Vector3 shootDirection;
	private float shootStateTime = SHOOT_COOLDOWN_TIME;

	public PlayerControllerInputAdapter(PlayerEntity controlledEntity) {
		this.controlledEntity = controlledEntity;
		shootDirection = new Vector3();
	}

	public void update(float delta) {
		if (Math.abs(shootDirection.x) > DEADZONE ||
				Math.abs(shootDirection.y) > DEADZONE) {
			if (shootStateTime > SHOOT_COOLDOWN_TIME) {
				controlledEntity.shoot(shootDirection);
				shootStateTime = 0f;
			}
			shootStateTime += delta;
		}
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		switch (axisIndex) {
			// Left Stick
			case 0: // Up and Down
				controlledEntity.getVelocity().y = -value * 0.8f;
				break;
			case 1: // Left and Right
				controlledEntity.getVelocity().x = -value * 0.8f;
				break;

			// Right Stick
			case 3: // Up and Down
				shootDirection.y = -value * 2f;
				break;
			case 2: // Left and Right
				shootDirection.x = -value * 2f;
				break;
		}
		return false;
	}
}
