package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster;

import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.gamemodel.Game;

public abstract class AttackMonsterProcessor {
    AttackMonsterProcessor processor;

    protected AttackMonsterProcessor(AttackMonsterProcessor processor) {
        this.processor = processor;
    }

    protected String process(MonsterHouse target, Game game) {
        if (processor != null)
            return processor.process(target, game);
        else return null;
    }
}
