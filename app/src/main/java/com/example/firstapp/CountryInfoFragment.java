package com.example.firstapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountryInfoFragment extends Fragment
{
    private CountryModel entity;
    public CountryInfoFragment() { }

    public CountryInfoFragment(CountryModel countryModel)
    {
        this.entity = countryModel;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_country_info, container, false);
        TextView tv = view.findViewById(R.id.countryName);
        if (this.entity != null) {
            tv.setText(this.entity.getName());
        }
        return view;
    }
}
