package controller.gamecontrollers.gamestagecontroller.handlers.hiremonster;

import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors.BoardProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors.CardSelectProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.processors.HireMonsterProcessor;
import model.enums.GameEnums.TypeOfHire;
import model.gameprop.gamemodel.Game;

public class HireMonsterChain {
    MonsterProcessor processor;

    public HireMonsterChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new CardSelectProcessor(new BoardProcessor(new HireMonsterProcessor(null)));
    }

    public String request(Game game, TypeOfHire type) {
        return processor.process(game, type);
    }
}
