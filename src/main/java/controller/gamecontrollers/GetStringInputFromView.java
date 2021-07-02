package controller.gamecontrollers;

import model.enums.GameEnums.RequestingInput;
import viewer.game.GetStringInput;

public class GetStringInputFromView {
    public static String getInputFromView(RequestingInput message) {
        return GetStringInput.getInput(message);
    }

    public static String getInputFromView(RequestingInput message, String name) {
        return GetStringInput.getInput(message, name);
    }
}
