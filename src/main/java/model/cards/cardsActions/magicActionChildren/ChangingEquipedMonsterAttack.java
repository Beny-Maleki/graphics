package model.cards.cardsActions.magicActionChildren;

import controller.gamecontrollers.GetStringInputFromView;
import model.cards.cardsActions.Action;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.RequestingInput;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.EquipCardObserver;
import model.gameprop.existenceBasedObserver.ExistenceObserver;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class ChangingEquipedMonsterAttack extends Action implements ChangingSomethingByEquipCard{
    private MonsterCard equipedMonster;
    private final int changeAttack;
    private final int addOrMinus;

    {
        name = this.getClass().getSimpleName();
        equipedMonster = null;
        isActivatedBefore = false;
    }

    public ChangingEquipedMonsterAttack(int changeAttack, int addOrMinus) {
        this.addOrMinus = addOrMinus;
        this.changeAttack = changeAttack;
    }

    public MonsterCard getEquipedMonster() {
        return equipedMonster;
    }

    @Override
    public void active(Game game) {
        Player ownerOfCard = game.getPlayer(SideOfFeature.CURRENT);

        MagicCard equipMagicCard = (MagicCard) game.getCardProp().getCard(); // the last selected Card is the spell Card!

        equipedMonster = Action.equipAMonsterWithSpell(this, game);

        String result = change(changeAttack, equipedMonster, equipMagicCard, "Attack", ownerOfCard);

        if (result.equals("Successful")) {
            ArrayList<ExistenceObserver> existenceObservers = ExistenceObserver.getExistenceObservers();
            EquipCardObserver lastObserver = (EquipCardObserver) existenceObservers.get(existenceObservers.size() - 1);
            // it can be guaranteed that the last observer is related to this action!
            lastObserver.setToRevertAction(this);

            isActivatedBefore = true;
        } else {
            GetStringInputFromView.getInputFromView(RequestingInput.ERROR_OF_INVALID_MONSTER_CARD_EQUIPPED);
            active(game);
        }
    }

}
