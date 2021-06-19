package model.gameprop.existenceBasedObserver;

import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;

import java.util.ArrayList;

public abstract class ExistenceObserver {
    protected static ArrayList<ExistenceObserver> existenceObservers;

    static {
        existenceObservers = new ArrayList<>();
    }

    protected Card observedCard;
    protected Player ownerOfCard;

    public ExistenceObserver(Card observedCard, Player ownerOfCard) {
        this.ownerOfCard = ownerOfCard;
        this.observedCard = observedCard;

        existenceObservers.add(this);
    }

    public static ArrayList<ExistenceObserver> getExistenceObservers() {
        return existenceObservers;
    }

    public static void clearExistenceObserver() {
        existenceObservers.clear();
    }

    public abstract void update();

    public boolean exists() {
        if (observedCard != null) { // card is assigned to observer!
            if (observedCard instanceof MonsterCard) {
                MonsterHouse[] monsterHouses = ownerOfCard.getBoard().getMonsterHouse();

                for (MonsterHouse monsterHouse : monsterHouses) {
                    MonsterCard monsterCard = monsterHouse.getMonsterCard();
                    if (observedCard.equals(monsterCard)) {
                        return false;
                    }
                }

            } else if (observedCard instanceof MagicCard) {
                MagicHouse[] magicHouses = ownerOfCard.getBoard().getMagicHouse();

                for (MagicHouse magicHouse : magicHouses) {
                    MagicCard magicCard = magicHouse.getMagicCard();
                    if (observedCard.equals(magicCard)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }
}
