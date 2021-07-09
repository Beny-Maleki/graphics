package view.controller;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import controller.menues.menuhandlers.menucontrollers.DuelPageController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.enums.Menu;
import model.userProp.Deck;
import model.userProp.LoginUser;
import model.userProp.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class DuelPageView {

    public Button twoPlayerGame;
    public Button onePlayerGame;
    public Button back;
    public ChoiceBox<Integer> roundNumber;
    public Pane gameSetUpPopUp;
    public Pane opponentFinder;
    public TextField opponentNickname;
    public ImageView opponentAvatar;
    public Label error;
    public Label nickname;
    public Label score;
    public Label doesHaveActiveDeck;
    public Button cancel;
    public Button duel;
    public AnchorPane mainPain;
    private DuelPageController controller;

    {
        controller = new DuelPageController();
    }

    @FXML
    public void initialize() {
        gameSetUpPopUp.setVisible(false);
        duel.setVisible(false);
        resetFinder();
        roundNumber.setItems(FXCollections.observableArrayList(Arrays.asList(1, 3)));
        opponentNickname.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                User user = findUserAndDisplayPopUp();
                if (user == null || user == LoginUser.getUser()) {
                    noPlayerFindForm();
                } else {
                    setUserNameAndScore(user);
                    Deck deck = user.getActiveDeck();
                    doesPlayerHaveActiveDeck(deck);
                }
            }
        });
        cancel.setOnMouseClicked(event -> {
            gameSetUpPopUp.setVisible(false);
            mainPain.getChildren().forEach(node -> node.setDisable(false));
        });

        duel.setOnMouseClicked(event -> {
            try {
                LoginUser.setOpponent(controller.findOpponent(opponentNickname.getText()));
                controller.moveToPage(duel , Menu.ROCK_PAPER_SCISSOR_PAGE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void doesPlayerHaveActiveDeck(Deck deck) {
        if (deck == null) {
            doesHaveActiveDeck.setText("No Active Deck");
        } else {
            doesHaveActiveDeck.setText("Have Active Deck");
            duel.setVisible(true);
            new FadeIn(duel).play();
        }
        new FadeIn(doesHaveActiveDeck).play();
    }

    private void setUserNameAndScore(User user) {
        error.setVisible(false);
        try {
            opponentAvatar.setImage(new Image(new FileInputStream(user.getAvatarAddress())));
            new FadeIn(opponentAvatar).play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nickname.setText("name : " + user.getNickname());
        score.setText("score : " + String.valueOf(user.getScore()));
        new FadeIn(score).play();
    }

    private void noPlayerFindForm() {
        doesHaveActiveDeck.setText("");
        try {
            opponentAvatar.setImage(new Image(new FileInputStream("src/main/resources/graphicprop/images/questionMark.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        error.setVisible(true);
    }

    private User findUserAndDisplayPopUp() {
        opponentFinder.setVisible(true);
        new FadeIn(opponentFinder).play();
        return controller.findOpponent(opponentNickname.getText());
    }

    private void resetFinder() {
        opponentFinder.setVisible(false);
        error.setVisible(false);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            controller.moveToPage(back, Menu.MAIN_MENU);
        } else if (event.getSource() == twoPlayerGame) {
            gameSetUpPopUp.setVisible(true);
            new BounceIn(gameSetUpPopUp).play();
            mainPain.getChildren().forEach(node -> {
                if (node != gameSetUpPopUp) {
                    node.setDisable(true);
                }
            });
        }
    }
}
