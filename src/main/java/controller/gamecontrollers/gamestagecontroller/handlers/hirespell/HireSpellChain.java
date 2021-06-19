package controller.gamecontrollers.gamestagecontroller.handlers.hirespell;

import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.BoardProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.HireSpellProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.hirespell.processors.SelectedCardProcessor;
import model.gameprop.gamemodel.Game;

public class HireSpellChain {
    SpellProcessor processor;

    public HireSpellChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SelectedCardProcessor(new BoardProcessor(new HireSpellProcessor(null)));
    }

    public String request(Game game) {
        return processor.process(game);
    }
}
