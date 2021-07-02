package controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class BoardProcessor extends SpellProcessor {
    public BoardProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        MagicCard magicCard = (MagicCard) game.getCardProp().getCard();
        if (magicCard.getMagicAttribute() != MagicAttribute.FIELD) {
            PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            if (board.numberOfFullHouse("spell") == 5) return MainPhase.BOARD_IS_FULL.toString();
        }
        return super.process(game);
    }
}
