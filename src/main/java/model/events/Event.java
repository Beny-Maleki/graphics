package model.events;

import model.enums.GameEnums.SideOfFeature;
import model.events.eventChildren.ManuallyActivation;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class Event {
    protected String name;

    public void activeEffects(Game game) {
        PlayerBoard currentPlayerBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        if (this instanceof ManuallyActivation) {
            game.getCardProp().getCard().activeEffectsByEvent(this, game);
        } else {
            for (MonsterHouse monsterHouse : currentPlayerBoard.getMonsterHouse()) {
                monsterHouse.getMonsterCard().activeEffectsByEvent(this, game);
            }
            for (MagicHouse magicHouse : currentPlayerBoard.getMagicHouse()) {
                magicHouse.getMagicCard().activeEffectsByEvent(this, game);
            }
        }
    }
}
