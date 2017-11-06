package pl.adamklimko.zerosegandroid.rest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import pl.adamklimko.zerosegandroid.util.ProfilePictureUtil;

public class ProfilePictureTask extends AsyncTask<String, Void, Bitmap> {
    private Context mContext;

    public ProfilePictureTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        final String facebookId = params[0];
        return ProfilePictureUtil.getProfilePicture(facebookId);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        ProfilePictureUtil.saveProfilePicture(bitmap, mContext);
        informToRedrawProfilePictureViewInDrawer();
        super.onPostExecute(bitmap);
    }

    private void informToRedrawProfilePictureViewInDrawer() {
        Intent intent = new Intent("redraw_picture");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
