package view.controller;

import animatefx.animation.FadeInLeft;
import animatefx.animation.SlideInDown;
import controller.RockPaperScissorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.enums.GameEnums.PlayerTurn;
import model.enums.Menu;
import model.gameprop.GameInProcess;
import model.gameprop.Player;
import model.gameprop.gamemodel.Game;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    public ImageView choiceImageView;

    RockPaperScissorController controller = RockPaperScissorController.getInstance();

    private final String blockPic = "src/main/resources/graphicprop/images/RockPaperScissor/empty.png";

    private User userOne;
    private User userTwo;

    private String userOneChoice;
    private String userTwoChoice;


    @FXML
    public void initialize() throws FileNotFoundException {
        userOne = LoginUser.getUser();
        userTwo = LoginUser.getOpponent();

        setAvatars(userOne.getAvatarAddress(), userTwo.getAvatarAddress());

        setNicknames(userOne.getNickname(), userTwo.getNickname());

        blockUpBox();
    }

    private void setNicknames(String forDownNickname, String forUpNickname) {
        nicknameDown.setText(forDownNickname);
        nicknameUp.setText(forUpNickname);
    }

    private void setAvatars(String forFrameDown, String forFrameUp) throws FileNotFoundException {
        frameDown.setImage(new Image(new FileInputStream(forFrameDown)));
        frameUp.setImage(new Image(new FileInputStream(forFrameUp)));
    }

    private void blockUpBox() {
        try {
            FileInputStream fileInputStream;
            Image image;
            fileInputStream = new FileInputStream(blockPic);
            image = new Image(fileInputStream);

            rockUp.setImage(image);
            paperUp.setImage(image);
            scissorUp.setImage(image);

            new SlideInDown(rockUp).play();
            new SlideInDown(paperUp).play();
            new SlideInDown(scissorUp).play();

            rock.setOnMouseClicked(e -> {
                userOneChoice = "rock";
                try {
                    choiceImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/RockPaperScissor/rock-upside.jpg")));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                changeTurn();
            });

            paper.setOnMouseClicked(e -> {
                userOneChoice = "paper";
                try {
                    choiceImageView.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/RockPaperScissor/paper-upside.jpg")));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                changeTurn();
            });

            scissor.setOnMouseClicked(e -> {
                userOneChoice = "scissor";
                changeTurn();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeTurn() {
        try {
            setAvatars(userTwo.getAvatarAddress(), userOne.getAvatarAddress());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setNicknames(userTwo.getNickname(), userOne.getNickname());

        new FadeInLeft(frameDown).play();
        new FadeInLeft(frameUp).play();
        new FadeInLeft(nicknameDown).play();
        new FadeInLeft(nicknameUp).play();

        new SlideInDown(rock).play();
        new SlideInDown(paper).play();
        new SlideInDown(scissor).play();

        rock.setOnMouseClicked(e -> {
            userTwoChoice = "rock";
            analyzeResult();
        });

        paper.setOnMouseClicked(e -> {
            userTwoChoice = "paper";
            analyzeResult();
        });

        scissor.setOnMouseClicked(e -> {
            userTwoChoice = "scissor";
            analyzeResult();
        });

    }

    private void analyzeResult() {
        PlayerTurn result = RockPaperScissorController.recognizeGameWinner(userOneChoice, userTwoChoice);
        if (result == PlayerTurn.None) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Draw!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    initialize();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        } else if (result == PlayerTurn.PLAYER_ONE) {
            makeGame(userOne, userTwo);
        } else if (result == PlayerTurn.PLAYER_TWO) {
            makeGame(userTwo, userOne);
        }
    }

    private void makeGame(User playerOneUser, User playerTwoUser) {
        Player playerOne;
        Player playerTwo;
        try {
            playerOne = new Player(playerOneUser, 0);
            playerTwo = new Player(playerTwoUser, 0);
            GameInProcess.setGame(new Game(playerOne, playerTwo, RockPaperScissorController.getNumOfRounds()));
            try {
                controller.moveToPage(rock, Menu.GAME_PAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    private void hoverOnImage(ImageView image) {
        image.setOnMouseEntered(event -> {
            (image).setScaleX(1.1);
            (image).setScaleY(1.1);
        });
        image.setOnMouseExited(event -> {
            (image).setScaleX(1.0);
            (image).setScaleY(1.0);
        });
    }
}
