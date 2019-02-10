/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.util.color.ColorSpecial;

public class SpecialGuiShader extends ShaderProgram {
    
    public static final String VERTEX_FILE = "nl/knokko/gui/shader/vertex.shader";
    public static final String FRAGMENT_FILE = "nl/knokko/shaders/specialgui.fshad";
    
    public static final SpecialGuiShader SPECIAL_GUI_SHADER = new SpecialGuiShader();
     
    private int locationScreenPosition;
	private int locationSize;
	private int locationUV;
    
    private int locationColorMyrmora;
 
    private SpecialGuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    
    public void loadPosition(float x, float y){
		loadVector(locationScreenPosition, x, y);
	}
	
	public void loadSize(float width, float height){
		loadVector(locationSize, width, height);
	}
	
	public void loadBounds(float minU, float minV, float maxU, float maxV) {
		loadVector(locationUV, minU, minV, maxU, maxV);
	}
 
    @Override
    protected void getAllUniformLocations() {
    	locationScreenPosition = getUniformLocation("screenPosition");
    	locationSize = getUniformLocation("size");
    	locationColorMyrmora = getUniformLocation("colorMyrmora");
    }
 
    @Override
    protected void bindAttributes() {
        bindAttribute(0, "modelPosition");
    }
    
    public void updateColors() {
    	ColorSpecial.updateColors();
    	Vector3f colorMyrmora = ColorSpecial.MYRMORA.toVector();
    	loadVector(locationColorMyrmora, colorMyrmora.x, colorMyrmora.y, colorMyrmora.z, 1);
    }
}