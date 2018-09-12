package nl.knokko.input;

public class KeyPressedCharEvent {
	
	private final char character;
	
	public KeyPressedCharEvent(char character) {
		this.character = character;
	}
	
	public char getCharacter(){
		return character;
	}
}