package controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.activeeffect.ActiveEffectProcessor;
import model.cards.cardsEnum.Magic.MagicAttribute;
import model.cards.cardsEnum.Magic.MagicType;
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
        if (((MagicCard) cardProp.getCard()).isActivated()) return General.IS_ACTIVATED_BEFORE.toString();
        if (cardProp.getLocation().equals(CardLocation.PLAYER_HAND)) {//activation of magic from hand
            if (magicCard.getTypeOfMagic() != MagicType.SPELL || magicCard.getMagicAttribute() == MagicAttribute.QUICK_PLAY) {
                return General.MAGIC_SPELL_SPEED_2.toString();
            } else {
                if (game.getPlayer(SideOfFeature.CURRENT).getBoard().numberOfFullHouse("spell") == 5) {
                    return General.SPELL_CARD_ZONE_FULL.toString();
                } else {
                    if (magicCard.getMagicAttribute() == MagicAttribute.FIELD) {
                        MagicHouse house = game.getPlayer(SideOfFeature.CURRENT).getBoard().getFieldHouse();
                        MagicCard previousFieldSpell = house.getMagicCard();
                        if (previousFieldSpell != null) {
                            game.getPlayer(SideOfFeature.CURRENT).getBoard().moveCardToGraveYard(0, CardLocation.FIELD_ZONE);
                        }
                        setCardOnBoard(game, magicCard, house);
                        game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand().remove(cardProp.getCard());
                    } else {
                        for (MagicHouse house : game.getPlayer(SideOfFeature.CURRENT).getBoard().getMagicHouse()) {
                            if (house.getMagicCard() == null) {
                                game.getPlayer(SideOfFeature.CURRENT).getBoard().getPlayerHand().remove(cardProp.getCard());
                                setCardOnBoard(game, magicCard, house);
                                magicCard.setActivated(true);
                                break;
                            }
                        }
                    }
                }
            }
        }

        ManuallyActivation.getInstance().activeEffects(game);//activation of magic from board ( magic houses and field house)
        magicCard.setActivated(true);
        ((MagicHouse) cardProp.getCardPlace()).setState(MagicHouseVisibilityState.O);
        game.setCardProp(null);
        return General.SPELL_ACTIVATED_SUCCESSFULLY.toString();
    }

    private void setCardOnBoard(Game game, MagicCard magicCard, MagicHouse house) {
        if (magicCard.getMagicAttribute() != MagicAttribute.FIELD &&
                magicCard.getMagicAttribute() != MagicAttribute.CONTINUOUS &&
                magicCard.getMagicAttribute() != MagicAttribute.EQUIP &&
                magicCard.getMagicAttribute() != MagicAttribute.COUNTER) {
            game.getPlayer(SideOfFeature.CURRENT).getBoard().moveCardToGraveYard(magicCard);
        } else {
            house.setMagicCard(magicCard);
        }

    }
}
