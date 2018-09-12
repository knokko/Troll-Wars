package nl.knokko.shaders;

import org.lwjgl.util.vector.Vector2f;

public class GuiShader extends ShaderProgram {
    
    public static final String VERTEX_FILE = "nl/knokko/shaders/gui.vshad";
    public static final String FRAGMENT_FILE = "nl/knokko/shaders/gui.fshad";
    
    public static final GuiShader GUI_SHADER = new GuiShader();
     
    private int locationCentrePosition;
    private int locationScale;
 
    private GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    public void loadCentrePosition(Vector2f position){
    	loadVector(locationCentrePosition, position);
    }
    
    public void loadScale(Vector2f scale){
    	loadVector(locationScale, scale);
    }
 
    @Override
    protected void getAllUniformLocations() {
    	locationCentrePosition = getUniformLocation("centrePosition");
    	locationScale = getUniformLocation("scale");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "modelPosition");
    }
}