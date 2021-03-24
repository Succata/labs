package com.labs.ex.activities;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.labs.ex.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
	@Override
	public void onMapReady(GoogleMap googleMap) {

		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		String[] coordinates = getIntent().getStringExtra("coordinates").split(",");
		LatLng point = new LatLng(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
		List<Address> addresses;
		MarkerOptions marker = new MarkerOptions();
		try {
			addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
			String title = "увидимся тут!";
			String country = addresses.get(0).getCountryName() == null ? "":addresses.get(0).getCountryName();
			String locality =  addresses.get(0).getLocality() == null ? "":(" " + addresses.get(0).getLocality());
			if (addresses.size() != 0)
				title+= " " + country + locality;
			marker.title(title);
		} catch (IOException e) { e.printStackTrace(); }


		marker.position(point);
		googleMap.addMarker(marker);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 4));
	}
}