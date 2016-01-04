package com.baidu.zhuanche.ui.user;

import java.lang.reflect.Field;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.amap.api.mapcore.l;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.bean.Location;
import com.baidu.zhuanche.utils.AMapUtil;
import com.baidu.zhuanche.utils.ToastUtils;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.user
 * @类名: BoardingLocationUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-30 上午9:35:13
 * @描述: 上车地点
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class GetOffUI extends BaseActivity implements OnGeocodeSearchListener
{
	private MapView							mMapView;
	private AMap							mAMap;
	private ImageView						mIvLeftArrow;
	private SearchView						mSearchView;
	private GeocodeSearch					mGeocoderSearch;
	private ProgressDialog					mPogressDialog	= null;
	private MarkerOptions					mMarkerOptions;
	private static OnGetOffLocationListener	mLocationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_getoff);
		mMapView = (MapView) findViewById(R.id.getoff_mapview);
		mIvLeftArrow = (ImageView) findViewById(R.id.getoff_iv_leftarrow);
		mSearchView = (SearchView) findViewById(R.id.getoff_searchview);
		mMapView.onCreate(savedInstanceState);
		initActivity();
		initEvent();
	}

	private void initEvent()
	{
		mGeocoderSearch.setOnGeocodeSearchListener(this);
		/** 地图长按事件 */
		mAMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng latLng)
			{
				showDialog();
				RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
				mGeocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
			}
		});
		/** 搜索框地点查询 */
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query)
			{
				showDialog();
				GeocodeQuery q = new GeocodeQuery(query, "");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
				mGeocoderSearch.getFromLocationNameAsyn(q);// 设置同步地理编码请求
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				return false;
			}
		});
		/** Marker点击事件 */
		mAMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker)
			{
				return false;
			}
		});
		/** 菜单左侧图标点击事件 */
		mIvLeftArrow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				finishActivity();
			}
		});
	}

	private void initActivity()
	{
		if (mAMap == null)
		{
			mAMap = mMapView.getMap();
		}
		mGeocoderSearch = new GeocodeSearch(this);
		mPogressDialog = new ProgressDialog(this);
		mSearchView.setSubmitButtonEnabled(true);
		mMarkerOptions = new MarkerOptions();
		mMarkerOptions.title("你的位置");
		//mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pass));
		/** 通过反射修改searchview 改变其样式 */
		try
		{
			Field field = mSearchView.getClass().getDeclaredField("mSubmitButton");
			field.setAccessible(true);
			ImageView iv = (ImageView) field.get(mSearchView);
			iv.setImageDrawable(this.getResources().getDrawable(R.drawable.piature_122));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode)
	{
		dismissDialog();
		if (rCode == 0)
		{
			if (result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0)
			{
				GeocodeAddress address = result.getGeocodeAddressList().get(0);
				mMarkerOptions.snippet(address.getFormatAddress());
				mMarkerOptions.position(AMapUtil.convertToLatLng(address.getLatLonPoint()));
				mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
				mAMap.clear();
				mAMap.addMarker(mMarkerOptions);
				if (mLocationListener != null)
				{
					Location location = new Location();
					location.address = address.getFormatAddress();
					location.province = address.getProvince();
					location.city = address.getCity();
					location.district = address.getDistrict();
					location.latLng = AMapUtil.convertToLatLng(address.getLatLonPoint());
					mLocationListener.onGetOffLocation(location);
				}
			}
			else
			{
				ToastUtils.makeShortText(this, "抱歉，未搜索到任何结果！");
			}
		}
		else
		{
			ToastUtils.makeShortText(this, "抱歉，查询失败！");
		}
	}

	/**
	 * 逆地理编码接口回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode)
	{
		dismissDialog();
		if (rCode == 0)
		{
			if (result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null)
			{
				RegeocodeQuery query = result.getRegeocodeQuery();
				mMarkerOptions.position(new LatLng(query.getPoint().getLatitude(), query.getPoint().getLongitude()));
				RegeocodeAddress address = result.getRegeocodeAddress();
				mMarkerOptions.snippet(address.getFormatAddress());
				mAMap.clear();
				mAMap.addMarker(mMarkerOptions);
				if (mLocationListener != null)
				{
					Location location = new Location();
					location.address = address.getFormatAddress();
					location.province = address.getProvince();
					location.city = address.getCity();
					location.district = address.getDistrict();
					location.latLng = new LatLng(query.getPoint().getLatitude(), query.getPoint().getLongitude());
					mLocationListener.onGetOffLocation(location);
				}
			}
			else
			{
				ToastUtils.makeShortText(this, "抱歉，未搜索到任何结果！");
			}
		}
		else
		{
			ToastUtils.makeShortText(this, "抱歉，查询失败！");
		}
	}

	public static void setOnGetOffLocationListener(OnGetOffLocationListener listener)
	{
		mLocationListener = listener;
	}

	/** 下车地点地址接口回调 */
	public interface OnGetOffLocationListener
	{
		void onGetOffLocation(Location location);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		mMapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		mMapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mMapView.onDestroy();
	}

	/**
	 * 
	 * 显示进度条对话框
	 */
	public void showDialog()
	{
		mPogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mPogressDialog.setIndeterminate(false);
		mPogressDialog.setCancelable(true);
		mPogressDialog.setMessage("正在获取地址");
		mPogressDialog.show();
	}

	/**
	 * 隐藏进度条对话框
	 */
	public void dismissDialog()
	{
		if (mPogressDialog != null)
		{
			mPogressDialog.dismiss();
		}
	}

	/** 这个方法在这个类废弃 */
	@Deprecated
	@Override
	public void initView()
	{
	}
	@Override
	public void onBackPressed()
	{
		finishActivity();
	}
}
