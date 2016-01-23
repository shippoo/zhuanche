package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.baidu.zhuanche.R;
import com.baidu.zhuanche.SplashUI;
import com.baidu.zhuanche.adapter.HomeGridAdapter;
import com.baidu.zhuanche.adapter.HotAskAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.Driver;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.bean.UserIndexBean;
import com.baidu.zhuanche.bean.UserIndexBean.Article;
import com.baidu.zhuanche.bean.UserIndexBean.Banner;
import com.baidu.zhuanche.bean.UserIndexBean.Cate;
import com.baidu.zhuanche.conf.MyConstains;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.holder.UserHomePicHolder;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.ui.driver.DriverHomeUI;
import com.baidu.zhuanche.view.NoScrolledGridView;
import com.google.gson.Gson;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui
 * @类名: UserHomeUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-26 上午11:37:53
 * @描述: 用户首页
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class UserHomeUI extends BaseActivity implements OnClickListener{
	private ListView				mListView;
	private LinearLayout			mContainerLogin;							// 点击过境小车登陆
	private User					mUser;
	private Driver					mDriver;
	private FrameLayout				mContainerPic;								// 首页轮播图
	private List<Article>			mListDatas		= new ArrayList<Article>();
	private HotAskAdapter			mAskAdapter;
	private NoScrolledGridView		mGridView;
	private List<Cate>				mGridViewDatas;
	private List<Banner>			mBannerDatas;								// 轮播图数据
	private int						currentPage		= 1;


	@Override
	public void initView()
	{
		setContentView(R.layout.ui_userhome);
		mListView = (ListView) findViewById(R.id.home_listview);
		View headerView = View.inflate(this, R.layout.layout_uh_header, null);
		mContainerPic = (FrameLayout) headerView.findViewById(R.id.uh_container_pic);
		mContainerLogin = (LinearLayout) headerView.findViewById(R.id.home_container_guojingxiaoche);
		mGridView = (NoScrolledGridView) headerView.findViewById(R.id.uh_gridview);
		// mListView.setMode(Mode.PULL_FROM_END);
		// AbsListView.LayoutParams layoutParams = new
		// AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
		// AbsListView.LayoutParams.WRAP_CONTENT);
		// headerView.setLayoutParams(layoutParams);
		// ListView lv = mListView.getRefreshableView();
		// lv.addHeaderView(headerView);
		mListView.addHeaderView(headerView);
		mListView.setAdapter(null);
	}

	
	@Override
	public void initData()
	{
		super.initData();
		mIvLeftHeader.setVisibility(8);
		mUser = BaseApplication.getUser();
		mDriver = BaseApplication.getDriver();
		mTvTitle.setText("首页");
		mAskAdapter = new HotAskAdapter(this, mListDatas);
		mListView.setAdapter(mAskAdapter);
		/** 数据 */
		String url = URLS.BASESERVER + URLS.User.index;
		mClient.post(url, new MyAsyncResponseHandler() {

			@Override
			public void success(String json)
			{
				processJson(json);
			}
		});
	}

	protected void processJson(String json)
	{
		Gson gson = new Gson();
		UserIndexBean userIndexBean = gson.fromJson(json, UserIndexBean.class);

		UserHomePicHolder picHolder = new UserHomePicHolder();
		mContainerPic.addView(picHolder.mHolderView);
		mBannerDatas = userIndexBean.content.banner;
		picHolder.setDataAndRefreshHolderView(mBannerDatas);
		/** 中间板块数据 */
		mGridViewDatas = userIndexBean.content.cate;
		HomeGridAdapter homeGridAdapter = new HomeGridAdapter(this, mGridViewDatas);
		mGridView.setAdapter(homeGridAdapter);
		/** 新闻资讯数据 */
		if (userIndexBean.content.article != null && userIndexBean.content.article.size() > 0)
		{
			mListDatas.addAll(userIndexBean.content.article);
			mAskAdapter.notifyDataSetChanged();
		}
		// mListView.onRefreshComplete();
	}

	protected void loadMore(String json)
	{
		Gson gson = new Gson();
		UserIndexBean userIndexBean = gson.fromJson(json, UserIndexBean.class);
		/** 新闻资讯数据 */
		if (userIndexBean.content.article != null && userIndexBean.content.article.size() > 0)
		{
			mListDatas.addAll(userIndexBean.content.article);
			mAskAdapter.notifyDataSetChanged();
			currentPage++;
		}
		// mListView.onRefreshComplete();
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mContainerLogin.setOnClickListener(this);
		// mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		//
		// @Override
		// public void onRefresh(PullToRefreshBase<ListView> refreshView)
		// {
		// setPullRefreshListUserLoadMoreData(refreshView);
		// mListView.postDelayed(new LoadMore(), 1000);
		// }
		// });
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Bundle bundle = new Bundle();
				Cate cate = mGridViewDatas.get(position);
				bundle.putSerializable("cate", cate);
				startActivity(NewsListUI.class, bundle);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Bundle bundle = new Bundle();
				Article article = mListDatas.get(position - 1);
				bundle.putString("id", article.id);
				startActivity(NewsDetailUI.class, bundle);
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		if (v == mContainerLogin)
		{
			if ((mUser == null || TextUtils.isEmpty(mUser.mobile)) && (mDriver == null || TextUtils.isEmpty(mDriver.mobile)))
			{
				startActivity(SplashUI.class);
			}
			else if (mDriver != null && !TextUtils.isEmpty(mDriver.mobile))
			{
				startActivity(DriverHomeUI.class);
			}
			else
			{
				startActivity(YuyueUI.class);
			}
		}
		else if (v == mIvLeftHeader)
		{
			finishActivity(SplashUI.class);
		}
	}

	private class LoadMore implements Runnable
	{

		@Override
		public void run()
		{
			/** 数据 */
			String url = URLS.BASESERVER + URLS.User.index;
			mClient.post(url, new MyAsyncResponseHandler() {

				@Override
				public void success(String json)
				{
					loadMore(json);
				}
			});
		}

	}

	

}
