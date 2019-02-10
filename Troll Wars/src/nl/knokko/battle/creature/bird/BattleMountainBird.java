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
package nl.knokko.battle.creature.bird;

import nl.knokko.animation.body.AnimatorBird;
import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.creature.SimpleBattleCreature;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.ItemMoveOption.Category;
import nl.knokko.battle.move.physical.bird.MoveBirdClawAttack;
import nl.knokko.battle.move.physical.bird.MoveBirdPickAttack;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.battle.render.properties.BirdRenderProperties;
import nl.knokko.inventory.Inventory;
import nl.knokko.model.body.BodyBird;
import nl.knokko.model.factory.creature.BirdFactory;
import nl.knokko.texture.painter.BirdPainter;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class BattleMountainBird extends SimpleBattleCreature implements MovingBattleCreature {
	
	protected BodyBird body;
	protected BirdPainter painter;
	
	protected float hitRadius;

	public BattleMountainBird(int level, BodyBird body, BirdPainter painter) {
		super("Mountain Bird", 300 + 100 * level, 150 + 100 * level, 40 + 10 * level, 40 + 10 * level, 15 + 2 * level, body, painter);
	}

	public BattleMountainBird(BitInput buffer) {
		super(buffer);
	}

	@Override
	public short getID() {
		return BattleCreature.Registry.ID_BIRD;
	}

	@Override
	public ElementalStatistics getElementStats() {
		return SimpleElementStats.BIRD;
	}

	@Override
	public int getArmor() {
		return 0;
	}

	@Override
	public int getResistance() {
		return 0;
	}

	@Override
	public boolean isPlayerControlled() {
		return false;
	}

	@Override
	public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
		if(Math.random() < 0.5)
			return new MoveBirdClawAttack(this, Battle.Selector.selectRandom(opposingTeam), models.get(0).getChildren()[3], models.get(0).getChildren()[4], body.bellyHeight() / 2, body.legLength());
		return new MoveBirdPickAttack(this, Battle.Selector.selectRandom(opposingTeam), models.get(0), models.get(0).getChildren()[0], body.snailLength());
	}

	@Override
	public ItemMoveOption[] getItems() {
		return null;
	}

	@Override
	public ItemMoveOption[] getItems(Category category) {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves() {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves(nl.knokko.battle.move.FightMoveOption.Category category) {
		return null;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	@Override
	protected BattleAnimationHelper createAnimator() {
		return new BattleAnimationHelper(body.legLength(), new AnimatorBird());
	}

	@Override
	protected void addBody(Object body, ModelPainter texture) {
		this.body = (BodyBird) body;
		this.painter = (BirdPainter) texture;
		models.add(BirdFactory.createModelBird(this.body, painter));
		hitRadius = Math.max(Math.max(this.body.bellyDepth(), this.body.bellyHeight()), this.body.bellyWidth()) / 2 + this.body.legLength();
	}

	@Override
	protected void addBody(BitInput bits) {
		body = BodyBird.Models.loadInstance(bits);
		painter = new BirdPainter(bits);
		models.add(BirdFactory.createModelBird(body, painter));
		hitRadius = Math.max(Math.max(body.bellyDepth(), body.bellyHeight()), body.bellyWidth()) / 2 + body.legLength();
	}

	@Override
	protected void saveBody(BitOutput bits) {
		body.save(bits);
		painter.save(bits);
	}
	
	@Override
	protected int getYOffset(){
		return (int) body.legLength();
	}

	@Override
	public String getActiveArm() {
		return null;
	}

	@Override
	public void swapArm() {
		throw new UnsupportedOperationException("Birds can't swap arms.");
	}

	@Override
	public float getSpeed() {
		return BattleCreature.MoveSpeed.BIRD;
	}

	@Override
	protected BattleRenderProperties createRenderProperties() {
		return new BirdRenderProperties(this, body);
	}
}
