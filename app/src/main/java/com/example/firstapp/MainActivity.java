package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceTransaction(new ChooseCountryFragment());
        }

        login = findViewById(R.id.login);
        mDrawerLayout = findViewById(R.id.drawer_layout);
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
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.country_info_item:
                                replaceTransaction(new CountryInfoFragment());
                                break;
                            case R.id.saved_countries_item:
                                replaceTransaction(new SavedCountriesFragment());
                                break;
                            case R.id.choose_country_item:
                                replaceTransaction(new ChooseCountryFragment());
                                break;
                            case R.id.about_item:
                                replaceTransaction(new AboutFragment());
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });

    }

    private void replaceTransaction(Fragment newFragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainActivityFrame, newFragment)
                .addToBackStack(null)
                .commit();
    }
}
