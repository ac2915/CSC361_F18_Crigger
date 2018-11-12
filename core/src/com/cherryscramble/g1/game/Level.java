/**
 * Level class properly creates the game's level based off of the image template
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.cherryscramble.g1.objects.AbstractGameObject;
import com.cherryscramble.g1.objects.Ground;
import com.cherryscramble.g1.objects.TrunkWallLeft;
import com.cherryscramble.g1.objects.TrunkWallRight;

public class Level {
	public static final String TAG = Level.class.getName();
	
	//Assets
	public Array<Ground> grounds;
	public Array<TrunkWallLeft> leftTrunkWalls;
	public Array<TrunkWallRight> rightTrunkWalls;
	
	/**
	 * Color coordination. Sets colors to specific kind of object
	 */
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), 				// Empty spaces are Black.
		GROUND(181,230,29),				// Ground
		TRUNKWALL_LEFT(153,217,234),	// TrunkWall Left
		TRUNKWALL_RIGHT(185,122,87);	// TrunkWall Right
		
		private int color;
		
		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		public boolean sameColor (int color) {
			return this.color == color;
		}
		
		public int getColor () {
			return color;
		}
	}
	
	/**
	 * Level Call
	 */
	public Level (String filename) {
		init(filename);
	}
	
	/**
	 * Initializes the level. Places objects where they should be.
	 */
	private void init (String filename) {
		//Load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		//Assets
		grounds = new Array<Ground>(); 					// Ground
		leftTrunkWalls = new Array<TrunkWallLeft>();	// Left TrunkWall
		rightTrunkWalls = new Array<TrunkWallRight>();	// Right TrunkWall
				
		//Scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractGameObject obj = null;
				float offsetHeight = 0;
						
				//Height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				 
				//Get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
						
				/* Find matching color value to identify block type at (x,y)
				 * point and create the corresponding game object if there is
				 * a match 
				 */
						
				//Empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
				//Do nothing
				}
				
				//Ground
				else if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) {
					System.out.println("found ground");
					obj = new Ground();
					offsetHeight = -2.0f;
					obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
					grounds.add((Ground)obj);
				}
				
				//TrunkWall Left
				else if (BLOCK_TYPE.TRUNKWALL_LEFT.sameColor(currentPixel)) {
					System.out.println("found left trunk!");
					obj = new TrunkWallLeft();
					//offsetHeight = -1.5f; No Offset Right Now.
					obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
					leftTrunkWalls.add((TrunkWallLeft)obj);
				}
				
				//TrunkWall Right
				else if (BLOCK_TYPE.TRUNKWALL_RIGHT.sameColor(currentPixel)) {
					obj = new TrunkWallRight();
					//offsetHeight = -1.5f; No Offset Right Now.
					obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
					rightTrunkWalls.add((TrunkWallRight)obj);
				}
				
				//Unknown object/pixel color
				else {
					int r = 0xff & (currentPixel >>> 24); //Red color channel
					int g = 0xff & (currentPixel >>> 16); //Green color channel
					int b = 0xff & (currentPixel >>> 8); //Blue color channel
					int a = 0xff & currentPixel; //Alpha channel
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b + "> a<" + a + ">");
				}
				
				lastPixel = currentPixel;
			}
		}
		
		//Free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	/**
	 * Renders the objects into the level.
	 */
	public void render (SpriteBatch batch) {
		//Draw Ground objects
		for (Ground ground : grounds)
			ground.render(batch);
		
		//Draw the TrunkWalls
		for (TrunkWallLeft trunkwallleft : leftTrunkWalls)
			trunkwallleft.render(batch);
		
		for (TrunkWallRight trunkwallright : rightTrunkWalls)
			trunkwallright.render(batch);
	}
	
	/**
	 * Updates the level each frame as the game progresses
	 * @param deltaTime
	 */
	public void update (float deltaTime) {
		//Ground
		for(Ground ground : grounds)
			ground.update(deltaTime);
		//TrunkWall Left
		for (TrunkWallLeft trunkwallleft : leftTrunkWalls)
			trunkwallleft.update(deltaTime);
		//TrunkWall Right
		for (TrunkWallRight trunkwallright : rightTrunkWalls)
			trunkwallright.update(deltaTime);
	}
}
