package model.gameprop;

import model.cards.cardsProp.Card;
import model.enums.GameEnums.CardLocation;
import model.gameprop.BoardProp.HandHouse;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;

public class SelectedCardProp<T extends Selectable> {
    int cardAddress;
    CardLocation location;
    boolean doesBelongToCurrent;
    T cardPlace;

    public SelectedCardProp(boolean doesBelongToCurrent, T cardPlace) {
        this.cardPlace = cardPlace;
        this.cardAddress = cardPlace.getIndex();
        this.doesBelongToCurrent = doesBelongToCurrent;
    }

    public Card getCard() {
        return cardPlace.getCard();
    }


    public boolean doesBelongToCurrent() {
        return doesBelongToCurrent;
    }

    public CardLocation getLocation() {
        if (cardPlace instanceof MonsterHouse) return CardLocation.MONSTER_ZONE;
        else if (cardPlace instanceof MagicHouse) return CardLocation.SPELL_ZONE;
        else if (cardPlace instanceof HandHouse) return CardLocation.PLAYER_HAND;
        else return CardLocation.FIELD_ZONE;
    }

    public Selectable getCardPlace() {
        return cardPlace;
    }

}
