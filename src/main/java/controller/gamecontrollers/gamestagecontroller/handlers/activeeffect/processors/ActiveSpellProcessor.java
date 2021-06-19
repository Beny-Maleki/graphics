package controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectProcessor;
import model.cards.cardsEnum.Magic.MagicSpeed;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.CardLocation;
import model.enums.GameEnums.GamePhaseEnums.General;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.events.eventChildren.ManuallyActivation;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class ActiveSpellProcessor extends ActiveEffectProcessor {


    public ActiveSpellProcessor(ActiveEffectProcessor processor) {
        super(processor);
    }

    public String process(Game game) {
        SelectedCardProp cardProp = game.getCardProp();
        MagicCard magicCard = (MagicCard) cardProp.getCard();
        if (cardProp.getLocation().equals(CardLocation.PLAYER_HAND)) {
            if (magicCard.getMagicSpeed().equals(MagicSpeed.UNLIMITED))
                return General.MAGIC_SPELL_SPEED_2.toString();
            else {
                if (game.getPlayer(SideOfFeature.CURRENT).getBoard().numberOfFullHouse("spell") == 5) {
                    return General.SPELL_CARD_ZONE_FULL.toString();
                }
                else{
                    for (MagicHouse house : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMagicHouse()) {
                        if (house.getMagicCard() == null){
                            house.setMagicCard(magicCard);
                            house.setState(MagicHouseVisibilityState.O);
                            break;
                        }
                    }
                }
            }
        }
        ManuallyActivation.getInstance().activeEffects(game);
        return General.SPELL_ACTIVATED_SUCCESSFULLY.toString();
    }
}
