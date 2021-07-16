package view.controller;

import animatefx.animation.Tada;
import controller.menues.menuhandlers.menucontrollers.WelcomePageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.Menu;
import view.AudioHandler;
import view.AudioPath;
import view.ClickButtonHandler;
import view.SoundEffectHandler;

import java.io.IOException;


public class WelcomeView {
    public Button Register;
    public Button Login;
    public Button Exit;
    public Button Scoreboard;

    WelcomePageController controller;

    {
        controller = WelcomePageController.getInstance();
    }

    @FXML
    public void initialize() {
        AudioHandler initialTheme;
        if (AudioHandler.getPlaying() != null) {
            if (!AudioHandler.getPlayingAudioPath().equals(AudioPath.INITIAL_MENUS)) {
                initialTheme = new AudioHandler(AudioPath.INITIAL_MENUS);
                AudioHandler.getPlaying().getMediaPlayer().stop();
                initialTheme.play();
            }
        } else {
            initialTheme = new AudioHandler(AudioPath.INITIAL_MENUS);
            initialTheme.play();
        }
    }

    public void run(MouseEvent event) throws IOException {
        if (event.getSource() == Exit) {
            controller.exit();
        } else if (event.getSource() == Register) {
            controller.moveToPage(Register, Menu.REGISTER_MENU);
        } else if (event.getSource() == Login) {
            controller.moveToPage(Login, Menu.LOGIN_MENU);
        } else if (event.getSource() == Scoreboard) {
            controller.moveToPage(Scoreboard, Menu.SCOREBOARD_MENU);
        }
    }

    public void hoverAnimation(MouseEvent event) {
        ClickButtonHandler.getInstance().play();
        new Tada((Node) event.getSource()).play();
    }
}