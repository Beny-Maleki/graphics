package model.cards.cardsActions.monsterActionChildren;

import model.cards.cardsActions.Action;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;

public class MakeAttackerMonsterAtkZero extends Action {

    {
        name = this.getClass().getSimpleName();
    }

    public MakeAttackerMonsterAtkZero() {
    }

    @Override
    public void active(Game game) {
        Player opponent = game.getPlayer(SideOfFeature.OPPONENT);
        MonsterCard suijin = (MonsterCard) game.getCardProp().getCard(); // attacked monster
        //
        isActivatedBefore = true;
    }
}
