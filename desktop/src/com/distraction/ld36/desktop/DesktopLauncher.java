package com.distraction.ld36.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.distraction.ld36.LD36;
import com.distraction.ld36.Vars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Vars.WIDTH * Vars.SCALE;
        config.height = Vars.HEIGHT * Vars.SCALE;
        new LwjglApplication(new LD36(), config);
	}
}
