package model.enums.MenusMassages;

public enum Scoreboard {
    CURRENT_MENU("Scoreboard menu"),
    SUCCESSFULLY_ENTER_MENU("you entered Scoreboard menu successfully"),
    SUCCESSFULLY_EXIT_MENU("Scoreboard menu exited successfully");

    private final String message;

    Scoreboard(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
