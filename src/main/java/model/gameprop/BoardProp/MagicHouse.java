package model.gameprop.BoardProp;

import javafx.scene.image.Image;
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
    MagicCard magicCard;
    MagicHouseVisibilityState state;

    {
        changePos = false;
        magicCard = null;
        state = MagicHouseVisibilityState.E;
    }

    public MagicHouse(int index) {
        this.index = index;
        this.getChildren().add(cardImageFrame);
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

    public String getState() {
        return state.toString();
    }

    public void setState(MagicHouseVisibilityState state) {
        this.state = state;
    }

    @Override
    public Image getCardImage() {
        switch (state) {
            case E:
                return null;
            case H:
                return GameHouse.getBackOfCardImage();
            default:
                return Card.getCardImage(this.getCard());
        }
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
            cardImageFrame.setImage(image);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }


    public void setImageOfCard() {
        this.getChildren().add(cardImageFrame);
    }

    @Override
    public int getIndex() {
        return index;
    }

}
