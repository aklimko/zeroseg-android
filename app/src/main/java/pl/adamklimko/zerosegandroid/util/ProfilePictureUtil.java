package pl.adamklimko.zerosegandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import pl.adamklimko.zerosegandroid.model.Profile;
import pl.adamklimko.zerosegandroid.rest.ApiClient;
import pl.adamklimko.zerosegandroid.rest.UserSession;
import pl.adamklimko.zerosegandroid.rest.ZerosegService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfilePictureUtil {

    private static final String PROFILE_PICTURE = "profile_picture.jpg";

    public static Bitmap getProfilePicture(String facebookId) {
        try {
            URL url = new URL("https://graph.facebook.com//v2.10/" + facebookId + "/picture?type=square&height=300&width=300");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            final InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveProfilePicture(Bitmap bitmapImage, Context context) {
        final File path = new File(context.getFilesDir(), PROFILE_PICTURE);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            fos = null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadImageFromStorage(Context context) {
        try {
            final File profilePicture = new File(context.getFilesDir(), PROFILE_PICTURE);
            return BitmapFactory.decodeStream(new FileInputStream(profilePicture));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void getUserProfile(Context context) {
        final ZerosegService authService = ApiClient.createServiceWithAuth(ZerosegService.class, context);
        final Call<Profile> profileCall = authService.getProfile();
        final Response<Profile> response;
        try {
            response = profileCall.execute();
        } catch (IOException e) {
            return;
        }
        final Profile profile = response.body();
        if (profile == null) {
            return;
        }
        UserSession.setProfileDataInPreferences(profile);
        final String facebookId = profile.getFacebookId();
        if (TextUtils.isEmpty(facebookId)) {
            return;
        }
        setProfilePicture(facebookId, context);
    }

    public static void setProfilePicture(String facebookId, Context context) {
        final Bitmap profilePicture = ProfilePictureUtil.getProfilePicture(facebookId);
        if (profilePicture == null) {
            return;
        }
        ProfilePictureUtil.saveProfilePicture(profilePicture, context);
    }
}
