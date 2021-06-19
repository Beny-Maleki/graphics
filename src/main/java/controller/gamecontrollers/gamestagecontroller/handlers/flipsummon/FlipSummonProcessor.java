package controller.gamecontrollers.gamestagecontroller.handlers.flipsummon;

import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.gameprop.SelectedCardProp;
import model.gameprop.gamemodel.Game;

public abstract class FlipSummonProcessor {
    FlipSummonProcessor processor;

    public FlipSummonProcessor(FlipSummonProcessor processor) {
        this.processor = processor;
    }

    public MainPhase process(SelectedCardProp cardProp, Game game) {
        if (processor != null)
            return processor.process(cardProp, game);
        else return null;
    }
}
