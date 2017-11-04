package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import pl.adamklimko.zerosegandroid.model.Profile;
import pl.adamklimko.zerosegandroid.model.Token;

public class UserSession {
    
    private static SharedPreferences preferences;

    private static final String USERNAME = "username";
    public static final String FULL_NAME = "full_name";
    public static final String FACEBOOK_ID = "facebook_id";
    private static final String TOKEN = "token";
    private static final String EXPIRATION_DATE = "expiration_date";

    private static boolean firstStarted = true;

    public static void initPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setTokenInPreferences(Token token) {
        final Editor editor = preferences.edit();
        editor.putString(TOKEN, token.getToken());
        editor.putString(EXPIRATION_DATE, token.getExpirationDate());
        editor.apply();
    }

    public static void setUsernameInPreferences(String username) {
        final Editor editor = preferences.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static String getUsername() {
        return preferences.getString(USERNAME, "");
    }

    public static String getFullName() {
        return preferences.getString(FULL_NAME, "");
    }

    public static String getFacebookId() {
        return preferences.getString(FACEBOOK_ID, "");
    }

    public static String getToken() {
        return preferences.getString(TOKEN, "");
    }

    public static boolean hasToken() {
         return !TextUtils.isEmpty(preferences.getString(TOKEN, ""));
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

    public static void setProfileDataInPreferences(Profile profile) {
        final Editor editor = preferences.edit();

        final String fullName = profile.getFullName();
        if (!TextUtils.isEmpty(fullName)) {
            editor.putString(FULL_NAME, fullName);
        }

        final String facebookId = profile.getFacebookId();
        if (!TextUtils.isEmpty(fullName)) {
            editor.putString(FACEBOOK_ID, facebookId);
        }

        editor.apply();
    }
}
