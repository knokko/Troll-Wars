/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.main;

import nl.knokko.gui.keycode.KeyCode;

public class Options {
	
	/**
	 * The amount of different textures for each side per tile.
	 */
	public static final int TEXTURES_PER_TILE = 10;
	
	private static Options instance;
	
	private Difficulty difficulty = Difficulty.INTRO;
	
	public int keyNorth = KeyCode.KEY_UP;
	public int keyEast = KeyCode.KEY_RIGHT;
	public int keySouth = KeyCode.KEY_DOWN;
	public int keyWest = KeyCode.KEY_LEFT;
	
	public int keyCameraRight = KeyCode.KEY_D;
	public int keyCameraLeft = KeyCode.KEY_A;
	public int keyCameraUp = KeyCode.KEY_W;
	public int keyCameraDown = KeyCode.KEY_S;
        
        public int keyZoomIn = KeyCode.KEY_MINUS_BASE;
        public int keyZoomOut = KeyCode.KEY_EQUALS;
	
	public int keyInteract = KeyCode.KEY_SPACE;
	
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
