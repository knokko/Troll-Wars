package nl.knokko.gui.button;

import java.awt.Color;
import java.awt.Font;

import nl.knokko.gui.util.TextBuilder.HorAlignment;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.TextBuilder.VerAlignment;

public class ButtonProps {

	public static final Properties MAIN_MENU = new Properties(new Font("TimesRoman", Font.PLAIN, 50), 
			new Color(0, 0, 50), new Color(50, 50, 200), new Color(10, 10, 40), HorAlignment.MIDDLE, 
			VerAlignment.MIDDLE, 0.03f, 0.06f, 0.03f, 0.06f, 512, 128);
	public static final Properties MAIN_MENU_HOVER = new Properties(new Font("TimesRoman", Font.PLAIN, 50), 
			new Color(250, 0, 250), new Color(60, 60, 250), new Color(15, 15, 70), HorAlignment.MIDDLE, 
			VerAlignment.MIDDLE, 0.03f, 0.06f, 0.03f, 0.06f, 512, 128);
	public static final Properties MAIN_MENU_SELECTED = new Properties(new Font("TimesRoman", Font.PLAIN, 50), 
			new Color(250, 0, 250), new Color(60, 60, 250), new Color(250, 0, 250), HorAlignment.MIDDLE, 
			VerAlignment.MIDDLE, 0.03f, 0.06f, 0.03f, 0.06f, 512, 128);
	public static final Properties MAIN_MENU_TITLE = new Properties(new Font("Lucida Calligraphy", Font.PLAIN, 60),
			new Color(70, 0, 170), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), HorAlignment.MIDDLE,
			VerAlignment.MIDDLE, 0f, 0f, 0f, 0f, 1024, 256);
}