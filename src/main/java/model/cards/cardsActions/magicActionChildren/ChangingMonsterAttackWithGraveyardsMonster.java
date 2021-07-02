package model.cards.cardsActions.magicActionChildren;

import model.cards.cardsActions.Action;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.gameprop.BoardProp.PlayerBoard;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.existenceBasedObserver.ExistenceObserver;
import model.gameprop.existenceBasedObserver.FieldCardObserver;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class ChangingMonsterAttackWithGraveyardsMonster extends Action implements ChangingSomethingByFieldCard{
    private final ArrayList<String> typesToChangeAttack;
    private final int changeAttackForEachMonsterInGraveyard;
    private final int addOrMinus;
    private final ArrayList<SideOfFeature> countWhichGraveYards;
    private final ArrayList<SideOfFeature> changeWhichTeamMonstersAttack;
    private int finalChangeValue;

    {
        name = this.getClass().getSimpleName();
    }

    public ChangingMonsterAttackWithGraveyardsMonster(int changeAttackForEachMonsterInGraveyard, int addOrMinus, ArrayList<String> typesToChangeAttack, ArrayList<SideOfFeature> countWhichGraveYards, ArrayList<SideOfFeature> changeWhichTeamMonstersAttack) {
        this.addOrMinus = addOrMinus;
        this.changeAttackForEachMonsterInGraveyard = changeAttackForEachMonsterInGraveyard;
        this.typesToChangeAttack = typesToChangeAttack;
        this.changeWhichTeamMonstersAttack = changeWhichTeamMonstersAttack;
        this.countWhichGraveYards = countWhichGraveYards;
    }

    public ArrayList<SideOfFeature> getChangeWhichTeamMonstersAttack() {
        return changeWhichTeamMonstersAttack;
    }

    public int getFinalChangeValue() {
        return finalChangeValue;
    }

    public ArrayList<String> getTypesToChangeAttack() {
        return typesToChangeAttack;
    }

    @Override
    public void active(Game game) {
        int countMonstersInGraveyard = 0;
        for (SideOfFeature countWhichGraveYard : countWhichGraveYards) {
            PlayerBoard playerBoard = game.getPlayer(countWhichGraveYard).getBoard();
            for (Card destroyedCard : playerBoard.getGraveYard().getDestroyedCards()) {
                if (destroyedCard instanceof MonsterCard) {
                    countMonstersInGraveyard++;
                }
            }
        }
        finalChangeValue = countMonstersInGraveyard * changeAttackForEachMonsterInGraveyard * addOrMinus;

        Player currentPlayer = GameInProcess.getGame().getPlayer(SideOfFeature.CURRENT);

        change(finalChangeValue, changeWhichTeamMonstersAttack, typesToChangeAttack, currentPlayer, "Attack");

        ArrayList<ExistenceObserver> existenceObservers = ExistenceObserver.getExistenceObservers();
        FieldCardObserver lastObserver = (FieldCardObserver) existenceObservers.get(existenceObservers.size() - 1);
        // it can be guaranteed that the last observer is from this type as the last one is added just moments (lines!) before in "change()"!
        lastObserver.setToRevertAction(this);

        isActivatedBefore = true;
    }
}
