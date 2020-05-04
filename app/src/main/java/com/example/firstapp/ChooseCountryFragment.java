package com.example.firstapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChooseCountryFragment extends Fragment
{
    private ArrayList<CountryModel> countries = new ArrayList<CountryModel>();
    private String logTag = "=== JSON parsing";

    class AsyncTask_LoadJson extends AsyncTask<Void, Void, String>
    {
        private String urlString =
                "https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/GuideNew";
        private String logTag = "=== Async JSON loading";
        private int readerCache = 8192;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.i(logTag, "Started loading from [" + this.urlString + "]");
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String response = null;
            try {
                response = this.loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response)
        {
            super.onPostExecute(response);
            Log.i(logTag, "Finished. Response was:\n" + response);
            Log.i(this.logTag, "===========================================");
        }

        /**
         * Return target URL object
         *
         * @return URL
         */
        private URL getTargetUrl()
        {
            URL targetUrl = null;
            try {
                targetUrl = new URL(this.urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return targetUrl;
        }

        /**
         * Load data from url
         *
         * @return String
         */
        private String loadData()
        {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) this.getTargetUrl().openConnection();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                StringBuilder stringResponse = new StringBuilder();
                InputStreamReader ISReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(ISReader, this.readerCache);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringResponse.append(line);
                }
                bufferedReader.close();

                return stringResponse.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    /**
     * Class constructor
     */
    public ChooseCountryFragment() { }

    /**
     * On create view
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_choose_country, container, false);

        this.initRecycleView(view);

        String response = null;
        try {
            AsyncTask_LoadJson at = new AsyncTask_LoadJson();
            response = at.execute().get();
            Gson gson = new Gson();
            List<CountryModel> countriesFromJson = gson.fromJson(
                    response,
                    new TypeToken<List<CountryModel>>() {}.getType()
            );

            assert countriesFromJson != null;
            for (CountryModel country : countriesFromJson) {
                Log.i(this.logTag, "===========================================");
                Log.i(this.logTag, "Country ID: " + country.getCountryId());
                Log.i(this.logTag, "Name: " + country.getName());
                Log.i(this.logTag, "Capital: " + country.getCapital());
                Log.i(this.logTag, "Square: " + country.getSquare());
            }
            Log.i(this.logTag, "===========================================");
            Log.i(this.logTag, "===========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * Init recycle view
     *
     * @param view View
     */
    private void initRecycleView(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.rwCountriesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new ChooseCountryRVAdapter(this.extractCountries());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Extract countries from string-array resource
     *
     * @return ArrayList<CountryModel>
     */
    private ArrayList<CountryModel> extractCountries()
    {
        for (String name : getResources().getStringArray(R.array.countries_list)) {
            countries.add(new CountryModel(name));
        }
        return countries;
    }
}
