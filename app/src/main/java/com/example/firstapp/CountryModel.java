package com.example.firstapp;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryModel
{
    private int countryId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("square")
    @Expose
    private int square;

    @SerializedName("Capital")
    @Expose
    private String capital;

    private int imageId;

    private String currency;

    public CountryModel() { }

    public CountryModel(ContentValues data)
    {
        this.countryId = data.getAsInteger("id");
        this.name = data.getAsString("name");
        this.square = data.getAsInteger("square");
        this.capital = data.getAsString("capital");
    }

    /**
     * Constructor
     * @param name     String
     * @param currency String
     * @param imageId  int
     */
    public CountryModel(String name, String currency, int imageId)
    {
        this.name = name;
        this.imageId = imageId;
        this.currency = currency;
    }

    /**
     * Constructor
     *
     * @param name String
     */
    public CountryModel(String name)
    {
        this.name = name;
        this.imageId = 0;
    }

    /**
     * Get country name
     *
     * @return String
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set country name
     *
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get image ID
     *
     * @return int
     */
    public int getImageId()
    {
        return this.imageId;
    }

    /**
     * Set image ID
     *
     * @param imageId int
     * @return void
     */
    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public int getSquare()
    {
        return square;
    }

    public void setSquare(int square)
    {
        this.square = square;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public int getCountryId()
    {
        return countryId;
    }

    public ContentValues getDataToSave()
    {
        ContentValues cvData = new ContentValues();
        cvData.put("name", this.getName());
        cvData.put("square", this.getSquare());
        cvData.put("capital", this.getCapital());
        return cvData;
    }
}
