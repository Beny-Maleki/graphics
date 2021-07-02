package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster;

import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.processors.AttackProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackmonster.processors.SelectedCardProcessor;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.gamemodel.Game;

public class AttackMonsterChain {
    AttackMonsterProcessor processor;

    public AttackMonsterChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectedCardProcessor(new AttackProcessor(null));
    }

    public String request(MonsterHouse monsterHouse, Game game) {
        return processor.process(monsterHouse, game);
    }


}
