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
