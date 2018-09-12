package nl.knokko.input;

public class MouseScrollEvent {
	
	private float deltaScroll;

	public MouseScrollEvent(float deltaScroll) {
		this.deltaScroll = deltaScroll;
	}
	
	public float getDeltaScroll(){
		return deltaScroll;
	}
}
