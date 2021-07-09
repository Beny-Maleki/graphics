package view.controller;

import animatefx.animation.FlipInX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RockPaperScissorView {
    public Label nicknameDown;
    public Label nicknameUp;
    public ImageView frameUp;
    public ImageView frameDown;
    public ImageView scissor;
    public ImageView rock;
    public ImageView paper;
    public ImageView scissorUp;
    public ImageView rockUp;
    public ImageView paperUp;
    private User userOne;
    private User userTwo;
    private String blockPic = "src/main/resources/graphicprop/images/RockPaperScissor/empty.png";

    @FXML
    public void initialize() throws FileNotFoundException {
        userOne = LoginUser.getUser();
        userTwo = LoginUser.getOpponent();
        nicknameDown.setText(userOne.getNickname());
        nicknameUp.setText(userTwo.getNickname());

        blockUpBox();
        frameDown.setImage(new Image(new FileInputStream(userOne.getAvatarAddress())));
        frameDown.setImage(new Image(new FileInputStream(userTwo.getAvatarAddress())));
    }

    private void blockUpBox() {
        try {
            rockUp.setImage(new Image(new FileInputStream(blockPic)));
            paperUp.setImage(new Image(new FileInputStream(blockPic)));
            scissorUp.setImage(new Image(new FileInputStream(blockPic)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new FlipInX(rockUp).play();
        new FlipInX(paperUp).play();
        new FlipInX(scissorUp).play();
    }

    private void hoverOnImage(ImageView image) {
        image.setOnMouseEntered(event -> {
            (image).setScaleX(1.1);
            (image).setScaleY(1.1);
        });
        image.setOnMouseExited(event -> {
                    (image).setScaleX(1.0);
                    (image).setScaleY(1.0);
                }
        );
    }
}
