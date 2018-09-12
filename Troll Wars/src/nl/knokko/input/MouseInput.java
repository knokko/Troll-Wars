package nl.knokko.input;

import java.util.ArrayList;

import nl.knokko.main.GameScreen;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public final class MouseInput {
	
	private static byte clickCooldown;
	
	private static ArrayList<MouseMoveEvent> moves = new ArrayList<MouseMoveEvent>();
	private static ArrayList<MouseClickEvent> clicks = new ArrayList<MouseClickEvent>();
	private static ArrayList<MouseScrollEvent> scrolls = new ArrayList<MouseScrollEvent>();
	
	public static void update(){
		moves.clear();
		clicks.clear();
		scrolls.clear();
		while(Mouse.next()){
			if(Mouse.getEventDWheel() != 0)
				scrolls.add(new MouseScrollEvent(Mouse.getEventDWheel()));
			if(Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0)
				moves.add(new MouseMoveEvent(Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventDX(), Mouse.getEventDY()));
			if(Mouse.getEventButton() != -1 && clickCooldown == 0){
				clicks.add(new MouseClickEvent(getRelativeX(Mouse.getEventX()), getRelativeY(Mouse.getEventY()), Mouse.getEventButton(), Mouse.getEventButtonState()));
				clickCooldown = (byte) (GameScreen.fps() / 2);
			}
		}
		if(clickCooldown > 0)
			clickCooldown--;
	}
	
	public static ArrayList<MouseMoveEvent> getMouseMoves(){
		return moves;
	}
	
	public static ArrayList<MouseClickEvent> getMouseClicks(){
		return clicks;
	}
	
	public static ArrayList<MouseScrollEvent> getMouseScrolls(){
		return scrolls;
	}
	
	public static int getCurrentX(){
		return Mouse.getX();
	}
	
	public static int getCurrentY(){
		return Mouse.getY();
	}
	
	public static float getRelativeX(){
		return getRelativeX(getCurrentX());
	}
	
	public static float getRelativeX(int screenX){
		return (float) screenX * 2f / Display.getWidth() - 1f;
	}
	
	public static float getRelativeY(){
		return getRelativeY(getCurrentY());
	}
	
	public static float getRelativeY(int screenY){
		return (float) screenY * 2f / Display.getHeight() - 1f;
	}
	
	public static float getX(){
		return getRelativeX(getCurrentX());
	}
	
	public static float getY(){
		return getRelativeY(getCurrentY());
	}
}
