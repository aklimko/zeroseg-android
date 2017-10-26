package pl.adamklimko.zerosegandroid;

public class UserToken {
    private static String userToken;

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        UserToken.userToken = userToken;
    }
}
