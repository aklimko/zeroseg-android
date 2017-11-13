package pl.adamklimko.zerosegandroid.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isKeyboardOpen(View contentView) {
        final Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        final int screenHeight = contentView.getRootView().getHeight();
        final int keypadHeight = screenHeight - r.bottom;

        return keypadHeight > screenHeight * 0.15;
    }
}
