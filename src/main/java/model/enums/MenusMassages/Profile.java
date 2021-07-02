package model.enums.MenusMassages;

public enum Profile {
    CURRENT_MENU("Profile controller"),
    SUCCESSFULLY_ENTER_MENU("you entered Profile controller successfully"),
    SUCCESSFULLY_EXIT_MENU("Profile controller exited successfully"),
    SUCCESSFULLY_CHANGE_NICKNAME("nickname changed successfully!"),
    SUCCESSFULLY_CHANGE_PASSWORD("password changed successfully!");

    private final String message;

    Profile(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
