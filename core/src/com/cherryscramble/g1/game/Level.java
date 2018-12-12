/**
 * Level class properly creates the game's level based off of the image template
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.cherryscramble.g1.objects.AbstractGameObject;
import com.cherryscramble.g1.objects.Cherry;
import com.cherryscramble.g1.objects.GoldenCherry;
import com.cherryscramble.g1.objects.Ground;
import com.cherryscramble.g1.objects.Player;
import com.cherryscramble.g1.objects.Shrub;
import com.cherryscramble.g1.objects.Stump;
import com.cherryscramble.g1.objects.TreeTop;
import com.cherryscramble.g1.objects.TrunkWallLeft;
import com.cherryscramble.g1.objects.TrunkWallRight;
import com.cherryscramble.g1.objects.WoodPlatform;

public class Level {
	public static final String TAG = Level.class.getName();
	
	// ===== Player =====
	public Player player;
	
	// ===== Object Assets ======
	public Array<Ground> grounds;					// Ground Object Array
	public Array<TrunkWallLeft> leftTrunkWalls;		// Left TrunkWall Array
	public Array<TrunkWallRight> rightTrunkWalls;   // Right TrunkWall Array
	public Array<WoodPlatform> platforms;			// Platform Array
	public Array<Stump> stumps;						// Stump Array
	
	// ===== Pick Ups =====
	public Array<Cherry> cherries;					// Cherry pickups
	public Array<GoldenCherry> goldcherries;		// Gold Cherry pickups
	
	// ===== Decoration Assets =====
	public Shrub shrub;								// Shrub Background
	public TreeTop treetop;							// TreeTop Foreground
	
	/**
	 * Color coordination. Sets colors to specific kind of object
	 */
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), 				// Empty spaces are Black.
		GROUND(181,230,29),				// Ground
		TRUNKWALL_LEFT(153,217,234),	// TrunkWall Left
		TRUNKWALL_RIGHT(185,122,87),	// TrunkWall Right
		WOOD_PLATFORM(255,201,14),		// Wooden Platform
		STUMP(79,39,0),					// Stumps
		PLAYER_SPAWN(255,255,255),		// Player Character Spawn Point
		CHERRY_SPAWN(151,0,0),			// Cherry Spawners
		GOLD_CHERRY(170,132,0);			// Gold Cherry
		
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
		
		// Player
		player = null;	// Player character
		
		// Assets
		grounds = new Array<Ground>(); 					// Ground
		leftTrunkWalls = new Array<TrunkWallLeft>();	// Left TrunkWall
		rightTrunkWalls = new Array<TrunkWallRight>();	// Right TrunkWall
		platforms = new Array<WoodPlatform>();			// Wooden Platforms
		stumps = new Array<Stump>();					// Stumps
		
		// Pickups
		cherries = new Array<Cherry>();				// Standard Cherries
		goldcherries = new Array<GoldenCherry>();	// Golden Cherries
				
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
					if (lastPixel != currentPixel) {
						obj = new Ground();
						offsetHeight = -2.0f;
						obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
						grounds.add((Ground)obj);
					} 
					else {
						grounds.get(grounds.size - 1).increaseLength(1);
					}
				}
				
				//TrunkWall Left
				else if (BLOCK_TYPE.TRUNKWALL_LEFT.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new TrunkWallLeft();
						obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
						leftTrunkWalls.add((TrunkWallLeft)obj);
					} 
					else {
						leftTrunkWalls.get(leftTrunkWalls.size - 1).increaseLength(1);
					}
				}
				
				//TrunkWall Right
				else if (BLOCK_TYPE.TRUNKWALL_RIGHT.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new TrunkWallRight();
						obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
						rightTrunkWalls.add((TrunkWallRight)obj);
					} 
					else {
						rightTrunkWalls.get(rightTrunkWalls.size - 1).increaseLength(1);
					}
				}
				
				//Wooden Platform
				else if (BLOCK_TYPE.WOOD_PLATFORM.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new WoodPlatform();
						obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
						platforms.add((WoodPlatform)obj);
					} 
					else {
						platforms.get(platforms.size - 1).increaseLength(1);
					}
				}
				
				//Stump
				else if (BLOCK_TYPE.STUMP.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new Stump();
						obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
						stumps.add((Stump)obj);
					} 
					else {
						stumps.get(stumps.size - 1).increaseLength(1);
					}
				}
				
				//TrunkWall Right
				else if (BLOCK_TYPE.PLAYER_SPAWN.sameColor(currentPixel)) {
					obj = new Player();
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					player = (Player) obj;
				}
				
				else if (BLOCK_TYPE.CHERRY_SPAWN.sameColor(currentPixel)) {
					obj = new Cherry();
					obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
					cherries.add((Cherry)obj);
				}
				
				else if (BLOCK_TYPE.GOLD_CHERRY.sameColor(currentPixel)) {
					obj = new GoldenCherry();
					obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight);
					goldcherries.add((GoldenCherry)obj);
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
		
		//Finally some decoration
		
		//Shrub background at the bottom
		shrub = new Shrub();
		shrub.position.set(1, 2);
		
		//TreeTop foreground at the top
		treetop = new TreeTop();
		treetop.position.set(0, 15);	
		
		//Free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	/**
	 * Renders the objects into the level.
	 */
	public void render (SpriteBatch batch) {
		// -=-= BACKGROUND =-=-
		shrub.render(batch); // Render Shrub 
		
		
		// -=-= SAME LAYER =-=-
		
		//Draw Ground objects
		for (Ground ground : grounds)
			ground.render(batch);
		
		//Draw the TrunkWalls
		for (TrunkWallLeft trunkwallleft : leftTrunkWalls)
			trunkwallleft.render(batch);
		
		for (TrunkWallRight trunkwallright : rightTrunkWalls)
			trunkwallright.render(batch);
		
		// Draw the wooden platforms
		for (WoodPlatform woodplatforms : platforms)
			woodplatforms.render(batch);
		
		// Draw the stumps
		for (Stump stump: stumps)
			stump.render(batch);
		
		// Player should be the last thing spawned in the same layer
		player.render(batch);
		
		// Nah, its cherries that are last
		for (Cherry cherry: cherries)
			cherry.render(batch);
		
		for (GoldenCherry gcherry: goldcherries)
			gcherry.render(batch);
			
		// -=-= FOREGROUND =-=-
		treetop.render(batch);	// Draw TreeTops
	}
	
	/**
	 * Updates the level each frame as the game progresses
	 * @param deltaTime
	 */
	public void update (float deltaTime) {
		//Player
		player.update(deltaTime);
		
		//Ground
		for(Ground ground : grounds)
			ground.update(deltaTime);
		//TrunkWall Left
		for (TrunkWallLeft trunkwallleft : leftTrunkWalls)
			trunkwallleft.update(deltaTime);
		//TrunkWall Right
		for (TrunkWallRight trunkwallright : rightTrunkWalls)
			trunkwallright.update(deltaTime);
		//Wood Platforms
		for (WoodPlatform woodplatforms : platforms)
			woodplatforms.update(deltaTime);
		//Stumps
		for (Stump stump: stumps)
			stump.update(deltaTime);
		
		// Cherries
		for (Cherry cherry:cherries)
			cherry.update(deltaTime);
		
		for (GoldenCherry gcherry: goldcherries)
			gcherry.update(deltaTime);
	}
}
