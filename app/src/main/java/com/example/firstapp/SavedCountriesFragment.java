package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SavedCountriesFragment extends Fragment implements CustomItemClickListener
{
    private ArrayList<CountryModel> savedCountries = new ArrayList<CountryModel>();

    public SavedCountriesFragment() { }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_saved_countries, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rwSavedCountriesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new SavedCountriesRVAdapter(
                getContext(),
                this.extractCountries(),
                this
        );
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<CountryModel> extractCountries()
    {
        for (String name : getResources().getStringArray(R.array.saved_countries_list)) {
            savedCountries.add(new CountryModel(name));
        }
        return savedCountries;
    }

    @Override
    public void onItemClick(CountryModel country)
    {

    }

    @Override
    public void onLongItemClick(CountryModel country)
    {

    }
}
