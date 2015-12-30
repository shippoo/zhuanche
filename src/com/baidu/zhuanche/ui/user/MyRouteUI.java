package com.baidu.zhuanche.ui.user;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.ui.user
 * @类名:	MyRouteUI
 * @创建者:	陈选文
 * @创建时间:	2015-12-30	下午2:29:43 
 * @描述:	我的行程
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class MyRouteUI extends BaseActivity
{
	private MapView mMapView;
	private AMap	mAMap;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_myroute);
		mMapView = (MapView) findViewById(R.id.myroute_mapview);
		mAMap = mMapView.getMap();
	}
	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("行程路线");
	}
	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(null);
	}
}
