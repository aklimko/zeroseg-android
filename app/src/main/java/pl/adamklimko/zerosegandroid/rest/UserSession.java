package pl.adamklimko.zerosegandroid.rest;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import pl.adamklimko.zerosegandroid.model.Token;

public class UserSession {
    private static SharedPreferences preferences;
    public static String PREFERENCES_NAME = "ZerosegPreferences";


    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        UserSession.preferences = preferences;
    }

    public static void setTokenInPreferences(Token token) {
        Editor editor = preferences.edit();
        editor.putString("Token", token.getToken());
        editor.putString("Expiration Date", token.getExpirationDate());
        editor.apply();
    }

    public static String getToken() {
        return preferences.getString("Token", "");
    }

    public static boolean hasToken() {
         return !TextUtils.isEmpty(preferences.getString("Token", ""));
    }
}
