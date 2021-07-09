package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.cardsProp.Card;
import model.cards.cardsProp.MagicCard;
import model.enums.GameEnums.SideOfFeature;
import model.enums.GameEnums.cardvisibility.MagicHouseVisibilityState;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.Selectable;
import model.gameprop.gamemodel.Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MagicHouse extends GameHouse implements Selectable {
    ImageView cardImage;
    MagicCard magicCard;
    MagicHouseVisibilityState state;

    {
        changePos = false;
        magicCard = null;
        state = MagicHouseVisibilityState.E;
    }

    public static MagicHouse getMagicHouseByMagicCard(MagicCard magicCard) {
        Game game = GameInProcess.getGame();

        ArrayList<SideOfFeature> sides = new ArrayList<>();
        sides.add(SideOfFeature.CURRENT);
        sides.add(SideOfFeature.OPPONENT);

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

    public MagicHouseVisibilityState getState() {
        return state;
    }

    public void setState(MagicHouseVisibilityState state) {
        this.state = state;
    }

    public MagicCard getMagicCard() {
        return magicCard;
    }

    public void setMagicCard(MagicCard magicCard) {
        this.magicCard = magicCard;
    }

    @Override
    public Card getCard() {
        return magicCard;
    }


    private void setCard(Card card) throws FileNotFoundException {
        String name = card.getName();
        String nameWithoutSpace = name.replaceAll("\\s+", "");
        Image image = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("src/main/resources/graphicprop/images/Cards/SpellTrap/" + nameWithoutSpace + ".jpg");
            image = new Image(fileInputStream);
            cardImage.setImage(image);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }
}
