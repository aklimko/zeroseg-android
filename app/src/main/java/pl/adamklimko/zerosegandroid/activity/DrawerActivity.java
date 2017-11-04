package pl.adamklimko.zerosegandroid.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import pl.adamklimko.zerosegandroid.R;
import pl.adamklimko.zerosegandroid.fragment.MessageFragment;
import pl.adamklimko.zerosegandroid.fragment.SettingsFragment;
import pl.adamklimko.zerosegandroid.rest.UserSession;
import pl.adamklimko.zerosegandroid.util.ProfilePictureUtil;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FrameLayout viewStub; //This is the framelayout to keep your content view
    private NavigationView navigationView; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mProfilePicture;
    private TextView mUsername;
    private MessageFragment messageFragment;
    private SettingsFragment settingsFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.drawer_layout);

        viewStub = (FrameLayout) findViewById(R.id.view_stub);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(0);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final View v = navigationView.getHeaderView(0);
        mUsername = v.findViewById(R.id.header_username);
        final String fullName = UserSession.getFullName();
        if (!TextUtils.isEmpty(fullName)) {
            mUsername.setText(fullName);
        } else {
            mUsername.setText(UserSession.getUsername());
        }

        mProfilePicture = v.findViewById(R.id.header_image);
        final Bitmap profile = ProfilePictureUtil.loadImageFromStorage(getApplicationContext());
        if (profile != null) {
            mProfilePicture.setImageBitmap(profile);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(this);

        messageFragment = MessageFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
        manager = getSupportFragmentManager();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (viewStub != null) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            final View stubView = inflater.inflate(layoutResID, viewStub, false);
            viewStub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (viewStub != null) {
            final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            viewStub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (viewStub != null) {
            viewStub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (isItemChecked(id)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        switch (id) {
            case R.id.nav_message:
                manager.beginTransaction()
                        .replace(R.id.fragment_container, messageFragment)
                        .commit();
                break;
            case R.id.nav_settings:
                manager.beginTransaction()
                        .replace(R.id.fragment_container, settingsFragment)
                        .commit();
                break;
            case R.id.nav_logout:
                switchToLoginActivity();
                UserSession.resetSession();
                Toast.makeText(getApplicationContext(), "Successful logout", Toast.LENGTH_SHORT).show();
                break;
        }

        uncheckAllCheckedMenuItems();
        navigationView.getMenu().findItem(id).setChecked(true);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isItemChecked(final int id) {
        return navigationView.getMenu().findItem(id).isChecked();
    }

    private void switchToLoginActivity() {
        final Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(login);
        finish();
    }

    private void uncheckAllCheckedMenuItems() {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    if (subMenuItem.isChecked()) {
                        subMenuItem.setChecked(false);
                    }
                }
            } else if (item.isChecked()) {
                item.setChecked(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewStub = null;
        navigationView = null;
        mDrawerLayout = null;
        mDrawerToggle = null;
        manager = null;
        messageFragment = null;
        settingsFragment = null;
        mUsername = null;
        mProfilePicture = null;
    }
}
