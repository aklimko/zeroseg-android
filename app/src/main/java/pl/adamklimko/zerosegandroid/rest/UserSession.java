package pl.adamklimko.zerosegandroid.rest;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import pl.adamklimko.zerosegandroid.model.Token;

public class UserSession {
    private static SharedPreferences preferences;

    public static final String PREFERENCES_NAME = "Zeroseg Preferences";
    private static final String PREFERENCES_USERNAME = "Username";
    private static final String PREFERENCES_TOKEN = "Token";
    private static final String PREFERENCES_EXPIRATION = "Expiration Date";

    private static boolean firstStarted = true;


    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        UserSession.preferences = preferences;
    }

    public static void setTokenInPreferences(Token token) {
        Editor editor = preferences.edit();
        editor.putString(PREFERENCES_TOKEN, token.getToken());
        editor.putString(PREFERENCES_EXPIRATION, token.getExpirationDate());
        editor.apply();
    }

    public static void setUsernameInPreferences(String username) {
        Editor editor = preferences.edit();
        editor.putString(PREFERENCES_USERNAME, username);
        editor.apply();
    }

    public static String getUsername() {
        return preferences.getString(PREFERENCES_USERNAME, "");
    }

    public static String getToken() {
        return preferences.getString(PREFERENCES_TOKEN, "");
    }

    public static boolean hasToken() {
         return !TextUtils.isEmpty(preferences.getString(PREFERENCES_TOKEN, ""));
    }

    public static void resetSession() {
        Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean isFirstStarted() {
        return firstStarted;
    }

    public static void setFirstStarted(boolean firstStarted) {
        UserSession.firstStarted = firstStarted;
    }
}
