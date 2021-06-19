package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.ExistenceObserver;
import model.gameprop.existenceBasedObserver.FieldCardObserver;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class ChangingMonsterDefenceAction extends Action implements ChangingSomethingByFieldCard{
    private final int changeDefence;
    private final ArrayList<String> typesToChangeDefence;
    private final int addOrMinus;
    private final ArrayList<SideOfFeature> changeWhichTeamMonsterDefence;
    private int finalChangeValue;

    {
        name = this.getClass().getSimpleName();
    }

    public ChangingMonsterDefenceAction(int changeDefence, ArrayList<String> typesToChangeDefence, int addOrMinus, ArrayList<SideOfFeature> changeWhichTeamMonsterDefence) {
        this.changeDefence = changeDefence;
        this.typesToChangeDefence = typesToChangeDefence;
        this.addOrMinus = addOrMinus;
        this.changeWhichTeamMonsterDefence = changeWhichTeamMonsterDefence;
    }

    public ArrayList<SideOfFeature> getChangeWhichTeamMonsterDefence() {
        return changeWhichTeamMonsterDefence;
    }

    public ArrayList<String> getTypesToChangeDefence() {
        return typesToChangeDefence;
    }

    public int getFinalChangeValue() {
        return finalChangeValue;
    }

    @Override
    public void active(Game game) {
        finalChangeValue = changeDefence * addOrMinus;

        Player currentPlayer = game.getPlayer(SideOfFeature.CURRENT);
        change(finalChangeValue, changeWhichTeamMonsterDefence, typesToChangeDefence, currentPlayer, "Defence");

        ArrayList<ExistenceObserver> existenceObservers = ExistenceObserver.getExistenceObservers();
        FieldCardObserver lastObserver = (FieldCardObserver) existenceObservers.get(existenceObservers.size() - 1);
        // it can be guaranteed that the last observer is from this type as the last one is added just moments (lines!) before in "change()"!
        lastObserver.setToRevertAction(this);

        isActivatedBefore = true;
    }
}
