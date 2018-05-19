package cn.carl.demo.map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

public class CustMapFragment extends Fragment {
	
	private static final String LOG_TAG ="CustMapFragment";
	private static CustMapFragment mCustMapFragment;
	private MapView mapView;
	private TencentMap tencentMap;

	
	public static synchronized CustMapFragment newInstance(Context context) {
		if (mCustMapFragment == null) {
			mCustMapFragment = new CustMapFragment();
			mCustMapFragment.mapView = new MapView(context);
		}
		return mCustMapFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (mapView == null) {
			mapView = new MapView(getContext());
		}
		return mapView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "onCreate");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mapView.onStart();
		Log.d(LOG_TAG, "onStart");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
		Log.d(LOG_TAG, "onResume");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		Log.d(LOG_TAG, "onPause");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mapView.onStop();
		Log.d(LOG_TAG, "onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		mapView = null;
		tencentMap = null;
		mCustMapFragment = null;
		Log.d(LOG_TAG, "onDestroy");
	}
	
	public TencentMap getMap() {
		if (tencentMap == null) {
			tencentMap = mapView.getMap();
		}
		return tencentMap;
	}

}
