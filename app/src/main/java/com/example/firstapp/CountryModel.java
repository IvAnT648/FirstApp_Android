package com.example.firstapp;

public class CountryModel
{
    private String name;
    private int imageId;
    private String currency;

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
}
