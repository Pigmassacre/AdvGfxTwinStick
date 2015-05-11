package com.pigmassacre.twinstick;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

/**
 * Created by Pigmassacre on 2015-05-11.
 */
public class PlayerControllerInputAdapter extends ControllerAdapter {

	private Entity controlledEntity;

	public PlayerControllerInputAdapter(Entity controlledEntity) {
		this.controlledEntity = controlledEntity;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		System.out.println(axisIndex);
		switch (axisIndex) {
			// Left Stick
			case 0: // Up and Down
				controlledEntity.getVelocity().y = -value;
				break;
			case 1: // Left and Right
				controlledEntity.getVelocity().x = -value;
				break;

			// Right Stick
			case 3: // Up and Down
				break;
			case 2: // Left and Right
				break;
		}
		return false;
	}
}
