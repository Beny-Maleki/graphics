package controller.gamecontrollers.gamestagecontroller.handlers.hirespell;

import controller.gamecontrollers.gamestagecontroller.handlers.hiremonster.MonsterProcessor;
import model.enums.GameEnums.TypeOfHire;
import model.gameprop.gamemodel.Game;

public abstract class SpellProcessor {
    SpellProcessor processor;

    protected SpellProcessor(SpellProcessor processor) {
        this.processor = processor;
    }

    protected String process(Game game) {
        if (processor != null) {
            return processor.process(game);
        }else{
            return null;
        }
    }
}
