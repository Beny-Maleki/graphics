package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.ExistenceObserver;
import model.gameprop.existenceBasedObserver.FieldCardObserver;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class ChangingMonsterAttackAction extends Action implements ChangingSomethingByFieldCard{
    private final int changeAttack;
    private final ArrayList<String> typesToChangeAttack;
    private final int addOrMinus;
    private final ArrayList<SideOfFeature> changeWhichTeamMonsterAttack;
    private int finalChangeValue;

    {
        name = this.getClass().getSimpleName();
    }

    public ChangingMonsterAttackAction(int changeAttack, ArrayList<String> typesToChangeAttack, int addOrMinus, ArrayList<SideOfFeature> changeWhichTeamMonsterAttack) {
        this.changeAttack = changeAttack;
        this.typesToChangeAttack = typesToChangeAttack;
        this.addOrMinus = addOrMinus;
        this.changeWhichTeamMonsterAttack = changeWhichTeamMonsterAttack;
    }

    public int getFinalChangeValue() {
        return finalChangeValue;
    }

    public ArrayList<String> getTypesToChangeAttack() {
        return typesToChangeAttack;
    }

    public ArrayList<SideOfFeature> getChangeWhichTeamMonsterAttack() {
        return changeWhichTeamMonsterAttack;
    }


    @Override
    public void active(Game game) {
        finalChangeValue = changeAttack * addOrMinus;

        Player currentPlayer = game.getPlayer(SideOfFeature.CURRENT);
        change(finalChangeValue, changeWhichTeamMonsterAttack, typesToChangeAttack, currentPlayer, "Attack");

        ArrayList<ExistenceObserver> existenceObservers = ExistenceObserver.getExistenceObservers();
        FieldCardObserver lastObserver = (FieldCardObserver) existenceObservers.get(existenceObservers.size() - 1);
        // it can be guaranteed that the last observer is from this type as the last one is added just moments (lines!) before in "change()"!
        lastObserver.setToRevertAction(this);

        isActivatedBefore = true;
    }
}
