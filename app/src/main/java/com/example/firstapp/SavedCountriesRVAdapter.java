package com.example.firstapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedCountriesRVAdapter
        extends RecyclerView.Adapter<SavedCountriesRVAdapter.SavedViewHolder>
{
    private ArrayList<CountryModel> savedCountries;

    public static class SavedViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public SavedViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.rv_item_saved_countries);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Click", "on item click");
                }
            });
        }
    }

    public SavedCountriesRVAdapter(ArrayList<CountryModel> savedCountries)
    {
        this.savedCountries = savedCountries;
    }

    @Override
    public SavedCountriesRVAdapter.SavedViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.saved_countries_rv_item, parent, false);
        return new SavedCountriesRVAdapter.SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedCountriesRVAdapter.SavedViewHolder holder, int position)
    {
        holder.mTextView.setText(savedCountries.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return savedCountries.size();
    }
}
