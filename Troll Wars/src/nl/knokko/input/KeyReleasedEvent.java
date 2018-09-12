package nl.knokko.input;

public class KeyReleasedEvent {
	
	private final int key;

	public KeyReleasedEvent(int key) {
		this.key = key;
	}
	
	public int getKey(){
		return key;
	}
}
