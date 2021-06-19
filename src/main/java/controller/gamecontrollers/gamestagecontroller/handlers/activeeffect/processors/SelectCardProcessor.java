package controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectProcessor;
import model.cards.cardsEnum.Magic.MagicSpeed;
import model.cards.cardsEnum.Magic.MagicType;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.GamePhaseEnums.General;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class SelectCardProcessor extends ActiveEffectProcessor {

    public SelectCardProcessor(ActiveEffectProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        if (cardProp == null)
            return General.NO_CARD_SELECT.toString();
        if (cardProp.getSide().equals(SideOfFeature.OPPONENT)) {
            return General.CANT_ACTIVE_OPPONENT_CARD.toString();
        }
        if (cardProp.getCard() instanceof MagicCard) {
            MagicCard card = (MagicCard) cardProp.getCard();
            if (!card.getTypeOfMagic().equals(MagicType.SPELL))
                return General.NOT_SPELL_CARD.toString();
        }
        MagicCard card = (MagicCard) cardProp.getCard();
        if (card.getMagicSpeed().equals(MagicSpeed.LIMITED) &&
                (!game.getGameMainStage().equals(GameMainStage.FIRST_MAIN_PHASE) &&
                        !game.getGameMainStage().equals(GameMainStage.SECOND_MAIN_PHASE))) {
            return General.SPELL_ACTIVATE_NOT_IN_MAIN_PHASE.toString();
        }
        return super.process(game);
    }
}
