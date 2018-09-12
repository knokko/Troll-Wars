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
