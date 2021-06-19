package model.gameprop.BoardProp;

import model.cards.cardsProp.MonsterCard;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MonsterHouseVisibilityState;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class MonsterHouse extends GameHouse {
    MonsterCard monsterCard;
    MonsterHouseVisibilityState state;
    private boolean isMonsterAttacked;
    private int practicalAttack;
    private int practicalDefence;
    private boolean haveBeenImpactedByField;

    {
        isMonsterAttacked = false;
        changePos = false;
        haveBeenImpactedByField = false;
        monsterCard = null;
        state = MonsterHouseVisibilityState.E;
    }

    public MonsterHouseVisibilityState getState() {
        return state;
    }

    public void setState(MonsterHouseVisibilityState state) {
        this.state = state;
    }

    public MonsterCard getMonsterCard() {
        return monsterCard;
    }

    public void setMonsterCard(MonsterCard monsterCard) {
        this.monsterCard = monsterCard;
        if (this.monsterCard == null) {
            state = MonsterHouseVisibilityState.E;
            isMonsterAttacked = false;
            haveBeenImpactedByField = false;
            practicalAttack = 0;
            practicalDefence = 0;
        } else {
            practicalAttack = monsterCard.getAttack();
            practicalDefence = monsterCard.getDefence();

        }
    }

    public int getPracticalAttack() {
        return practicalAttack;
    }

    public void setPracticalAttack(int practicalAttack) {
        this.practicalAttack = practicalAttack;
    }

    public int getPracticalDefence() {
        return practicalDefence;
    }

    public void setPracticalDefence(int practicalDefence) {
        this.practicalDefence = practicalDefence;
    }

    public boolean getHaveBeenImpactedByField() {
        return haveBeenImpactedByField;
    }

    public void setHaveBeenImpactedByField(boolean haveBeenImpactedByField) {
        this.haveBeenImpactedByField = haveBeenImpactedByField;
    }

    public boolean isPosChange() {
        return changePos;
    }

    public void changePos() {
        changePos = true;
    }

    public boolean isMonsterAttacked() {
        return isMonsterAttacked;
    }

    public void setMonsterAttacked() {
        isMonsterAttacked = true;
    }

    public static MonsterHouse getMonsterHouseByMonsterCard(MonsterCard monsterCard) {
        Game game = GameInProcess.getGame();

        ArrayList<SideOfFeature> sides = new ArrayList<>();
        sides.add(SideOfFeature.CURRENT); sides.add(SideOfFeature.OPPONENT);

        for (SideOfFeature side : sides) {
            Player player = game.getPlayer(side);
            MonsterHouse[] monsterHouses = player.getBoard().getMonsterHouse();

            for (MonsterHouse monsterHouse : monsterHouses) {
                if (monsterHouse.getMonsterCard().equals(monsterCard)) {
                    return monsterHouse;
                }
            }
        }
        return null;
    }
}
//TODO game map and where to add it