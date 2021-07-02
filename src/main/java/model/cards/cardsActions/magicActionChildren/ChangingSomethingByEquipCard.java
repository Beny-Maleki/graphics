package model.cards.cardsActions.magicActionChildren;

import controller.gamecontrollers.GetStringInputFromView;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.EquipCardObserver;
import model.gameprop.gamemodel.Game;

public interface ChangingSomethingByEquipCard {
    default String change(int changeValue, MonsterCard equippedCard, MagicCard equipCard, String whichPower, Player ownerOfCard) {
        if (!isMonsterFaceUp(equippedCard)) {
            GetStringInputFromView.getInputFromView(RequestingInput.ERROR_OF_INVALID_MONSTER_CARD_EQUIPPED);
            return "Error";
        }

        MonsterHouse monsterHouseOfEquipped = MonsterHouse.getMonsterHouseByMonsterCard(equippedCard);

        if (whichPower.equals("Attack")) {
            assert monsterHouseOfEquipped != null;
            monsterHouseOfEquipped.setAdditionalAttack(monsterHouseOfEquipped.getAdditionalAttack() + changeValue);
        } else if (whichPower.equals("Defence")) {
            assert monsterHouseOfEquipped != null;
            monsterHouseOfEquipped.setAdditionalDefence(monsterHouseOfEquipped.getAdditionalDefence() + changeValue);
        }

        equippedCard.setEquippedWith(equipCard);

        new EquipCardObserver(equippedCard, ownerOfCard);
        return "Successful";
    }

    default boolean isMonsterFaceUp(MonsterCard monsterCard) {
        Game game = GameInProcess.getGame();
        Player ownerOfCard = game.getPlayer(SideOfFeature.CURRENT);
        MonsterHouse[] monsterHouses = ownerOfCard.getBoard().getMonsterHouse();
        for (MonsterHouse monsterHouse : monsterHouses) {
            if (monsterHouse.getMonsterCard().equals(monsterCard)) {
                if (!monsterHouse.getState().equals(MonsterHouseVisibilityState.DH)) { // isn't defensive hidden!
                    return true;
                }
            }
        }
        return false;
    }

}
