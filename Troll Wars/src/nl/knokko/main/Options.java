package nl.knokko.main;

import org.lwjgl.input.Keyboard;

public class Options {
	
	/**
	 * The amount of different textures for each side per tile.
	 */
	public static final int TEXTURES_PER_TILE = 10;
	
	private static Options instance;
	
	private Difficulty difficulty = Difficulty.INTRO;
	
	public int keyNorth = Keyboard.KEY_UP;
	public int keyEast = Keyboard.KEY_RIGHT;
	public int keySouth = Keyboard.KEY_DOWN;
	public int keyWest = Keyboard.KEY_LEFT;
	
	public int keyCameraRight = Keyboard.KEY_D;
	public int keyCameraLeft = Keyboard.KEY_A;
	public int keyCameraUp = Keyboard.KEY_W;
	public int keyCameraDown = Keyboard.KEY_S;
	
	public int keyInteract = Keyboard.KEY_SPACE;
	
	public int pixelsPerMeter = 2;
	
	public Difficulty getDifficulty(){
		return difficulty;
	}
	
	public static Options getInstance(){
		if(instance == null)
			instance = new Options();
		return instance;
	}
	
	public static enum Difficulty {
		
		INTRO(0.3f, 0.8f, true, true),
		EASY(0.6f, 0.7f, true, true),
		NORMAL(1f, 1f, false, true),
		HARD(2f, 1.1f, false, false),
		EXTREME(3f, 1.3f, false, false);
		
		private final float factor;
		private final float speedFactor;
		
		private final boolean runAlways;
		private final boolean runOnTurn;
		
		Difficulty(float factor, float speedFactor, boolean runAlways, boolean runOnTurn){
			this.runAlways = runAlways;
			this.runOnTurn = runOnTurn;
			this.factor = factor;
			this.speedFactor = speedFactor;
		}
		
		public boolean canRunAlways(){
			return runAlways;
		}
		
		public boolean canRunWhileOnTurn(){
			return runOnTurn;
		}
		
		public int determineStrength(int original){
			return (int) (original * factor);
		}
		
		public int determineSpirit(int original){
			return (int) (original * factor);
		}
		
		public int determineTurnSpeed(int original){
			return (int) (original * speedFactor);
		}
		
		public long determineHealth(long original){
			return (long) (original * factor);
		}
		
		public long determineMana(long original){
			return (long) (original * factor);
		}
		
		public int determineEnergy(int original){
			return (int) (original * factor);
		}
		
		public int determineRage(int original){
			return original;
		}
		
		public int determineFocus(int original){
			return original;
		}
	}
}
