package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SavedCountriesFragment extends Fragment
{
    private ArrayList<CountryModel> savedCountries = new ArrayList<CountryModel>();
    private SavedCountriesRVAdapter adapter;
    private DBHelper dbHelper;

    public SavedCountriesFragment() { }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_saved_countries, container, false);
        this.dbHelper = new DBHelper(getContext(), "bd", null, 1);

        RecyclerView recyclerView = view.findViewById(R.id.rwSavedCountriesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SavedCountriesRVAdapter(
                getContext(),
                this.loadUserCountries(),
                this
        );
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<CountryModel> loadUserCountries()
    {
        return this.dbHelper.loadUsersCountry(MainActivity.userID);
    }

    private ArrayList<CountryModel> extractCountries()
    {
        for (String name : getResources().getStringArray(R.array.saved_countries_list)) {
            savedCountries.add(new CountryModel(name));
        }
        return savedCountries;
    }

    public boolean deleteCountry(CountryModel country)
    {
        if (country == null) {
            return false;
        }
        if (dbHelper.deleteSavedCountry(MainActivity.userID, country.getId()) != 0) {
            this.updateList();
            return true;
        }
        return false;
    }

    public void updateList()
    {
        ArrayList<CountryModel> countries = this.loadUserCountries();
        this.adapter.setSavedCountries(countries);
        this.adapter.notifyDataSetChanged();
    }
}
