package nl.knokko.input;

public class KeyPressedEvent {
	
	private final int key;

	public KeyPressedEvent(int key) {
		this.key = key;
	}
	
	public int getKey(){
		return key;
	}
}
