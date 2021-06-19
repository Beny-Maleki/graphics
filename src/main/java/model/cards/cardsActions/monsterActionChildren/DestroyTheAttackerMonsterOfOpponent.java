package model.cards.cardsActions.monsterActionChildren;

import model.cards.cardsActions.Action;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;

public class DestroyTheAttackerMonsterOfOpponent extends Action {

    {
        name = this.getClass().getName();
    }

    public DestroyTheAttackerMonsterOfOpponent() {

    }

    @Override
    public void active(Game game) {
        MonsterCard attacker = (MonsterCard) game.getCardProp().getCard();
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterHouse[] monsterHouses = opponent.getBoard().getMonsterHouse();

        for (MonsterHouse monsterHouse : monsterHouses) {
            MonsterCard monsterCard = monsterHouse.getMonsterCard();
            if (monsterCard != null) {
                opponent.getBoard().getGraveYard().addCardToGraveYard(monsterCard);
                monsterHouse.setMonsterCard(null);
            }
        }
    }
}
