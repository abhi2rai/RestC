package com.abc.restc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends UIConfig {

	private Spinner searchBy;
	private ProgressDialog progress;
	String URL = "https://restcountries-v1.p.mashape.com/";
	Map<String,String> dict = new HashMap<String,String>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateMap();
        if(android.os.Build.VERSION.SDK_INT == 19){
        	setStatusBarColor("#512da8");
        }
        setIfBlank();
        Spinner options = (Spinner) findViewById(R.id.option_spinner);
        ArrayList<String> subSpecialities = new ArrayList<String>();
        subSpecialities.add("Name");
        subSpecialities.add("Capital");
        subSpecialities.add("Calling Code");
        subSpecialities.add("Currency");
        subSpecialities.add("Region");
        subSpecialities.add("Subregion");
        CustomAdapter adapter = new CustomAdapter(this, R.layout.spinner_layout, subSpecialities);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        options.setAdapter(adapter);
        Typeface fontLight = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
        Typeface fontReg = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        
        setActionBarFont(fontReg,25);
        
        EditText input = (EditText) findViewById(R.id.inputtext);
        input.setTypeface(fontLight);
        
        Button search = (Button) findViewById(R.id.search_button);
        search.setTypeface(fontReg);
        
    }
	
	@SuppressWarnings("rawtypes")
	private class CustomAdapter extends ArrayAdapter implements SpinnerAdapter{

	    Context context;
	    int textViewResourceId;
	    ArrayList arrayList;
	    Typeface fontReg = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
	    @SuppressWarnings("unchecked")
		public CustomAdapter(Context context, int textViewResourceId,  ArrayList arrayList) {
	        super(context, textViewResourceId, arrayList);

	        this.context = context;
	        this.textViewResourceId = textViewResourceId;
	        this.arrayList = arrayList;

	    }

	    @Override
	     public View getDropDownView(int position, View convertView, ViewGroup parent){
	       if (convertView == null)
	       {
	         LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	         //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
	         convertView = vi.inflate(R.layout.spinner_layout, null);
	       }

	       TextView textView = (TextView) convertView.findViewById(R.id.spinnerTarget);
	       textView.setTypeface(fontReg);
	       for(int i = 0 ; i < arrayList.size();i++){
	    	   if(position == i )
	    		   textView.setText(arrayList.get(i).toString());
	       }

	       return convertView;
	     }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent){
	    	if (convertView == null)
		       {
		         LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		         //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
		         convertView = vi.inflate(R.layout.spinner_layout, null);
		       }

		       TextView textView = (TextView) convertView.findViewById(R.id.spinnerTarget);
		       textView.setTypeface(fontReg);
		       for(int i = 0 ; i < arrayList.size();i++){
		    	   if(position == i )
		    		   textView.setText(arrayList.get(i).toString());
		       }
		      

		       return convertView;
	    }



	}

	
	private void populateMap(){
		dict.put("Name", "name/");
		dict.put("Capital", "capital/");
		dict.put("Calling Code", "callingcode/");
		dict.put("Currency", "currency/");
		dict.put("Region", "region/");
		dict.put("Subregion", "subregion/");
	}
	
    private void setIfBlank(){
    	Typeface fontLight = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
    	TextView txt = new TextView(getApplicationContext());
        String str = "<p>Choose from the options mentioned below to search for a country or group of countries. And then tap on a " +
        		"country to get more info:</p>" +
        		"&#8226; Name : Eg. - united, island<br/>" +
        		"&#8226; Capital : Eg. - berlin,paris<br/>" +
        		"&#8226; Calling Code : Eg. - 1, 91<br/>" +
        		"&#8226; Currency : Eg. - eur, usd<br/>" +
        		"&#8226; Region : Eg. - asia, africa<br/>" +
        		"&#8226; Subregion : Eg. - polynesia, western europe<br/>";
        txt.setText(Html.fromHtml(str));
        txt.setGravity(Gravity.CENTER);
        txt.setTextSize(18);
        txt.setTypeface(fontLight);
        txt.setTextColor(Color.DKGRAY);
        LinearLayout layout = (LinearLayout)findViewById(R.id.result_list);
        layout.addView(txt);
    }
    public void searchCountry(View view){
    	LinearLayout layout = (LinearLayout)findViewById(R.id.result_list);
    	layout.removeAllViews();
    	searchBy = (Spinner)findViewById(R.id.option_spinner);
    	if(isConnected()){  
    		EditText myEditText = (EditText) findViewById(R.id.inputtext);
        	hideKeyboard(myEditText);
        	progress = new ProgressDialog(this);
        	progress.setMessage("Searching....");
        	progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        	progress.setIndeterminate(true);
        	progress.show();
        	progress.setCancelable(false);
        	
        	String inputString = myEditText.getText().toString();
        	
        	if(myEditText.getText().toString().equals("")){
        		progress.hide();
        		hideKeyboard((EditText) findViewById(R.id.inputtext));
        		setIfBlank();
    			showToast("Please enter some text");
        	}
        	else{
        		new RetrieveFeedTask().execute(inputString);
        	}
        }
		else{
			hideKeyboard((EditText) findViewById(R.id.inputtext));
			setIfBlank();
			showToast("You need to be connected");
		}
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
    
    public class MyOnClickListener implements OnClickListener
    {

    	String country;
      public MyOnClickListener(String country) {
           this.country = country;
      }

      @Override
      public void onClick(View v)
      {
    	  Intent i = new Intent(getApplicationContext(), Details.class);
	    	i.putExtra("country",country);
	    	startActivity(i);
    	  //read your lovely variable
      }

   };
    
    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        String result="";
    	InputStream inputStream = null;
		
		private void setupDB(JSONObject jsonObject){
			try {
				String name = String.valueOf(ifNull(jsonObject.getString("name")));
				String capital = String.valueOf(ifNull(jsonObject.getString("capital")));
				String region = String.valueOf(ifNull(jsonObject.getString("region")));
				String subregion = String.valueOf(ifNull(jsonObject.getString("subregion")));
				double population = Double.parseDouble(ifNull(jsonObject.getString("population")));
				JSONArray latlng = jsonObject.getJSONArray("latlng");
				double lat = latlng.getDouble(0);
				double lng = latlng.getDouble(1);
				double area = Double.parseDouble(ifNull(jsonObject.getString("area")));
				String timezones="";
				if(!jsonObject.isNull("timezones")){
					JSONArray timez = jsonObject.getJSONArray("timezones");
					for(int i = 0 ; i < timez.length();i++){
						if(i == timez.length()-1){
							timezones += timez.getString(i);
						}
						else{
							timezones += timez.getString(i) + ",";
						}
					}
				}
				else{
					timezones = "N/A";
				}
				
				String borders="";
				if(!jsonObject.isNull("borders")){
					JSONArray bord = jsonObject.getJSONArray("borders");
					for(int i = 0 ; i < bord.length();i++){
						if(i == bord.length()-1){
							borders += new Locale("", bord.getString(i)).getDisplayCountry();
						}
						else{
							borders += new Locale("", bord.getString(i)).getDisplayCountry() + ",";
						}
					}
				}
				else{
					borders = "N/A";
				}
				
				String callingCodes="";
				if(!jsonObject.isNull("callingCodes")){
					JSONArray callingC= jsonObject.getJSONArray("callingCodes");
					for(int i = 0 ; i < callingC.length();i++){
						if(i == callingC.length()-1){
							callingCodes += callingC.getString(i);
						}
						else{
							callingCodes += callingC.getString(i) + ",";
						}
					}
				}
				String currencies="";
				if(!jsonObject.isNull("currencies")){
					JSONArray currency= jsonObject.getJSONArray("currencies");
					for(int i = 0 ; i < currency.length();i++){
						if(i == currency.length()-1){
							currencies += currency.getString(i);
						}
						else{
							currencies += currency.getString(i) + ",";
						}
					}
				}
				String languages="";
				if(!jsonObject.isNull("currencies")){
					JSONArray lang= jsonObject.getJSONArray("languages");
					for(int i = 0 ; i < lang.length();i++){
						if(i == lang.length()-1){
							languages += new Locale(lang.getString(i)).getDisplayName();
						}
						else{
							languages += new Locale(lang.getString(i)).getDisplayName() + ",";
						}
					}
				}
				else{
					languages = "N/A";
				}
				
				String nativeName = String.valueOf(ifNull(jsonObject.getString("nativeName")));
				String alpha2Code = String.valueOf(ifNull(jsonObject.getString("alpha2Code")));
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				db.addCountry(new Country(name,capital,region,subregion,population,lat,
			lng,area,timezones,borders,nativeName,callingCodes,
			currencies,languages,alpha2Code));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private String ifNull(String d){
			if(d == "null"){
				return "0";
			}
			else{
				return d;
			}
		}
		
        protected String doInBackground(String... q) {
        	StringBuilder builder = new StringBuilder();
        	try {
            	HttpClient httpclient = new DefaultHttpClient();
            	String getSelection = String.valueOf(searchBy.getSelectedItem());
            	String encodedString = URLEncoder.encode((q[0]).replace(" ", "%20"),"UTF-8");
    			HttpGet request = new HttpGet(URL + dict.get(getSelection)+encodedString);
    			request.addHeader("X-Mashape-Key","vaIqEmArX9mshVokSbs1RySugavkp16yAv6jsnbMp67ktpm97r");
    			// make GET request to the given URL
    			
    			HttpResponse response = httpclient.execute(request);

    			StatusLine statusLine = response.getStatusLine();
    		      int statusCode = statusLine.getStatusCode();
    		      if (statusCode == 200) {
    		        HttpEntity entity = response.getEntity();
    		        InputStream content = entity.getContent();
    		        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    		        String line;
    		        while ((line = reader.readLine()) != null) {
    		          builder.append(line);
    		        }
    		      } else if(statusCode == 404) {
    		    	  return "Not Found";
    		      }
    		    } catch (ClientProtocolException e) {
    		      e.printStackTrace();
    		    } catch (IOException e) {
    		      e.printStackTrace();
    		    }
    		    return builder.toString();
        }

        protected void onPostExecute(String feed) {
        	try {
        		if(feed.equals("Not Found")){
        			progress.hide();
        			setIfBlank();
        			showToast("Not Found");
        		}
        		else{
        			progress.hide();
        			DatabaseHandler db = new DatabaseHandler(getApplicationContext());
    				db.deleteRecords();
    				JSONArray jsonArray = new JSONArray(feed);
    				Set<String> s = new HashSet<String>();
    				for (int i = 0; i < jsonArray.length(); i++) {
    			        JSONObject jsonObject = jsonArray.getJSONObject(i);
    			        if(s.add(String.valueOf(jsonObject.getString("name")))){
    			        	setupDB(jsonObject);
    			        	TableRow tr1 = new TableRow(getApplicationContext());
        			        TableLayout.LayoutParams tableRowParams=
        		        			  new TableLayout.LayoutParams
        		        			  (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        			        tableRowParams.setMargins(0, 6, 0, 0);
        		        	tr1.setLayoutParams(tableRowParams);
        		        	tr1.setBackgroundResource(R.drawable.selector_shapes);
        		        	tr1.setOnClickListener(new MyOnClickListener(String.valueOf(jsonObject.getString("name"))));
        		        	TextView beno = new TextView(getApplicationContext());
        		            beno.setText(String.valueOf(jsonObject.getString("name")));
        		            beno.setGravity(Gravity.CENTER);
        		            beno.setTextColor(Color.parseColor("#000000"));
        		            beno.setTextSize(28);
        		            Typeface fontReg = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        		            beno.setTypeface(fontReg);
        		            TableRow.LayoutParams col2Params = new TableRow.LayoutParams();
        		            col2Params.height = LayoutParams.WRAP_CONTENT;
        		            col2Params.width = LayoutParams.MATCH_PARENT;
        		            tr1.setClickable(true);
        		            tr1.addView(beno, col2Params);
        		            LinearLayout layout = (LinearLayout)findViewById(R.id.result_list);
        		            layout.addView(tr1);
    			        }
    			      }
        		}
        		
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // TODO: check this.exception 
            // TODO: do something with the feed
        }
    }
}
