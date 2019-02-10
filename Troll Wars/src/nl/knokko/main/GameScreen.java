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
		Game.getWindow().open("Troll Wars", true);
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
