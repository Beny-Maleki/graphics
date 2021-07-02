package model.events;

import model.enums.GameEnums.SideOfFeature;
import model.events.eventChildren.ActivationInOpponentTurn;
import model.events.eventChildren.ManuallyActivation;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

public class Event {
    protected String name;

    public String getName() {
        return name;
    }

    public void activeEffects(Game game) {
        PlayerBoard currentPlayerBoard = game.getPlayer(SideOfFeature.CURRENT).getBoard();
        PlayerBoard opponentPlayerBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        if (this instanceof ManuallyActivation) {
            game.getCardProp().getCard().activeEffectsByEvent(this, game);
        } else if (this instanceof ActivationInOpponentTurn) {
            //ASK PLAYER FOR ACTIVATION OR NOT
            for (MagicHouse magicHouse : opponentPlayerBoard.getMagicHouse()) {
                if (magicHouse.getMagicCard() != null) {
                    magicHouse.getMagicCard().activeEffectsByEvent(this, game);
                }
            }
        } else {
            for (MonsterHouse monsterHouse : currentPlayerBoard.getMonsterHouse()) {
                if (monsterHouse.getMonsterCard() != null) {
                    monsterHouse.getMonsterCard().activeEffectsByEvent(this, game);
                }
            }
            for (MagicHouse magicHouse : currentPlayerBoard.getMagicHouse()) {
                if (magicHouse.getMagicCard() != null) {
                    magicHouse.getMagicCard().activeEffectsByEvent(this, game);
                }
            }
        }
    }
}
