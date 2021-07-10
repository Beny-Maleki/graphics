package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public abstract class GameHouse extends Pane {
    public boolean changePos;
    protected ImageView cardImageFrame;
    protected Image cardImage;

    public GameHouse() {
        cardImageFrame = new ImageView();
        cardImageFrame.setFitWidth(47);
        cardImageFrame.setFitHeight(70);
    }

    public abstract Enum getState();

    public abstract Image getCardImage();
}
