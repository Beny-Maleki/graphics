package controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.SpellProcessor;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class SetSpellProcessor extends SpellProcessor {
    public SetSpellProcessor(SpellProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        MagicCard magicCard = (MagicCard) game.getCardProp().getCard();
        PlayerBoard board = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        if (magicCard.getMagicAttribute() == MagicAttribute.FIELD) {
            MagicHouse magicHouse = board.getFieldHouse();
            MagicCard previousFieldSpell = magicHouse.getMagicCard();
            if (previousFieldSpell != null) {
                board.moveCardToGraveYard(0, CardLocation.FIELD_ZONE);
            }
            return placeCardInBoard(game, magicCard, magicHouse);
        }
        for (MagicHouse magicHouse : board.getMagicHouse()) {
            if (magicHouse.getMagicCard() == null) {
                return placeCardInBoard(game, magicCard, magicHouse);
            }
        }
        try {
            throw new Exception("wrong process");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String placeCardInBoard(Game game, MagicCard magicCard, MagicHouse magicHouse) {
        magicHouse.setMagicCard(magicCard);
        magicHouse.setState(MagicHouseVisibilityState.H);
        game.setCardProp(null);
        game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand().remove(magicCard);
        return MainPhase.SET_SUCCESSFULLY.toString();
    }
}
