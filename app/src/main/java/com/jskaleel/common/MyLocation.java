package com.jskaleel.common;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MyLocation {

	public static final int FLAG_FOUND = 1;
	public static final int FLAG_PROVIDER_NOT_ENABLED=0;

	public static final int GET_NOW = 0;
	public static final int GET_LATER = 1;
	//public static final int GET_LATER_GPS_REQUEST = 2;

	private Timer timer1;
	private LocationManager lm;
	private LocationResult locationResult;
	private boolean gps_enabled=false;
	boolean network_enabled=false;
	private int CURRENT_TYPE = 0;

	public MyLocation(final int TYPE){
		CURRENT_TYPE = TYPE;
	}

	public boolean getLocation(Context context, LocationResult result){

		locationResult=result;
		if(lm==null)
			lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		try{
			gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}catch(Exception ex){

		}

		try{
			network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}catch(Exception ex){
		}

		if(!gps_enabled && !network_enabled){
			locationResult.gotLocation(null,FLAG_PROVIDER_NOT_ENABLED, CURRENT_TYPE);
			return false;
		}

		if(gps_enabled)
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		if(network_enabled)
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);


		if(CURRENT_TYPE == GET_NOW){
			timer1=new Timer();        
			timer1.schedule(new GetLastLocation(), 80);
		}
		
		return true;

	}

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			if(timer1 != null)
			timer1.cancel();
			locationResult.gotLocation(location,FLAG_FOUND, CURRENT_TYPE);
			lm.removeUpdates(this);
			lm.removeUpdates(locationListenerNetwork);
		}
		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {}
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	};

	LocationListener locationListenerNetwork = new LocationListener() {

		public void onLocationChanged(Location location) {
			if(location!=null){
				Log.e("MyLocation", "Current Location:" + location.getLatitude() + " , " + location.getLongitude());
			}
			if(timer1 != null)
			timer1.cancel();
			locationResult.gotLocation(location,FLAG_FOUND, CURRENT_TYPE);
			lm.removeUpdates(this);
			lm.removeUpdates(locationListenerGps);
		}
		public void onProviderDisabled(String provider) {}
		public void onProviderEnabled(String provider) {}
		public void onStatusChanged(String provider, int status, Bundle extras) {}

	};

	class GetLastLocation extends TimerTask {

		@Override
		public void run() {
			lm.removeUpdates(locationListenerGps);
			lm.removeUpdates(locationListenerNetwork);

			Location net_loc=null, gps_loc=null;
			
			if(gps_enabled)
				gps_loc=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(network_enabled)
				net_loc=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if(gps_loc!=null && net_loc!=null){
				if(gps_loc.getTime() > net_loc.getTime())
					locationResult.gotLocation(gps_loc,FLAG_FOUND, CURRENT_TYPE);
				else
					locationResult.gotLocation(net_loc,FLAG_FOUND, CURRENT_TYPE);
				return;
			}

			if(gps_loc!=null){
				locationResult.gotLocation(gps_loc,FLAG_FOUND, CURRENT_TYPE);
				return;
			}
			if(net_loc!=null){
				locationResult.gotLocation(net_loc,FLAG_FOUND, CURRENT_TYPE);
				return;
			}

			locationResult.gotLocation(null,FLAG_PROVIDER_NOT_ENABLED, CURRENT_TYPE);		
		}		
	}
	
	public void removeUpdates(){
		lm.removeUpdates(locationListenerGps);
		lm.removeUpdates(locationListenerNetwork);
	}

	public static abstract class LocationResult{
		public abstract void gotLocation(Location location, int flag, int TYPE);
	}

}