package model.gameprop.existenceBasedObserver;

import model.cards.cardsActions.Action;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;

public class EquipCardObserver extends ExistenceObserver{
    private MonsterCard equippedCard;
    private Action toRevertAction;

    public EquipCardObserver(Card observedCard, Player ownerOfCard) {
        super(observedCard, ownerOfCard);
        setEquippedCard((MonsterCard) observedCard);
    }

    public void setToRevertAction(Action toRevertAction) {
        this.toRevertAction = toRevertAction;
    }

    public void setEquippedCard(MonsterCard equippedCard) {
        this.equippedCard = equippedCard;
    }

    public boolean isMonsterFaceUp() {
        MonsterHouse[] monsterHouses = ownerOfCard.getBoard().getMonsterHouse();
        for (MonsterHouse monsterHouse : monsterHouses) {
            if (monsterHouse.getMonsterCard().equals(observedCard)) {
                if (!monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update() {
        if (exists() || !isMonsterFaceUp()) {
            actionFinalize();
            existenceObservers.remove(this);
        }
    }

    private void actionFinalize() {
        MagicCard equipCard = equippedCard.getEquippedWith();
        ownerOfCard.getBoard().moveCardToGraveYard(equipCard); // when equipped card goes to Graveyard or turns to hidden situation, equip card goes to graveyard!
    }
}
