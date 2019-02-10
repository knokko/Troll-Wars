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
package nl.knokko.battle.move.myrre;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.effect.EffectGuardianDemon;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.render.battle.EffectRenderer;
import nl.knokko.util.Maths;
import nl.knokko.view.camera.Camera;

import static nl.knokko.util.Maths.AND;

public class MoveGuardianDemon implements BattleMove {
	
	private static final Vector3f BASE_COLOR = new Vector3f(0.25f, 0.05f, 0.05f);
	
	protected final ModelPart[] leftArm;
	protected final ModelPart[] rightArm;
	
	protected final ModelPart leftHand;
	protected final ModelPart rightHand;
	
	protected final BattleCreature caster;
	protected final BattleCreature target;
	
	protected final float sphereRenderRadius;
	protected final float cilRenderRadius;
	protected final float targetHeight;
	
	protected byte state;
	protected byte counter;
	protected float renderCounter;

	public MoveGuardianDemon(BattleCreature caster, float armRadius, float targetRadius, float targetHeight, BattleCreature target, ModelPart[] leftArm, ModelPart[] rightArm, ModelPart leftHand, ModelPart rightHand) {
		this.leftArm = leftArm;
		this.rightArm = rightArm;
		this.leftHand = leftHand;
		this.rightHand = rightHand;
		this.caster = caster;
		this.target = target;
		this.targetHeight = targetHeight;
		sphereRenderRadius = armRadius * 2;
		cilRenderRadius = targetRadius * 1.2f;
	}
	
	private static final float ROTATE_SPEED_0 = 4.5f;
	private static final float ROTATE_SPEED_1 = 1.5f;
	private static final float ROTATE_SPEED_3 = 3.5f;

	@Override
	public void update() {
		if(state == 0){//this state moves the arms to the basic position
			boolean result = AND(leftArm[0].movePitchTowards(270, ROTATE_SPEED_0), rightArm[0].movePitchTowards(270, ROTATE_SPEED_0));
			for(int index = 1; index < leftArm.length; index++){
				if(!AND(leftArm[index].movePitchTowards(0f, ROTATE_SPEED_0), rightArm[index].movePitchTowards(0f, ROTATE_SPEED_0)))
					result = false;
			}
			if(result)
				state = 1;
		}
		else if(state == 1){//this state slowly moves the arms upwards
			if(AND(leftArm[0].moveRollTowards(-130, ROTATE_SPEED_1), rightArm[0].moveRollTowards(130, ROTATE_SPEED_1))){
				state = 2;
				counter = 100;
				renderCounter = 0;
			}
		}
		else if(state == 2){//this state is the channeling of the power
			if(counter == 0){
				state = 3;
				target.getStatusEffects().add(new EffectGuardianDemon(caster));
			}
			else
				counter--;
		}
		else if(state == 3){//this state returns the arms to basic position
			if(AND(leftArm[0].moveRollTowards(-BodyMyrre.Rotation.ARM_ROLL, ROTATE_SPEED_3), rightArm[0].moveRollTowards(BodyMyrre.Rotation.ARM_ROLL, ROTATE_SPEED_3)))
				state = 4;
		}
	}
	
	private Vector3f getLeftHandCentre(Camera camera){
		Matrix4f matrix = leftHand.getMatrix(caster.getMatrix(camera));
		return new Vector3f(matrix.m20, matrix.m21, -matrix.m22);
	}
	
	private Vector3f getRightHandCentre(Camera camera){
		Matrix4f matrix = rightHand.getMatrix(caster.getMatrix(camera));
		return new Vector3f(matrix.m20, matrix.m21, -matrix.m22);
	}

	@Override
	public void render(Matrix4f viewMatrix, Camera camera) {
		if(state == 1){
			Vector3f color = getColor();
			EffectRenderer ef = Game.getEffectRenderer();
			ef.startSimpleShader(viewMatrix);
			ef.renderSphere(getLeftHandCentre(camera), sphereRenderRadius, color);
			ef.renderSphere(getRightHandCentre(camera), sphereRenderRadius, color);
			ef.stopSimpleShader();
			renderCounter += 0.01f;
		}
		if(state == 2){
			Vector3f color = getColor();
			EffectRenderer ef = Game.getEffectRenderer();
			Vector3f[] particlePositions = new Vector3f[10];
			BattleRenderProperties props = target.getRenderProperties();
			for(int extra = 0; extra < 10; extra++)
				particlePositions[extra] = getCilPosition(new Vector3f(props.getCentreX(camera), props.getCentreY(camera), props.getCentreZ(camera)), renderCounter + extra * 0.001f);
			ef.startSimpleShader(viewMatrix);
			ef.renderParticles(particlePositions, color);
			ef.renderSphere(getLeftHandCentre(camera), sphereRenderRadius, color);
			ef.renderSphere(getRightHandCentre(camera), sphereRenderRadius, color);
			ef.stopSimpleShader();
			renderCounter += 0.01f;
		}
	}
	
	private Vector3f getCilPosition(Vector3f c, float counter){
		float angle = counter * 20;
		return new Vector3f(c.x + cilRenderRadius * Maths.sin(angle), c.y + targetHeight * renderCounter, c.z + cilRenderRadius * Maths.cos(angle));
	}
	
	private Vector3f getColor(){
		return new Vector3f(BASE_COLOR.x + 0.1f * Maths.sin(renderCounter), BASE_COLOR.y + 0.05f * Maths.cos(renderCounter), BASE_COLOR.z - 0.05f * Maths.cos(renderCounter));
	}

	@Override
	public boolean isDone() {
		return state == 4;
	}
}