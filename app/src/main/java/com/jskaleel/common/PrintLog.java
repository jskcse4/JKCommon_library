package com.jskaleel.common;

import android.util.Log;

public class PrintLog {

	public static void debug(String TAG,String str) {
		if(str.length() > 4000) 
		{
			Log.d("MGC",TAG+"  ---> " + str.substring(0, 4000));
			debug(TAG,str.substring(4000));
		} else
		{
			Log.d("MGC",TAG+ " ---->" +str);
		}
	}
	public static void error(String TAG,String str) {
		if(str.length() > 4000) 
		{
			Log.e("MGC",TAG +"  ---> " + str.substring(0, 4000));
			error(TAG,str.substring(4000));
		}
		else
		{
			Log.e("MGC",TAG+ " ---->" +str);
		}
	}
	
}
