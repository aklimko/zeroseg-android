package pl.adamklimko.zerosegandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfilePictureUtil {

    private static final String PROFILE_PICTURE = "profile_picture.jpg";

    public static Bitmap getProfilePicture(String facebookId) {
        try {
            URL url = new URL("https://graph.facebook.com//v2.10/" + facebookId + "/picture");
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
}
