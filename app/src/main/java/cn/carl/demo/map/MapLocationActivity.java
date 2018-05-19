package cn.carl.demo.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

import cn.carl.demo.Constants;
import cn.carl.demo.utils.NetworkUtils;
import cn.carl.demo.xcodescanner.R;

public class MapLocationActivity extends AppCompatActivity {
	
	private TencentMap tencentMap;
	private DemoLocationSource locationSource;
	private String type;
	private String result;
	private double destLat = 39.984129;
	private double destLng = 116.307696;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_map_location);
		Intent intent = getIntent();
		if (intent != null) {
			type = intent.getStringExtra(Constants.BAR_TYPE);
			result = intent.getStringExtra(Constants.BAR_RESULT);
		}
		init();
		showLocationSetting();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!NetworkUtils.isConnected()) {
			Toast.makeText(this, "地图定位需要连接网络！", Toast.LENGTH_SHORT);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//这里调用了腾讯定位sdk,如果不再需要定位功能，必须关闭定位。
		if (tencentMap.isMyLocationEnabled()) {
			tencentMap.setMyLocationEnabled(false);
		}
	}
	
	protected void init() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.frag_map);
		tencentMap = mapFragment.getMap();
		tencentMap.getUiSettings().setZoomControlsEnabled(false);
		
		locationSource = new DemoLocationSource(this);
		tencentMap.setLocationSource(locationSource);
		tencentMap.setMyLocationEnabled(true);
	}
	
	protected void showLocationSetting() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			builder.setMessage("GPS is enabled, do you want to change it?");
		} else {
			builder.setMessage("GPS is disabled, do you want to change it?");
		}
		builder.setPositiveButton("Yes", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	class DemoLocationSource implements LocationSource, TencentLocationListener {

		private Context mContext;
		private OnLocationChangedListener mChangedListener;
		private TencentLocationManager locationManager;
		private TencentLocationRequest locationRequest;
		
		public DemoLocationSource(Context context) {
			mContext = context;
			locationManager = TencentLocationManager.getInstance(mContext);
			locationRequest = TencentLocationRequest.create();
			locationRequest.setInterval(2000);
		}
		
		@Override
		public void onLocationChanged(TencentLocation arg0, int arg1, String arg2) {
			if (arg1 == TencentLocation.ERROR_OK && mChangedListener != null) {
				Log.e("maplocation", "location: " + arg0.getCity() + " " + arg0.getProvider());
				Location location = new Location(arg0.getProvider());
				location.setLatitude(arg0.getLatitude());
				location.setLongitude(arg0.getLongitude());
				location.setAccuracy(arg0.getAccuracy());
				mChangedListener.onLocationChanged(location);
			}
		}

		@Override
		public void onStatusUpdate(String arg0, int arg1, String arg2) {
			
		}

		@Override
		public void activate(OnLocationChangedListener arg0) {
			mChangedListener = arg0;
			int err = locationManager.requestLocationUpdates(locationRequest, this);
			switch (err) {
			case 1:
				setTitle("设备缺少使用腾讯定位服务需要的基本条件");
				break;
			case 2:
				setTitle("manifest 中配置的 key 不正确");
				break;
			case 3:
				setTitle("自动加载libtencentloc.so失败");
				break;

			default:
				break;
			}
		}

		@Override
		public void deactivate() {
			locationManager.removeUpdates(this);
			mContext = null;
			locationManager = null;
			locationRequest = null;
			mChangedListener = null;
		}
		
		public void onPause() {
			locationManager.removeUpdates(this);
		}
		
		public void onResume() {
			locationManager.requestLocationUpdates(locationRequest, this);
		}
		
	}
}
