package com.cherryscramble.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cherryscramble.g1.CherryScrambleMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//
		// Window variables for desktop
		//
		
		// -= Window Title and Icon =-
		config.title = "Cherry Scramble!";  // Window Title
		config.addIcon("assets-raw/icons/CherryIcon.png", Files.FileType.Internal);     //Window Icon
		
		// -= Window Resolution =-
		config.height = 576; // Window Height (18 tiles high)
		config.width = 800;  // Window Width  (32 tiles wide)
		
		
		new LwjglApplication(new CherryScrambleMain(), config); //Create application
	}
}
