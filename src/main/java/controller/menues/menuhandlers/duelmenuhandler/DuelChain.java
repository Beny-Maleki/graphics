package controller.menues.menuhandlers.duelmenuhandler;

import controller.menues.menuhandlers.duelmenuhandler.processors.RoundNumberProcessor;
import controller.menues.menuhandlers.duelmenuhandler.processors.SecPlayerProcessor;
import controller.menues.menuhandlers.duelmenuhandler.processors.deckValidatorProcessor;
import model.enums.MenusMassages.Duel;

public class DuelChain {
    DuelProcessor processor;

    public DuelChain() {
        buildChain();
    }

    private void buildChain() {
        processor = new SecPlayerProcessor(new deckValidatorProcessor(new RoundNumberProcessor(null)));
    }

    public Duel request(String[] data) {
       return processor.process(data);
    }
}
