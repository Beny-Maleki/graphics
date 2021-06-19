package model.gameprop.BoardProp;

import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;

import java.util.ArrayList;

public class MagicHouse extends GameHouse {
    MagicCard magicCard;
    MagicHouseVisibilityState state;

    {
        changePos = false;
        magicCard = null;
        state = MagicHouseVisibilityState.E;
    }

    public void setMagicCard(MagicCard magicCard) {
        this.magicCard = magicCard;
    }

    public MagicHouseVisibilityState getState() {
        return state;
    }

    public void setState(MagicHouseVisibilityState state) {
        this.state = state;
    }

    public MagicCard getMagicCard() {
        return magicCard;
    }

    public static MagicHouse getMagicHouseByMagicCard(MagicCard magicCard) {
        Game game = GameInProcess.getGame();

        ArrayList<SideOfFeature> sides = new ArrayList<>();
        sides.add(SideOfFeature.CURRENT); sides.add(SideOfFeature.OPPONENT);

        for (SideOfFeature side : sides) {
            Player player = game.getPlayer(side);
            MagicHouse[] magicHouses = player.getBoard().getMagicHouse();

            for (MagicHouse magicHouse : magicHouses) {
                if (magicHouse.getMagicCard().equals(magicCard)) {
                    return magicHouse;
                }
            }
        }
        return null;
    }


}
