package controller.gamecontrollers.gamestagecontroller.handlers.changeposition.processors;

import controller.gamecontrollers.gamestagecontroller.handlers.changeposition.ChangePosProcessor;
import model.enums.GameEnums.GamePhaseEnums.MainPhase;
import model.enums.GameEnums.WantedPos;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;

public class ChangeRequestProcessor extends ChangePosProcessor {
    public ChangeRequestProcessor(ChangePosProcessor processor) {
        super(processor);
    }

    public MainPhase process(SelectedCardProp cardProp, WantedPos pos, MonsterHouse summonedMonsterHouses) {
        MonsterHouse monsterHouse = (MonsterHouse) cardProp.getCardPlace();
        if (monsterHouse == summonedMonsterHouses)
            return MainPhase.CANT_CHANGE_POS_OF_HIRED_CARD;
        if (monsterHouse.isPosChange())
            return MainPhase.POS_CHANGE_BEFORE;
        else {
            if (pos.equals(WantedPos.ATTACK)) {
                monsterHouse.changePos();
                monsterHouse.setState(MonsterHouseVisibilityState.OO);
            } else {
                monsterHouse.changePos();
                monsterHouse.setState(MonsterHouseVisibilityState.DO);
            }
            return MainPhase.MONSTER_CHANGE_POS;
        }
    }
}
