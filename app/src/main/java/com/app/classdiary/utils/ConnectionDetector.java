package com.app.classdiary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

	   private Context _context;
	    
	   public ConnectionDetector(Context context){
	       this._context = context;
	   }

	   public boolean isConnectingToInternet(){
	       ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	         if (connectivity != null) 
	         {
	             NetworkInfo[] info = connectivity.getAllNetworkInfo();
	             if (info != null) 
	                 for (int i = 0; i < info.length; i++) 
	                     if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                     {
	                         return true;
	                     }

	         }
	         return false;
	   }
	   
	   public boolean isConnected() {

	       ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	       NetworkInfo netinfo = cm.getActiveNetworkInfo();

	       if (netinfo != null && netinfo.isConnectedOrConnecting()) {
	           android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	           android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	           if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
	           else return false;
	       } else
	           return false;
	   }
	   
	   public AlertDialog.Builder buildDialog() {

		    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		    builder.setTitle("No Internet connection.");
		    builder.setMessage("You have no internet connection\nTrying to connect");

		    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

		        @Override
		        public void onClick(DialogInterface dialog, int which) {

		            dialog.dismiss();
		        }
		    });

		    return builder;
		}
	   

}
