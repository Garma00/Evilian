package com.prog.evilian.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.prog.evilian.Evilian;

public class DesktopLauncher 
{
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                config.setWindowedMode(800, 600);
                config.setIdleFPS(60);
                config.setResizable(false);
                config.setWindowIcon("images/icon/Icon_16x16.png","images/icon/Icon_32x32.png");
		new Lwjgl3Application(new Evilian(), config);
	}
}
