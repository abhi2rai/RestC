package com.abc.restc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Spinner searchBy;
	private ProgressDialog progress;
	String URL = "https://restcountries-v1.p.mashape.com/";
	Map<String,String> dict = new HashMap<String,String>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateMap();
    }
	
	private void populateMap(){
		dict.put("Name", "name/");
		dict.put("Capital", "capital/");
		dict.put("Country Code", "alpha/");
		dict.put("Calling Code", "callingcode/");
		dict.put("Currency", "currency/");
		dict.put("Language", "lang/");
		dict.put("Region", "region/");
		dict.put("Subregion", "subregion/");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void searchCountry(View view){
    	LinearLayout layout = (LinearLayout)findViewById(R.id.result_list);
    	layout.removeAllViews();
    	searchBy = (Spinner)findViewById(R.id.option_spinner);
    	if(isConnected()){  
    		EditText myEditText = (EditText) findViewById(R.id.inputtext);
        	hideKeyboard(myEditText);
        	progress = new ProgressDialog(this);
        	progress.setMessage("Yodafying....");
        	progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        	progress.setIndeterminate(true);
        	progress.show();
        	progress.setCancelable(false);
        	
        	String inputString = myEditText.getText().toString();
        	
        	if(myEditText.getText().toString().equals("")){
        		progress.hide();
        		hideKeyboard((EditText) findViewById(R.id.inputtext));
    			showToast("Please enter some text");
        	}
        	else{
        		new RetrieveFeedTask().execute(inputString);
        	}
        }
		else{
			hideKeyboard((EditText) findViewById(R.id.inputtext));
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
    
    private void showToast(String input){
    	Context context = getApplicationContext();
		CharSequence text = input;
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
    }
    
    private boolean isConnected(){
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	    if (networkInfo != null && networkInfo.isConnected()) 
    	    	return true;
    	    else
    	    	return false;	
    }
    
    private void hideKeyboard(EditText myEditText){
    	InputMethodManager imm = (InputMethodManager)getSystemService(
      	      Context.INPUT_METHOD_SERVICE);
      	imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }
    
    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        String result="";
    	InputStream inputStream = null;
		private Exception exception;

        protected String doInBackground(String... q) {
        	StringBuilder builder = new StringBuilder();
        	try {
            	HttpClient httpclient = new DefaultHttpClient();
    			HttpGet request = new HttpGet(URL + dict.get(String.valueOf(searchBy.getSelectedItem()))+URLEncoder.encode(q[0]));
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
        			showToast("Not Found");
        		}
        		else{
        			progress.hide();
    				JSONArray jsonArray = new JSONArray(feed);
    				for (int i = 0; i < jsonArray.length(); i++) {
    			        JSONObject jsonObject = jsonArray.getJSONObject(i);
    			        TableRow tr1 = new TableRow(getApplicationContext());
    			        TableLayout.LayoutParams tableRowParams=
    		        			  new TableLayout.LayoutParams
    		        			  (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    			        tableRowParams.setMargins(0, 6, 0, 0);
    		        	tr1.setLayoutParams(tableRowParams);
    		        	tr1.setBackgroundResource(R.drawable.selector_shapes);
    		        	TextView beno = new TextView(getApplicationContext());
    		            beno.setText(String.valueOf(jsonObject.getString("name")));
    		            beno.setGravity(Gravity.CENTER);
    		            beno.setTextColor(Color.parseColor("#000000"));
    		            beno.setTextSize(28);
    		            TableRow.LayoutParams col2Params = new TableRow.LayoutParams();
    		            col2Params.height = LayoutParams.WRAP_CONTENT;
    		            col2Params.width = LayoutParams.MATCH_PARENT;
    		            tr1.setClickable(true);
    		            tr1.addView(beno, col2Params);
    		            LinearLayout layout = (LinearLayout)findViewById(R.id.result_list);
    		            layout.addView(tr1);
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
