package pl.adamklimko.zerosegandroid;

public class UserToken {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserToken.token = token;
    }
}
