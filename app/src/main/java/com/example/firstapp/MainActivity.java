package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    private TextView login;
    private DBHelper dbHelper;
    private Cursor cursor;
    public static int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            this.currentFragment = new ChooseCountryFragment();
            replaceFragment(this.currentFragment);
        }

        this.dbHelper = new DBHelper(this, "bd", null, 1);

        login = findViewById(R.id.login);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("login");
            View head = navigationView.getHeaderView(0);
            TextView text = head.findViewById(R.id.loginHead);
            text.setText(userName);
        }

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem)
                {
                    menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                    switch (menuItem.getItemId()) {
                        case R.id.country_info_item:
                            currentFragment = new CountryInfoFragment();
                            replaceFragment(currentFragment);
                            break;
                        case R.id.saved_countries_item:
                            currentFragment = new SavedCountriesFragment();
                            replaceFragment(currentFragment);
                            break;
                        case R.id.choose_country_item:
                            currentFragment = new ChooseCountryFragment();
                            replaceFragment(currentFragment);
                            break;
                        case R.id.about_item:
                            currentFragment = new AboutFragment();
                            replaceFragment(currentFragment);
                            break;
                        default:
                            return false;
                    }
                    return true;
                }
            }
        );
    }

    /**
     * Replace fragment
     *
     * @param newFragment Fragment
     */
    public void replaceFragment(Fragment newFragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivityFrame, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
