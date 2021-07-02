package controller.gamecontrollers.gamestagecontroller.handlers.hirespell;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.BoardProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.SetSpellProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.SelectedCardProcessor;
import model.gameprop.gamemodel.Game;

public class SetSpellChain {
    SpellProcessor processor;

    public SetSpellChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectedCardProcessor(new BoardProcessor(new SetSpellProcessor(null)));
    }

    public String request(Game game) {
        return processor.process(game);
    }
}
