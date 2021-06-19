package model.userProp;

public class LoginUser {
    public static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoginUser.user = user;
    }
}
