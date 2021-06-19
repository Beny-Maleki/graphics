package model.gameprop.gamemodel;

import model.enums.GameEnums.PlayerTurn;
import model.enums.GameEnums.TypeOfHire;
import model.gameprop.BoardProp.MonsterHouse;
import model.gameprop.SelectedCardProp;

public class Turn {
    PlayerTurn playerWithTurn;
    TypeOfHire typeOfHighLevelMonsterHire;
    MonsterHouse monsterHouseOfHiredMonster;
    SelectedCardProp selectedCardProp;
    int tributeNumber;
    boolean isCardDraw;
    boolean isFirstTurn;

    {
        isFirstTurn = false;
        typeOfHighLevelMonsterHire = null;
        tributeNumber = 0;
        isCardDraw = false;
        monsterHouseOfHiredMonster = null;
    }

    protected Turn(PlayerTurn playerWithTurn, boolean firstTurn) {
        this.playerWithTurn = playerWithTurn;
        this.isFirstTurn = firstTurn;
    }

    public int getTributeNumber() {
        return tributeNumber;
    }

    public void setTributeNumber(int tributeNumber) {
        this.tributeNumber = tributeNumber;
    }

    public SelectedCardProp getSelectedCardProp() {
        return selectedCardProp;
    }

    public void setSelectedCardProp(SelectedCardProp selectedCardProp) {
        this.selectedCardProp = selectedCardProp;
    }

    public boolean isCardDraw() {
        return isCardDraw;
    }

    public void setCardDraw() {
        isCardDraw = true;
    }

    public MonsterHouse getMonsterHouseOfHiredMonster() {
        return monsterHouseOfHiredMonster;
    }

    public void setMonsterHouseOfHiredMonster(MonsterHouse monsterHouseOfHiredMonster) {
        this.monsterHouseOfHiredMonster = monsterHouseOfHiredMonster;
    }

    public TypeOfHire getTypeOfHighLevelMonsterHire() {
        return typeOfHighLevelMonsterHire;
    }

    public void setTypeOfHighLevelMonsterHire(TypeOfHire typeOfHighLevelMonsterHire) {
        this.typeOfHighLevelMonsterHire = typeOfHighLevelMonsterHire;
    }

    public PlayerTurn getPlayerWithTurn() {
        return playerWithTurn;
    }

    public boolean isFirstTurn() {
        return isFirstTurn;
    }
}
