package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import view.URLenums.AddressFXML;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class SceneCreator {
    public static Scene create(AddressFXML addressFXML) throws FileNotFoundException{
        try {
            Scene scene;
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneCreator.class.getResource(addressFXML.value)));
            scene = new Scene(root);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }

    }
}
