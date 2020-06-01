package com.example.firstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedCountriesRVAdapter
        extends RecyclerView.Adapter<SavedCountriesRVAdapter.SavedViewHolder>
{
    private ArrayList<CountryModel> savedCountries;
    public SavedCountriesFragment listener;
    public AlertDialog.Builder alertDialogBuilder;
    public CountryModel targetCountry;
    public Context ctx;

    public SavedCountriesRVAdapter(Context context, ArrayList<CountryModel> savedCountries, SavedCountriesFragment listener)
    {
        this.ctx = context;
        this.listener = listener;
        this.savedCountries = savedCountries;
        this._createAlertDialogBuilder();
    }

    private void _createAlertDialogBuilder()
    {
        this.alertDialogBuilder = new AlertDialog.Builder(this.ctx)
                .setCancelable(true)
                .setNegativeButton(R.string.cad_cancelButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("== AlertDialog", "on cancel button click");
                            }
                        }
                )
                .setPositiveButton(R.string.cad_delButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("== AlertDialog", "on delete button click");
                                String message;
                                if (listener.deleteCountry(targetCountry)) {
                                    message = "Страна " + targetCountry.getName() + " успешно удалена";
                                } else {
                                    message = "Не удалось удалить страну";
                                }
                                Toast.makeText(
                                        ctx,
                                        message,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
    }

    @Override
    public SavedCountriesRVAdapter.SavedViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
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
        holder.country = savedCountries.get(position);
    }

    @Override
    public int getItemCount()
    {
        return savedCountries.size();
    }

    public class SavedViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public CountryModel country;

        public SavedViewHolder(View v)
        {
            super(v);
            mTextView = v.findViewById(R.id.rv_item_saved_countries);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Click", "on item click");
                    targetCountry = country;
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.i("== Click", "on long item click");
                    targetCountry = country;
                    alertDialogBuilder.setTitle(mTextView.getText());
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }

    public void setSavedCountries(ArrayList<CountryModel> newList)
    {
        this.savedCountries = newList;
    }
}
