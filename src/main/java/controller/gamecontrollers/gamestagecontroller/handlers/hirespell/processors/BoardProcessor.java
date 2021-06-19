package controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class BoardProcessor extends SpellProcessor {
    public BoardProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        if (board.numberOfFullHouse("spell") == 5) return MainPhase.BOARD_IS_FULL.toString();
        return super.process(game);
    }
}
