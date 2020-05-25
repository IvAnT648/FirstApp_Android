package com.example.firstapp;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Collections;

public class ChooseCountryFragment extends Fragment implements CustomItemClickListener
{
    private ArrayList<CountryModel> countries = new ArrayList<CountryModel>();
    private ChooseCountryRVAdapter adapter;
    private DBHelper dbHelper;

    @Override
    public void onItemClick(CountryModel country) { }

    @Override
    public void onLongItemClick(CountryModel country)
    {
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) {
            return;
        }
        activity.replaceFragment(new CountryInfoFragment(country));
    }

    class AsyncLoad_Countries extends AsyncTask<String, Void, String>
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
        protected String doInBackground(String... params)
        {
            String response = null;
            try {
                response = this.loadData(this.urlString);
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
        private URL getTargetUrl(String url)
        {
            URL targetUrl = null;
            try {
                targetUrl = new URL(url);
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
        private String loadData(String url)
        {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) this.getTargetUrl(url).openConnection();
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
        this.dbHelper = new DBHelper(getContext(), "bd", null, 1);

        this.loadCountries(true);
        this.initRecycleView(view);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.srlCountriesList);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadCountries(true);
                adapter.countries = countries;
                Collections.shuffle(adapter.countries);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    /**
     * Init recycle view
     *
     * @param view View
     * @return void
     */
    private void initRecycleView(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.rwCountriesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        this.adapter = new ChooseCountryRVAdapter(getContext(), this.countries, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Load countries
     *
     * @return void
     */
    private void loadCountries(boolean updateIsNeeded)
    {
        if (updateIsNeeded) {
            try {
                AsyncLoad_Countries at = new AsyncLoad_Countries();
                String response = at.execute().get();
                Gson gson = new Gson();
                this.countries = gson.fromJson(
                        response,
                        new TypeToken<ArrayList<CountryModel>>() {}.getType()
                );

                if (this.countries == null) {
                    Log.i("=== Country loading", "Country not loaded");
                    return;
                }

                for (CountryModel country : this.countries) {
                    this.dbHelper.saveCountry(country.getDataToSave());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.countries = this.loadCountriesFromDB();
    }

    private ArrayList<CountryModel> loadCountriesFromDB()
    {
        ArrayList<CountryModel> result = new ArrayList<>();
        for (ContentValues cv : this.dbHelper.selectCountries()) {
            result.add(new CountryModel(cv));
        }
        return result;
    }
}
