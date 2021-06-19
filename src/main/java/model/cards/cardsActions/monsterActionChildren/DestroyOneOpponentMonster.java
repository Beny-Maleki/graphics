package model.cards.cardsActions.monsterActionChildren;

import controller.gamecontrollers.GetStringInputFromView;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;

public class DestroyOneOpponentMonster extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        String nameOfTargetMonster = GetStringInputFromView.getInputFromView(RequestingInput.CHOOSE_FROM_OPPO_MONSTER_HOUSES);
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterHouse[] monsterHouses = opponent.getBoard().getMonsterHouse();

        for (MonsterHouse monsterHouse : monsterHouses) {
            MonsterCard monsterCard = monsterHouse.getMonsterCard();
            if (monsterCard != null) { // checking emptiness of a house!
                if (monsterCard.getName().equals(nameOfTargetMonster)) { // detected the target!
                    opponent.getBoard().getGraveYard().addCardToGraveYard(monsterCard); // send target to graveyard
                    monsterHouse.setMonsterCard(null); // make empty
                    break;
                }
            }
        }
        isActivatedBefore = true;
    }
}
