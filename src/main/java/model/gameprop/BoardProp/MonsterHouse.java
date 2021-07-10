package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import model.cards.cardsProp.Card;
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
    private int additionalAttack;
    private int additionalDefence;
    private boolean haveBeenImpactedByField;

    {
        additionalAttack = 0;
        additionalDefence = 0;
        isMonsterAttacked = false;
        changePos = false;
        haveBeenImpactedByField = false;
        monsterCard = null;
        state = MonsterHouseVisibilityState.E;
    }

    public MonsterHouse(int index) {
        super();
        this.index = index;
    }

    public static MonsterHouse getMonsterHouseByMonsterCard(MonsterCard monsterCard) {
        Game game = GameInProcess.getGame();

        ArrayList<SideOfFeature> sides = new ArrayList<>();
        sides.add(SideOfFeature.CURRENT);
        sides.add(SideOfFeature.OPPONENT);

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

    public int getIndex() {
        return index;
    }

    public String getState() {
        return state.toString();
    }

    public void setState(MonsterHouseVisibilityState state) {
        this.state = state;
    }

    @Override
    public Image getCardImage() {
        switch (state) {
            case E:
                return null;
            case DH:
                return GameHouse.getBackOfCardImage();
            default:
                return Card.getCardImage(this.getCard());
        }
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
        }

    }

    public int getAdditionalAttack() {
        return additionalAttack;
    }

    public void setAdditionalAttack(int additionalAttack) {
        this.additionalAttack = additionalAttack;
    }

    public int getAdditionalDefence() {
        return additionalDefence;
    }

    public void setAdditionalDefence(int additionalDefence) {
        this.additionalDefence = additionalDefence;
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

    @Override
    public Card getCard() {
        return monsterCard;
    }

    public void setImageOfCard(boolean isVisible) {
        if (isVisible)
            this.cardImageFrame.setImage(Card.getCardImage(monsterCard));
        else
            this.cardImageFrame.setImage(GameHouse.getBackOfCardImage());
    }
}
//TODO game map and where to add it