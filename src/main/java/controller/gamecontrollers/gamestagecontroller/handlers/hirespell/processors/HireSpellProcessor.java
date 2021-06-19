package controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.gamemodel.Game;

public class HireSpellProcessor extends SpellProcessor {
    public HireSpellProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        MagicCard magicCard = (MagicCard) game.getCardProp().getCard();
        for (MagicHouse magicHouse : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMagicHouse()) {
            if (magicHouse.getMagicCard() == null) {
                magicHouse.setMagicCard(magicCard);
                magicHouse.setState(MagicHouseVisibilityState.H);
                game.setCardProp(null);
                game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand().remove(magicCard);
                return MainPhase.SET_SUCCESSFULLY.toString();
            }
        }
        try {
            throw new Exception("wrong process");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
