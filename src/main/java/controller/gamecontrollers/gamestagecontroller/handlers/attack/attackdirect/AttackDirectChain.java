package controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect;

import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.processors.AttackProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.attack.attackdirect.processors.SelectedCardProcessor;
import model.gameprop.gamemodel.Game;

public class AttackDirectChain {
    AttackDirectProcessor processor;
    public AttackDirectChain(){
        buildChain();
    }

    private void buildChain (){
        processor = new SelectedCardProcessor(new AttackProcessor(null));
    }

    public String request(Game game){
       return processor.process(game);
    }
}
