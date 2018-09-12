package nl.knokko.input;

public class MouseClickEvent {
	
	private final float x;
	private final float y;
	
	private final int button;

	public MouseClickEvent(float x, float y, int button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public int getButton(){
		return button;
	}
}
