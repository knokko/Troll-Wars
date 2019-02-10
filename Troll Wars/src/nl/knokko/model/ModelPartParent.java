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
package nl.knokko.model;

import java.util.Arrays;

import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;

import org.lwjgl.util.vector.Vector3f;

public class ModelPartParent extends ModelPart {
	
	private ModelPart[] children;

	public ModelPartParent(AbstractModel model, ModelTexture texture, Vector3f relativePosition, ModelPart... children) {
		super(model, texture, relativePosition);
		this.children = children;
		for(ModelPart child : this.children)
			child.setParent(this);
	}
	
	public ModelPartParent(AbstractModel model, ModelTexture texture, Vector3f relativePosition, float pitch, float yaw, float roll, ModelPart... children) {
		super(model, texture, relativePosition, pitch, yaw, roll);
		this.children = children;
		for(ModelPart child : this.children)
			child.setParent(this);
	}
	
	@Override
	public ModelPart[] getChildren(){
		return children;
	}
	
	@Override
	public void addChild(ModelPart child){
		children = Arrays.copyOf(children, children.length + 1);
		children[children.length - 1] = child;
	}
	
	@Override
	public ModelPartParent clone(){
		return new ModelPartParent(getModel(), getTexture(), new Vector3f(getRelativePosition()), rotationX, rotationX, rotationX, children);
	}
}
