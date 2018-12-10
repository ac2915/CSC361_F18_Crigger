/**
 * Class contains the properties for CherryScramble's desktop launcher.
 */

package com.cherryscramble.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.cherryscramble.g1.CherryScrambleMain;

public class DesktopLauncher {
	
	private static boolean rebuildAtlas = true;		// Boolean of atlas rebuild
	private static boolean drawDebugOutline = false;	// Boolean for draw debug
	
	public static void main (String[] arg) {	
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration(); //Configuration
		
		//
		// Window variables for desktop
		//
		
		// -= Window Title and Icon =-
		config.title = "Cherry Scramble!";  										// Window Title
		config.addIcon("assets-raw/icons/CherryIcon.png", Files.FileType.Internal); // Window Icon
		
		// -= Window Resolution =-
		config.height = 576; 		// Window Height (18 tiles high)
		config.width = 800;  		// Window Width  (32 tiles wide)
		config.resizable = false; 	// Prevents Resizing of the window
		
		// Rebuilds the texture atlases when they need to be recompiled
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 2048;
			settings.maxHeight = 2048;
			settings.debug = drawDebugOutline; //Draws an outline around the images
			
			// Process the textures and merge it into a texture atlas
			TexturePacker.process(settings, "assets-raw/images", "../core/assets/images", "cherryscramble.atlas");
			
			// UI Texture atlas
			TexturePacker.process(settings, "assets-raw/images-ui", "../core/assets/images-ui", "cherryscrambleui.atlas");
		}
		System.out.println("========== Terminal Debug ==========");
		new LwjglApplication(new CherryScrambleMain(), config); //Create application
	}
}
