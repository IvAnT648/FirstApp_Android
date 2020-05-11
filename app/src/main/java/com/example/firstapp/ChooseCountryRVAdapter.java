package com.example.firstapp;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChooseCountryRVAdapter
        extends RecyclerView.Adapter <ChooseCountryRVAdapter.SelectViewHolder>
{
    public ArrayList<CountryModel> countries;

    public static class SelectViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public SelectViewHolder(View v)
        {
            super(v);
            mTextView = v.findViewById(R.id.rv_item_country_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Click", "on item click");
                }
            });
        }
    }

    public ChooseCountryRVAdapter(ArrayList<CountryModel> countries)
    {
        this.countries = countries;
    }

    @Override
    public ChooseCountryRVAdapter.SelectViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.choose_country_rv_item, parent, false);
        view.setMinimumHeight(200);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectViewHolder holder, int position)
    {
        final String countryName = countries.get(position).getName();
        holder.mTextView.setText(countryName);

        int countryNameLength = countries.get(position).getName().length();
        if (countryNameLength < 6) {
            holder.mTextView.setTextColor(Color.RED);
        } else if (countryNameLength > 7) {
            holder.mTextView.setTextColor(Color.GREEN);
        } else {
            holder.mTextView.setTextColor(Color.BLUE);
        }
    }

    @Override
    public int getItemCount()
    {
        return countries.size();
    }
}
