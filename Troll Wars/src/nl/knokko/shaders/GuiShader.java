/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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