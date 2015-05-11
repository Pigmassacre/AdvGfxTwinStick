package com.pigmassacre.twinstick;

import com.badlogic.gdx.Game;
import com.pigmassacre.twinstick.Screens.GameScreen;

public class TwinStick extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}

}
