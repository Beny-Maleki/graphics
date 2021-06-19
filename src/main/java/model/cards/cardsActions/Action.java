package model.cards.cardsActions;

import controller.gamecontrollers.GetStringInputFromView;
import exceptions.CardNotFoundException;
import model.cards.cardsActions.magicActionChildren.ChangingEquipedMonsterAttack;
import model.cards.cardsActions.magicActionChildren.ChangingEquippedMonsterDefence;
import model.cards.cardsActions.magicActionChildren.ChangingSomeRaceEquipedMonsterAttack;
import model.cards.cardsActions.magicActionChildren.ChangingSomeRaceEquipedMonsterDefence;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class Action {
    protected String name;
    protected boolean isActivatedBefore;

    public void active(Game game) {
    }

    public static MonsterCard equipAMonsterWithSpell(Action action, Game game) { // Because of Polymorphism and JSON problems the best approach was this way!!!

        MonsterCard equipedMonster;

        {
            equipedMonster = null;
        }

        ChangingEquipedMonsterAttack changingEquipedMonsterAttack;
        ChangingSomeRaceEquipedMonsterAttack changingSomeRaceEquipedMonsterAttack;
        ChangingEquippedMonsterDefence changingEquippedMonsterDefence;
        ChangingSomeRaceEquipedMonsterDefence changingSomeRaceEquipedMonsterDefence;
        if (action instanceof ChangingEquipedMonsterAttack) {
            changingEquipedMonsterAttack = (ChangingEquipedMonsterAttack) action;
            equipedMonster = changingEquipedMonsterAttack.getEquipedMonster();

        } else if (action instanceof ChangingSomeRaceEquipedMonsterAttack) {
            changingSomeRaceEquipedMonsterAttack = (ChangingSomeRaceEquipedMonsterAttack) action;
            equipedMonster = changingSomeRaceEquipedMonsterAttack.getEquipedMonster();

        } else if (action instanceof  ChangingEquippedMonsterDefence) {
            changingEquippedMonsterDefence = (ChangingEquippedMonsterDefence) action;
            equipedMonster = changingEquippedMonsterDefence.getEquipedMonster();

        } else if (action instanceof ChangingSomeRaceEquipedMonsterDefence) {
            changingSomeRaceEquipedMonsterDefence = (ChangingSomeRaceEquipedMonsterDefence) action;
            equipedMonster = changingSomeRaceEquipedMonsterDefence.getEquipedMonster();

        } // Down cast of "action" to appropriate subclass of "Action" and evaluation of equipMonster


        if (equipedMonster == null) {
            String nameOfMonster = GetStringInputFromView.getInputFromView(RequestingInput.SET_EQUIPED_MONSTER);
            PlayerBoard currentPlayerboard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
            try {
                equipedMonster = currentPlayerboard.getMonsterCardByName(nameOfMonster);
                equipedMonster.setEquippedWith((MagicCard) game.getCardProp().getCard());
            } catch (CardNotFoundException e) {
                equipAMonsterWithSpell(action, game);
            }
        }

        return equipedMonster;
    }
}
