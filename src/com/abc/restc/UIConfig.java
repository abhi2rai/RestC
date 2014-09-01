package com.abc.restc;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UIConfig extends Activity {
	
	public UIConfig(){
			
		}
	
	public void setActionBarFont(Typeface type, int size){
    	int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if(actionBarTitleView != null){
            actionBarTitleView.setTypeface(type);
            actionBarTitleView.setTextSize(size);
        }
    }
	
	public void setStatusBarColor(String color){
    	SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.parseColor(color));
    }
	
	public void showToast(String input){
    	Context context = getApplicationContext();
		CharSequence text = input;
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
    }
	
	public boolean isConnected(){
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	    if (networkInfo != null && networkInfo.isConnected()) 
    	    	return true;
    	    else
    	    	return false;	
    }
	
	public void hideKeyboard(EditText myEditText){
    	InputMethodManager imm = (InputMethodManager)getSystemService(
      	      Context.INPUT_METHOD_SERVICE);
      	imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }
}
