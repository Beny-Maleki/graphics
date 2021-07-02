package controller.gamecontrollers.gamestagecontroller.handlers.flipsummon;

import controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors.FlipCardProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.flipsummon.processors.SelectCardProcessor;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public class FlipSummonChain {
    FlipSummonProcessor processor;

    public FlipSummonChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectCardProcessor(new FlipCardProcessor(null));
    }

    public String request(Game game) {
        return processor.process(game);
    }
}
