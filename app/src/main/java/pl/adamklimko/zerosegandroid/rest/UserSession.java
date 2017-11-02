package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import pl.adamklimko.zerosegandroid.model.Token;

import static android.content.Context.MODE_PRIVATE;

public class UserSession {
    
    private static SharedPreferences preferences;

    private static final String PREFERENCES = "zeroseg_preferences";
    private static final String PREFERENCES_USERNAME = "username";
    private static final String PREFERENCES_FULLNAME = "full_name";
    private static final String PREFERENCES_TOKEN = "token";
    private static final String PREFERENCES_EXPIRATION = "expiration_date";

    private static boolean firstStarted = true;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void initPreferences(Context context) {
        preferences = context.getSharedPreferences(UserSession.PREFERENCES, MODE_PRIVATE);
    }

    public static void setPreferences(SharedPreferences preferences) {
        UserSession.preferences = preferences;
    }

    public static void setTokenInPreferences(Token token) {
        final Editor editor = preferences.edit();
        editor.putString(PREFERENCES_TOKEN, token.getToken());
        editor.putString(PREFERENCES_EXPIRATION, token.getExpirationDate());
        editor.apply();
    }

    public static void setUsernameInPreferences(String username) {
        final Editor editor = preferences.edit();
        editor.putString(PREFERENCES_USERNAME, username);
        editor.apply();
    }

    public static void setFullNameInPreferences(String fullName) {
        final Editor editor = preferences.edit();
        editor.putString(PREFERENCES_FULLNAME, fullName);
        editor.apply();
    }

    public static String getUsername() {
        return preferences.getString(PREFERENCES_USERNAME, "");
    }

    public static String getFullName() {
        return preferences.getString(PREFERENCES_FULLNAME, "");
    }

    public static String getToken() {
        return preferences.getString(PREFERENCES_TOKEN, "");
    }

    public static boolean hasToken() {
         return !TextUtils.isEmpty(preferences.getString(PREFERENCES_TOKEN, ""));
    }

    public static void resetSession() {
        final Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean isAppJustStarted() {
        return firstStarted;
    }

    public static void setFirstStarted(boolean firstStarted) {
        UserSession.firstStarted = firstStarted;
    }
}
