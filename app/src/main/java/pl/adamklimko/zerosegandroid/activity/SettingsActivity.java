package pl.adamklimko.zerosegandroid.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import pl.adamklimko.zerosegandroid.R;
import pl.adamklimko.zerosegandroid.model.Profile;
import pl.adamklimko.zerosegandroid.rest.ApiClient;
import pl.adamklimko.zerosegandroid.rest.UserSession;
import pl.adamklimko.zerosegandroid.rest.ZerosegService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private boolean inFragment = false;
    private Profile profileBeforeChanges;
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            final String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                final ListPreference listPreference = (ListPreference) preference;
                final int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (inFragment) {
            updateProfileIfChanged();
        }
        super.onBackPressed();
    }

    private void updateProfileIfChanged() {
        final Profile profileAfterChanges = new Profile(UserSession.getFullName(), UserSession.getFacebookId());
        if (profileChanged(profileAfterChanges)) {
            informToUpdateProfileViewInDrawer();
            updateProfilePreferencesInApi(profileAfterChanges);
        }
    }

    private void informToUpdateProfileViewInDrawer() {
        Intent intent = new Intent("update_profile");
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean profileChanged(Profile profileAfterChanges) {
        return !profileBeforeChanges.equals(profileAfterChanges);
    }

    private void updateProfilePreferencesInApi(Profile profileAfterChanges) {
        final Context mContext = getApplicationContext();
        final ZerosegService zerosegService = ApiClient.createServiceWithAuth(ZerosegService.class, mContext);
        final Call<Profile> patchProfileCall = zerosegService.patchProfile(profileAfterChanges);
        patchProfileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Profile updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(mContext, "Cannot connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || ProfilePreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ProfilePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_profile);
            setHasOptionsMenu(true);

            ((SettingsActivity) getActivity()).inFragment = true;
            ((SettingsActivity) getActivity()).profileBeforeChanges = new Profile(UserSession.getFullName(), UserSession.getFacebookId());

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(UserSession.FULL_NAME));
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(UserSession.FACEBOOK_ID));
        }
    }
}
