package controller.gamecontrollers.gamestagecontroller.handlers.attackdirect;

import controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.processors.AttackProcessor;
import controller.gamecontrollers.gamestagecontroller.handlers.attackdirect.processors.SelectedCardProcessor;
import model.enums.GameEnums.gamestage.GameMainStage;
import model.gameprop.Player;
import model.gameprop.SelectedCardProp;

public class AttackDirectChain {
    AttackDirectProcessor processor;
    public AttackDirectChain(){
        buildChain();
    }

    private void buildChain (){
        processor = new SelectedCardProcessor(new AttackProcessor(null));
    }

    public String request(SelectedCardProp cardProp, Player target, boolean firstTurnOfTheGame, GameMainStage stage){
       return processor.process(cardProp , target, firstTurnOfTheGame, stage);
    }
}
