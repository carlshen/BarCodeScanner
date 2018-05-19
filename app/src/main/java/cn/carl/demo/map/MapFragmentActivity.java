package cn.carl.demo.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.Polyline;

import cn.carl.demo.Constants;
import cn.carl.demo.xcodescanner.R;

public class MapFragmentActivity extends AppCompatActivity {

	private Context mContext;
	private SupportMapFragment supportMapFragment;
	private FragmentManager fragmentManager;
	private String type;
	private String result;
	private double srcLat = 39.984129;
	private double srcLng = 116.307696;
	private double destLat = 39.980129;
	private double destLng = 119.300696;
	private Polyline polyline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_in_fragment);
		mContext = this;
		Intent intent = getIntent();
		if (intent != null) {
			type = intent.getStringExtra(Constants.BAR_TYPE);
			result = intent.getStringExtra(Constants.BAR_RESULT);
		}
		initView();
	}

	private void initView() {
		fragmentManager = getSupportFragmentManager();
		
		supportMapFragment = SupportMapFragment.newInstance(this);
		Marker marker1 = supportMapFragment.getMap()
				.addMarker(new MarkerOptions()
				.position(new LatLng(39.984129,116.307696))
				.title("SupportMapFragment"));
		marker1.showInfoWindow();
		fragmentManager.beginTransaction().add(R.id.ll_frag_root, supportMapFragment).commit();

	}

}
