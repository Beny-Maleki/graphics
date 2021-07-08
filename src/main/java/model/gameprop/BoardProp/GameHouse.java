package model.gameprop.BoardProp;

import javafx.scene.layout.Pane;

import javax.swing.text.html.ImageView;

public abstract class GameHouse extends Pane {
    public abstract Enum getState();
    public boolean changePos;
    public ImageView cardImageView;
}
