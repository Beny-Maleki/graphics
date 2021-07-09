package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.gameprop.Selectable;


public abstract class GameHouse extends Pane {
    public boolean changePos;
    private Image cardImage;
    private ImageView imageView = new ImageView();

    public abstract Enum getState();

    public Image getCardImage() {
        return cardImage;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }
}
