package nl.knokko.input;

public class MouseMoveEvent {
	
	private final float ax;
	private final float ay;
	
	private final float dx;
	private final float dy;

	public MouseMoveEvent(float ax, float ay, float dx, float dy) {
		this.ax = ax;
		this.ay = ay;
		this.dx = dx;
		this.dy = dy;
	}
	
	public float getToX(){
		return ax;
	}
	
	public float getToY(){
		return ay;
	}
	
	public float getFromX(){
		return ax - dx;
	}
	
	public float getFromY(){
		return ay - dy;
	}
	
	public float getDeltaX(){
		return dx;
	}
	
	public float getDeltaY(){
		return dy;
	}
}
