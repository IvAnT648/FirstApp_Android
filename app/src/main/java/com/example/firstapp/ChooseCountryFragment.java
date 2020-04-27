package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ChooseCountryFragment extends Fragment
{
    private ArrayList<CountryModel> countries = new ArrayList<CountryModel>();

    public ChooseCountryFragment() { }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_choose_country, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rwCountriesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new ChooseCountryRVAdapter(this.extractCountries());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<CountryModel> extractCountries()
    {
        for(String name : getResources().getStringArray(R.array.countries_list)) {
            countries.add(new CountryModel(name));
        }
        return countries;
    }
}
