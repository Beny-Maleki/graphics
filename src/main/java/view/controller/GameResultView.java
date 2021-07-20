package view.controller;

import animatefx.animation.FadeOut;
import animatefx.animation.Tada;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.enums.Menu;
import model.gameprop.Player;
import view.AudioHandler;
import view.AudioPath;
import view.ClickButtonHandler;

import java.io.IOException;

public class GameResultView {
    public Label winner;
    public Button back;
    public Button nextRound;
    public AnchorPane root;
    public Label title;

    public void setDetails(Player player, String winOrLose, boolean isFinished) {
        winner.setText(player.getUser().getNickname());
        AudioPath audioPath;
        if (winOrLose.equals("win")) {
            audioPath = AudioPath.WIN;
        } else {
            audioPath = AudioPath.LOSE;
            title.setText("Looser");
        }
        AudioHandler winnerTheme;
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(audioPath)) {
                winnerTheme = new AudioHandler(audioPath);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                winnerTheme.play();
            }
        } else {
            winnerTheme = new AudioHandler(audioPath);
            winnerTheme.play();
        }
        nextRound.setVisible(!isFinished);
        back.setVisible(isFinished);
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == back) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graphicprop/fxml/mainPage.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) back.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            scene.getRoot().requestFocus();
            stage.show();
        } else if ((event.getSource() == nextRound)) {
            FadeOut fadeOut = new FadeOut(root);
            fadeOut.setSpeed(0.5);
            fadeOut.play();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Menu.BETWEEN_ROUNDS.getAddress()));
            Parent parent = loader.load();
            Stage stage = (Stage) nextRound.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            scene.getRoot().requestFocus();
            stage.show();
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Tada((Node) event.getSource()).play();
    }
}
