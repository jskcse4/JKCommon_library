package com.jskaleel.common;

import java.text.DecimalFormat;

import android.content.Context;
import android.util.TypedValue;

public class MathUtils 
{

	public static int convertDpToPx(int dp,Context context)
	{
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  context.getResources().getDisplayMetrics());
		return (int)px;
	}
	public static int convertPxoDp (int pixel,Context context) 
	{
		float density = context.getResources().getDisplayMetrics().density; 
		int minHeight = (int) (pixel*density + 0.5f);
		return minHeight;
	}

	public static String roundOneDecimals(double value) 
	{
		DecimalFormat twoDForm = new DecimalFormat("0.0");
		return twoDForm.format(value);
	}
	public static String roundTwoDecimals(double value) 
	{
		DecimalFormat twoDForm = new DecimalFormat(".00");
		return twoDForm.format(value);
	}
	public static String roundThreeDecimals(double value) 
	{
		DecimalFormat twoDForm = new DecimalFormat("0.000");
		return twoDForm.format(value);
	}
	public static String roundFourDecimals(double value) 
	{
		DecimalFormat twoDForm = new DecimalFormat("0.0000");
		return twoDForm.format(value);
	}

	public static double roundDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.######");
		return Double.valueOf(twoDForm.format(d));
	}
}

