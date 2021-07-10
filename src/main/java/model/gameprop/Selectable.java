package model.gameprop;

import model.cards.cardsProp.Card;

public interface Selectable {
    Card getCard();
    int getIndex();

    String getState();
}
