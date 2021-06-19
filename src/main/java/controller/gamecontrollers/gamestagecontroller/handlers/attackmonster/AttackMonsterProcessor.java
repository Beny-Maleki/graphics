package controller.gamecontrollers.gamestagecontroller.handlers.attackmonster;

import model.enums.GameEnums.GamePhaseEnums.BattlePhase;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public abstract class AttackMonsterProcessor {
    AttackMonsterProcessor processor;

    protected AttackMonsterProcessor(AttackMonsterProcessor processor) {
        this.processor = processor;
    }

    protected BattlePhase process(SelectedCardProp offensive, MonsterHouse target, Game game) {
        if (processor != null)
            return processor.process(offensive, target, game);
        else return null;
    }
}
