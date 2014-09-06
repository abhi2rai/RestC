package com.abc.restc;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "countryManager";
 
    // Contacts table name
    private static final String TABLE_COUNTRY = "country";
    
    private static final String name = "name";
    private static final String capital = "capital";
    private static final String region = "region";
    private static final String subregion = "subregion";
    private static final String population = "population";
    private static final String lat = "lat";
    private static final String lng = "lng";
    private static final String area = "area";
    private static final String alpha2Code = "alpha2Code";
    private static final String timezones = "timezones";
    private static final String borders = "borders";
    private static final String nativeName = "nativeName";
    private static final String callingCodes = "callingCodes";
    private static final String currencies = "currencies";
    private static final String languages = "languages";
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE = "CREATE TABLE " + TABLE_COUNTRY + "("
                + name + " TEXT PRIMARY KEY,"
        		+ capital + " TEXT,"
                + region + " TEXT,"
                + subregion + " TEXT,"
                + population + " REAL,"
                + lat + " REAL,"
                + lng + " REAL,"
                + area + " REAL,"
                + timezones + " TEXT,"
                + borders + " TEXT,"
                + nativeName + " TEXT,"
                + callingCodes + " TEXT,"
                + currencies + " TEXT,"
                + languages + " TEXT,"
                + alpha2Code + " REAL"+")";
        db.execSQL(CREATE_COUNTRY_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addCountry(Country country){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(name,country.getName());
    	values.put(capital,country.getCapital());
    	values.put(region, country.getRegion());
    	values.put(subregion, country.getSubRegion());
    	values.put(population, country.getPopulation());
    	values.put(lat, country.getLat());
    	values.put(lng, country.getLng());
    	values.put(area,country.getArea());
    	values.put(timezones, country.getTimezones());
    	values.put(borders, country.getBorders());
    	values.put(nativeName, country.getnativeName());
    	values.put(callingCodes, country.callingCodes);
    	values.put(currencies, country.getCurrencies());
    	values.put(languages, country.getLanguages());
    	values.put(alpha2Code, country.getalpha2Code());
    	db.insert(TABLE_COUNTRY, null, values);
    	db.close();
    }
    
    public void deleteRecords(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from "+ TABLE_COUNTRY);
    	db.close();
    }
    
    public List<Country> getCountry(String name){
    	List<Country> countryList = new ArrayList<Country>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COUNTRY + " WHERE name = '"+name+"'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Country country = new Country();
            	country.setName(cursor.getString(0));
            	country.setCapital(cursor.getString(1));
            	country.setRegion(cursor.getString(2));
            	country.setSubRegion(cursor.getString(3));
            	country.setPopulation(Double.parseDouble(cursor.getString(4)));
            	country.setLat(Double.parseDouble(cursor.getString(5)));
            	country.setLng(Double.parseDouble(cursor.getString(6)));
            	country.setArea(Double.parseDouble(cursor.getString(7)));
            	country.setTimezones(cursor.getString(8));
            	country.setBorders(cursor.getString(9));
            	country.setnativeName(cursor.getString(10));
            	country.setcallingCodes(cursor.getString(11));
            	country.setCurrencies(cursor.getString(12));
            	country.setLanguages(cursor.getString(13));
            	country.setalpha2Code(cursor.getString(14));
                // Adding contact to list
                countryList.add(country);
            } while (cursor.moveToNext());
        }
        // return contact list
        return countryList;
    }
}
