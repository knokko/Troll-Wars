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
package nl.knokko.players.moves;

import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.players.moves.AbstractPlayerMoves.BasicMoveOption.*;

import static nl.knokko.model.body.BodyTroll.Models.BATTLE_GOTHROK;

public class PlayerMovesGothrok extends AbstractPlayerMoves {
	
	private static final PunchMoveOption PUNCH = new PunchMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	private static final StabMoveOption STAB_UP = new StabMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	private static final PrickMoveOption PRICK = new PrickMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	private static final SmashMoveOption SMASH = new SmashMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	private static final SwingMoveOption SWING = new SwingMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	private static final SlashMoveOption SLASH = new SlashMoveOption(BATTLE_GOTHROK, BATTLE_GOTHROK);
	
	private static final FightMoveOption[] DEFAULT_MOVES = {PUNCH, STAB_UP, PRICK, SMASH, SWING, SLASH};
	private static final PlayerMoveOption[] LEARNABLE_MOVES = {};

	public PlayerMovesGothrok() {}

	@Override
	protected FightMoveOption[] getDefaultMoves() {
		return DEFAULT_MOVES;
	}

	@Override
	protected PlayerMoveOption[] getLearnableMoves() {
		return LEARNABLE_MOVES;
	}
}
