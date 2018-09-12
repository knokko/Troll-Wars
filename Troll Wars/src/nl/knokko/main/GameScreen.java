package nl.knokko.main;

import org.lwjgl.opengl.*;

public class GameScreen {
	
	private static long updateTime;
	
	private static int updateCalls;
	
	public static void openScreen(){
		/*
		try {
			ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
			int size = getSize();
			Display.setDisplayMode(new DisplayMode(size, size));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Troll Wars");
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
		*/
		Game.getWindow().open("Troll Wars", 800, 800, true);
	}
	
	public static void updateScreen(){
		/*
		long startTime = System.nanoTime();
		Display.update();
		long endTime = System.nanoTime();
		updateTime += endTime - startTime;
		updateCalls++;
		Display.sync(fps());
		*/
		Game.getWindow().render();
		Display.sync(fps());
	}
	
	public static void closeScreen(){
		Game.getWindow().close();
		//Display.destroy();
		System.out.println("Average screen update time is " + (updateTime / updateCalls) / 1000 + " microseconds.");
	}
	
	public static int fps(){
		return 64;
	}
	
	public static int getWidth(){
		return Display.getWidth();
	}
	
	public static int getHeight(){
		return Display.getHeight();
	}
}
