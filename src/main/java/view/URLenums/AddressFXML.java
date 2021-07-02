package view.URLenums;

public enum AddressFXML {
    REGISTER_MENU("registerMenu.fxml"),
    MAIN_MENU("mainMenu.fxml");
    public final String value;

    AddressFXML(String value) {
        this.value = "/graphicprop/fxml/" + value;
    }
}
