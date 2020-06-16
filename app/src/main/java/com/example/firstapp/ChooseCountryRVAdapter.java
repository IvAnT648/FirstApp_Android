package com.example.firstapp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChooseCountryRVAdapter
        extends RecyclerView.Adapter <ChooseCountryRVAdapter.SelectViewHolder>
{
    public ArrayList<CountryModel> countries;
    public Context ctx;
    public AlertDialog.Builder alertDialogBuilder;
    public ChooseCountryFragment fragmentListener;
    public CountryModel selectedCountry;
    private DBHelper dbHelper;
    private static int notifyCounter = 0;

    public ChooseCountryRVAdapter(Context context, ArrayList<CountryModel> countries, ChooseCountryFragment listener)
    {
        this.ctx = context;
        this.fragmentListener = listener;
        this.countries = countries;
        this._createAlertDialogBuilder();
        this.dbHelper = new DBHelper(ctx, "bd", null, 1);
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
                .setPositiveButton(R.string.cad_saveButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("== AlertDialog", "on save button click");
                                String messageText;
                                if (dbHelper.saveUserCountry(MainActivity.userID, selectedCountry.getId()) == 0) {
                                    messageText = "Страна уже сохранена";
                                } else {
                                    messageText = "Страна успешно сохранена";
                                }
                                Toast.makeText(
                                        ctx,
                                        messageText,
                                        Toast.LENGTH_SHORT
                                ).show();
                                this.createNotification(messageText);
                            }

                            private void createNotification(String message)
                            {
                                if (ctx == null) {
                                    return;
                                }

                                // Create an explicit intent for an Activity in your app
                                Intent intent = new Intent(ctx, MainActivity.class);
                                intent.putExtra("country_id", selectedCountry.getId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, MainActivity.NOTIFICATION_CHANNEL_ID)
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle("Путеводитель")
                                        .setContentText(message)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(pendingIntent)
                                        .setAutoCancel(true);

                                NotificationManagerCompat
                                    .from(ctx)
                                    .notify(++notifyCounter, builder.build());
                            }
                        }
                )
                .setNeutralButton(R.string.cad_infoButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("== AlertDialog", "on info button click");
                                if (selectedCountry != null) {
                                    fragmentListener.openCountryInfo(selectedCountry);
                                }
                            }
                        }
                )
                .setNeutralButton(R.string.cad_searchButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("== AlertDialog", "on search button click");
                                if (selectedCountry != null) {
                                    fragmentListener.browseCountryInfo(selectedCountry.getName());
                                }
                            }
                        }
                );
    }

    @Override
    public void onBindViewHolder(SelectViewHolder holder, int position)
    {
        holder.country = countries.get(position);
        holder.mTextView.setText(holder.country.getName());

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

    /**
     * Select View Holder inner class
     */
    public class SelectViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public CountryModel country;

        public SelectViewHolder(View v)
        {
            super(v);
            mTextView = v.findViewById(R.id.rv_item_country_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("== Click", "on item click");
                    selectedCountry = country;
                    fragmentListener.onItemClick(selectedCountry);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.i("== Click", "on long item click");
                    selectedCountry = country;
                    alertDialogBuilder.setTitle(mTextView.getText());
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
