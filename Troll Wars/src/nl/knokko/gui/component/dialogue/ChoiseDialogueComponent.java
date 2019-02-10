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
package nl.knokko.gui.component.dialogue;

import java.awt.image.BufferedImage;

import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.dialogue.GuiDialogue;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.gui.texture.loader.GuiTextureLoader;
import nl.knokko.story.dialogue.ChoiseDialoguePart;
import nl.knokko.story.dialogue.ChoiseDialogueText;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.util.resources.Resources;

public class ChoiseDialogueComponent extends AbstractGuiComponent {
	
	private final ChoiseDialoguePart part;
	private final GuiDialogue gui;
	
	protected GuiTexture[] defaultTextures;
	protected GuiTexture[] hoverTextures;
	protected GuiTexture upperTexture;
	protected GuiTexture lowerTexture;
	protected DialogueText[] availableTexts;
	protected float[] heights;
	
	public ChoiseDialogueComponent(ChoiseDialoguePart part, GuiDialogue gui) {
		this.part = part;
		this.gui = gui;
	}

	@Override
	public void init() {
		DialogueText[] texts = part.getText();
		int availableTextCount = 0;
		for (DialogueText text : texts) {
			ChoiseDialogueText cd = (ChoiseDialogueText) text;
			if (cd.getCondition().isTrue()) {
				availableTextCount++;
			}
		}
		availableTexts = new DialogueText[availableTextCount];
		int availableIndex = 0;
		for (DialogueText text : texts) {
			ChoiseDialogueText cd = (ChoiseDialogueText) text;
			if (cd.getCondition().isTrue()) {
				availableTexts[availableIndex++] = cd;
			}
		}
		
		DialogueText[] hoverTexts = new DialogueText[availableTextCount];
		for (int index = 0; index < availableTextCount; index++) {
			ChoiseDialogueText cd = (ChoiseDialogueText) availableTexts[index];
			hoverTexts[index] = new ChoiseDialogueText(cd.getText(), cd.getHoldColor(), cd.getHoldColor(), cd.getFont());
		}
		
		int[] intTextHeights = new int[availableTextCount];
		BufferedImage defaultImage = Resources.createDialogueImage(part.getBackGround(), part.getPortrait(), 
				part.getTitle(), availableTexts, intTextHeights, false);
		BufferedImage hoverImage = Resources.createDialogueImage(part.getBackGround(), part.getPortrait(), 
				part.getTitle(), hoverTexts, null, false);
		
		int[] intHeights = new int[availableTextCount + 1];
		for (int index = 0; index < availableTextCount; index++) {
			intHeights[index] = intTextHeights[index] - 30;
		}
		intHeights[availableTextCount] = intTextHeights[availableTextCount - 1] + 20;
		heights = new float[availableTextCount + 1];
		for (int index = 0; index <= availableTextCount; index++) {
			heights[index] = 1f - (float) intHeights[index] / defaultImage.getHeight();
		}
		defaultTextures = new GuiTexture[availableTextCount];
		hoverTextures = new GuiTexture[availableTextCount];
		GuiTextureLoader tl = state.getWindow().getTextureLoader();
		int maxX = defaultImage.getWidth() - 1;
		for (int index = 0; index < availableTextCount; index++) {
			int minY = intHeights[index];
			int maxY = intHeights[index + 1];
			defaultTextures[index] = tl.loadTexture(defaultImage, 0, minY, maxX, maxY);
			hoverTextures[index] = tl.loadTexture(hoverImage, 0, minY, maxX, maxY);
		}
		upperTexture = tl.loadTexture(defaultImage, 0, 0, maxX, intHeights[0]);
		lowerTexture = tl.loadTexture(defaultImage, 0, intHeights[availableTextCount], maxX, defaultImage.getHeight() - 1);
	}

	@Override
	public void update() {}
	
	private static final float RENDER_MARGIN = 0;

	@Override
	public void render(GuiRenderer renderer) {
		boolean shouldRenderHover = state.isMouseOver();
		float y = state.getMouseY();
		renderer.renderTexture(upperTexture, 0, heights[0], 1, 1);
		for (int index = 0; index < availableTexts.length; index++) {
			if (shouldRenderHover && y >= heights[index + 1] && y <= heights[index]) {
				shouldRenderHover = false;
				renderer.renderTexture(hoverTextures[index], 0, heights[index + 1] - RENDER_MARGIN, 1, heights[index] + RENDER_MARGIN);
			} else {
				renderer.renderTexture(defaultTextures[index], 0, heights[index + 1] - RENDER_MARGIN, 1, heights[index] + RENDER_MARGIN);
			}
		}
		renderer.renderTexture(lowerTexture, 0, 0, 1, heights[heights.length - 1]);
	}

	@Override
	public void click(float x, float y, int button) {
		if (y >= heights[availableTexts.length] && y <= heights[0]) {
			for (int index = 0; index < availableTexts.length; index++) {
				if (y >= heights[index + 1]) {
					availableTexts[index].onClick();
					if (gui.getDialogue().current() != part) {
						gui.changePart();
					}
					return;
				}
			}
		}
	}

	@Override
	public void clickOut(int button) {}

	@Override
	public boolean scroll(float amount) {
		return false;
	}

	@Override
	public void keyPressed(int keyCode) {}

	@Override
	public void keyPressed(char character) {}

	@Override
	public void keyReleased(int keyCode) {}
}