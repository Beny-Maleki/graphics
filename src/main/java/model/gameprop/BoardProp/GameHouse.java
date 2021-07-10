package model.gameprop.BoardProp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public abstract class GameHouse extends Pane {
    private static Image backOfCardImage;

    static {
        try {
            backOfCardImage = new Image(new FileInputStream("src/main/resources/graphicprop/images/Cards/Monsters/Unknown.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean changePos;
    protected ImageView cardImageFrame;
    protected int index;

    public GameHouse() {
        cardImageFrame = new ImageView();
        cardImageFrame.setFitWidth(47);
        cardImageFrame.setFitHeight(70);
    }

    public static Image getBackOfCardImage() {
        return backOfCardImage;
    }

    public abstract Image getCardImage();
}
