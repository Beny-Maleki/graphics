package viewer.URLenums;

import model.enums.MenusMassages.Main;

public enum AddressFXML {
    REGISTER_MENU("registerMenu.fxml"),
    MAIN_MENU("mainMenu.fxml");
    public final String value;

    AddressFXML(String value) {
        this.value = "/fxml/" + value;
    }
}
