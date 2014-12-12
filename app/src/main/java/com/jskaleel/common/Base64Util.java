package com.jskaleel.common;

import java.io.UnsupportedEncodingException;

import android.util.Base64;

public class Base64Util {

	public static String decodeBase64(String encodedString)
	{
	
		byte[] byteData = Base64.decode(encodedString, Base64.NO_WRAP);
		String decodedString = null;
		try {
			decodedString= new String(byteData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodedString;
	}

	public static String encodedBase64(String currentString)
	{
		
		
		byte[] commentData = null;

		try 
		{
			commentData = currentString.trim().getBytes("UTF-8");			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String b =Base64.encodeToString(commentData, Base64.NO_WRAP);
		return b;

	}
	
}