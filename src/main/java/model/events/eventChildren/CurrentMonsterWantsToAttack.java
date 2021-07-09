package model.events.eventChildren;

import model.enums.GameEnums.SideOfFeature;
import model.events.Event;
import model.gameprop.BoardProp.MagicHouse;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.gamemodel.Game;

import java.io.FileNotFoundException;

public class CurrentMonsterWantsToAttack extends Event {
    private static CurrentMonsterWantsToAttack instance;

    private CurrentMonsterWantsToAttack() {
    }

    public static CurrentMonsterWantsToAttack getInstance() {
        if (instance == null) {
            instance = new CurrentMonsterWantsToAttack();
        }
        return instance;
    }

    @Override
    public void activeEffects(Game game) throws FileNotFoundException {
        PlayerBoard opponentPlayerBoard = game.getPlayer(SideOfFeature.OPPONENT).getBoard();
        for (MagicHouse magicHouse : opponentPlayerBoard.getMagicHouse()) {
            magicHouse.getMagicCard().activeEffectsByEvent(this, game);
        }
        for (MonsterHouse monsterHouse : opponentPlayerBoard.getMonsterHouse()) {
            monsterHouse.getMonsterCard().activeEffectsByEvent(this, game);
        }
    }
}
