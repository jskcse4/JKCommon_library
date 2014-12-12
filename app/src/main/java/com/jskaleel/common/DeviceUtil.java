package com.jskaleel.common;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceUtil 
{
	public static boolean isKindleFire() {
        return android.os.Build.MANUFACTURER.equals("Amazon")
                && (android.os.Build.MODEL.equals("Kindle Fire")
                    || android.os.Build.MODEL.startsWith("KF"));
    }
	public static String getUUID(Context context)
	{
		TelephonyManager tManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String uuid = tManager.getDeviceId();
		if(uuid==null||uuid.equals(""))
			uuid= "123456789";
		
		return uuid;
	}
}
