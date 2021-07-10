package controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class SelectedCardProcessor extends SpellProcessor {

    public SelectedCardProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp == null) return MainPhase.NO_CARD_SELECTED_YET.toString();

        CardLocation location = cardProp.getLocation();
        boolean doesBelongToCurrent = cardProp.doesBelongToCurrent();
        if (location.equals(CardLocation.SPELL_ZONE)
                || doesBelongToCurrent)
            return MainPhase.CANT_HIRE_CARD.toString();

        if (!(cardProp.getCard() instanceof MagicCard)) {
            return MainPhase.CANT_SET_MONSTER_INSTEAD_OF_SPELL.toString();
        }


        return super.process(game);
    }

}
