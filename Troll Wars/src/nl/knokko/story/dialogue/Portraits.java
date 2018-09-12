package nl.knokko.story.dialogue;

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.resources.Resources;

public final class Portraits {
	
	public static final ImageTexture GOTHROK = get("gothrok");
	
	public static final ImageTexture HUMAN_MALE_BLANC = get("human male blanc");
	public static final ImageTexture HUMAN_MALE_BROWN = get("human male brown");
	
	public static final ImageTexture HUMAN_MALE_BLANC_WARRIOR = get("human male blanc warrior");
	public static final ImageTexture HUMAN_MALE_BROWN_WARRIOR = get("human male brown warrior");
	
	public static final ImageTexture HUMAN_MALE_BLANC_PALADIN = get("human male blanc paladin");
	
	public static final ImageTexture UNKNOWN = get("unknown");
	public static final ImageTexture EMPTY = get("empty");
	
	private static ImageTexture get(String name){
		return new ImageTexture(Resources.loadPortrait(name), false);
	}
}