package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public abstract class GameHouse extends Pane {
    public abstract Enum getState();
    public boolean changePos;
    private Image cardImage;
    private ImageView imageView = new ImageView();

    public Image getCardImage() {
        return cardImage;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }
}
