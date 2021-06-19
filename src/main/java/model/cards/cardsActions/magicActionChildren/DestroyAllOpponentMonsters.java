package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class DestroyAllOpponentMonsters extends Action {
    {
        name = this.getClass().getSimpleName();
    }

    @Override
    public void active(Game game) {
        PlayerBoard opponentBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (MonsterHouse monsterHouse : opponentBoard.getMonsterHouse()) {
            if (monsterHouse.getMonsterCard() != null) {
                opponentBoard.getGraveYard().addCardToGraveYard(monsterHouse.getMonsterCard());
                monsterHouse.setMonsterCard(null);
            }
        }
        isActivatedBefore = true;
    }
}
