package com.abc.restc;

import java.text.DecimalFormat;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Details extends UIConfig {
	
	// Google Map
    private GoogleMap googleMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		if(android.os.Build.VERSION.SDK_INT == 19){
        	setStatusBarColor("#512da8");
        }
		Intent intent = getIntent();
		getActionBar().setTitle(intent.getStringExtra("country"));
		try {
            // Loading map
            initialize(intent);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		Typeface fontReg = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        setActionBarFont(fontReg,25);
        setPlaceholderFont();
	}
	
	private void setPlaceholderFont(){
		Typeface fontReg = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
		TextView capital = (TextView)findViewById(R.id.capital_placeholder);
        capital.setTypeface(fontReg);
        
        TextView region = (TextView)findViewById(R.id.region_placeholder);
        region.setTypeface(fontReg);
        
        TextView subregion = (TextView)findViewById(R.id.subregion_placeholder);
        subregion.setTypeface(fontReg);
        
        TextView population = (TextView)findViewById(R.id.population_placeholder);
        population.setTypeface(fontReg);
        
        TextView area = (TextView)findViewById(R.id.area_placeholder);
        area.setTypeface(fontReg);
        
        TextView timezone = (TextView)findViewById(R.id.timezones_placeholder);
        timezone.setTypeface(fontReg);
        
        TextView borders = (TextView)findViewById(R.id.borders_placeholder);
        borders.setTypeface(fontReg);
        
        TextView nativeNames = (TextView)findViewById(R.id.nativename_placeholder);
        nativeNames.setTypeface(fontReg);
        
        TextView callingCodes = (TextView)findViewById(R.id.callingcodes_placeholder);
        callingCodes.setTypeface(fontReg);
        
        TextView currencies = (TextView)findViewById(R.id.currencies_placeholder);
        currencies.setTypeface(fontReg);
        
        TextView languages = (TextView)findViewById(R.id.languages_placeholder);
        languages.setTypeface(fontReg);
	}
	
	private void initialize(Intent intent) {
		DatabaseHandler db = new DatabaseHandler(getApplicationContext());
		List<Country> obj = db.getCountry(intent.getStringExtra("country"));
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            double latitude =obj.get(0).lat ;
            double longitude =obj.get(0).lng ;
             
            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(intent.getStringExtra("country"));
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.getUiSettings().setScrollGesturesEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(true);
            // adding marker
            googleMap.addMarker(marker).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(3).build();
     
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // check if map is created successfully or not
            if (googleMap == null) {
                showToast("Sorry! unable to create maps");
            }
        }
        Typeface fontLight = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
        DecimalFormat numFormat;
        
        TextView capital = (TextView)findViewById(R.id.capital);
        capital.setText(obj.get(0).capital);
        capital.setTypeface(fontLight);
        
        TextView region = (TextView)findViewById(R.id.region);
        region.setText(obj.get(0).region);
        region.setTypeface(fontLight);
        
        TextView subregion = (TextView)findViewById(R.id.subregion);
        subregion.setText(obj.get(0).subregion);
        subregion.setTypeface(fontLight);
        
        TextView population = (TextView)findViewById(R.id.population);
        numFormat = new DecimalFormat("#,###,###");
        population.setText(numFormat.format(obj.get(0).population));
        population.setTypeface(fontLight);
        
        TextView area = (TextView)findViewById(R.id.area);
        numFormat = new DecimalFormat("#,###,###");
        area.setText(numFormat.format(obj.get(0).area)+" sq km");
        area.setTypeface(fontLight);
        
        TextView timezone = (TextView)findViewById(R.id.timezones);
        timezone.setText(obj.get(0).timezones);
        timezone.setTypeface(fontLight);
        
        TextView borders = (TextView)findViewById(R.id.borders);
        borders.setText(obj.get(0).borders);
        borders.setTypeface(fontLight);
        
        TextView nativeNames = (TextView)findViewById(R.id.nativename);
        nativeNames.setText(obj.get(0).nativeName);
        nativeNames.setTypeface(fontLight);
        
        TextView callingCodes = (TextView)findViewById(R.id.callingcodes);
        callingCodes.setText(obj.get(0).callingCodes);
        callingCodes.setTypeface(fontLight);
        
        TextView currencies = (TextView)findViewById(R.id.currencies);
        currencies.setText(obj.get(0).currencies);
        currencies.setTypeface(fontLight);
        
        TextView languages = (TextView)findViewById(R.id.languages);
        languages.setText(obj.get(0).languages);
        languages.setTypeface(fontLight);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
