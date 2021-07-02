package model.enums.MenusMassages;

public enum Scoreboard {
    CURRENT_MENU("Scoreboard controller"),
    SUCCESSFULLY_ENTER_MENU("you entered Scoreboard controller successfully"),
    SUCCESSFULLY_EXIT_MENU("Scoreboard controller exited successfully");

    private final String message;

    Scoreboard(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
