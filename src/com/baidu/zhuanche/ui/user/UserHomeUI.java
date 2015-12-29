package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.SplashUI;
import com.baidu.zhuanche.adapter.HomeGridAdapter;
import com.baidu.zhuanche.adapter.HotAskAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.base.MyBaseApdater;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.holder.UserHomePicHolder;
import com.baidu.zhuanche.view.NoScrolledGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui
 * @类名: UserHomeUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-26 上午11:37:53
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class UserHomeUI extends BaseActivity implements OnClickListener
{
	private PullToRefreshListView	mListView;
	private LinearLayout			mContainerLogin;	// 点击过境小车登陆
	private User					mUser;
	private FrameLayout				mContainerPic;		// 首页轮播图
	private List<String>			mListDatas;
	private HotAskAdapter			mAskAdapter;
	private NoScrolledGridView		mGridView;
	private List<Integer>			mGridViewDatas;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_userhome);
		mListView = (PullToRefreshListView) findViewById(R.id.home_listview);
		View headerView = View.inflate(this, R.layout.layout_uh_header, null);
		mContainerPic = (FrameLayout) headerView.findViewById(R.id.uh_container_pic);
		mContainerLogin = (LinearLayout) headerView.findViewById(R.id.home_container_guojingxiaoche);
		mGridView = (NoScrolledGridView) headerView.findViewById(R.id.uh_gridview);
		/** 为pulltorefreshListview添加头 */
		mListView.setMode(Mode.PULL_FROM_END);
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
		headerView.setLayoutParams(layoutParams);
		ListView lv = mListView.getRefreshableView();
		lv.addHeaderView(headerView);
		mListView.setAdapter(null); // TODO 显示头视图的作用？
	}

	@Override
	public void initData()
	{
		super.initData();

		mUser = BaseApplication.getUser();
		mTvTitle.setText("首页");
		UserHomePicHolder picHolder = new UserHomePicHolder();
		mContainerPic.addView(picHolder.mHolderView);
		mGridViewDatas = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++){
			mGridViewDatas.add(R.drawable.gridview_01);
		}
		mGridView.setAdapter(new HomeGridAdapter(this, mGridViewDatas));
		
		
		
		/** 新闻资讯数据 */
		List<String> picDatas = new ArrayList<String>(); // TODO 模拟数据
		picDatas.add("http://192.168.1.131:8080/zc01.jpg");
		picDatas.add("http://192.168.1.131:8080/zc02.jpg");
		picDatas.add("http://192.168.1.131:8080/zc03.jpg");
		picHolder.setDataAndRefreshHolderView(picDatas);

		mListDatas = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			mListDatas.add("哈哈哈");
		}
		mAskAdapter = new HotAskAdapter(this, mListDatas);
		mListView.setAdapter(mAskAdapter);
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mContainerLogin.setOnClickListener(this);
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				String str = DateUtils.formatDateTime(UserHomeUI.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				/** 上拉加载数据设置 */
				mListView.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
				mListView.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
				mListView.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + str);
				new MyTask().execute();
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		if (v == mContainerLogin)
		{
			if (mUser == null || TextUtils.isEmpty(mUser.mobile))
			{
				startActivity(UserLoginSelectUI.class);
			}
			else
			{
				startActivity(YuyueUI.class);
			}
		}else if(v == mIvLeftHeader){
			finishActivity(SplashUI.class);
		}
	}

	private class MyTask extends AsyncTask<Void, Void, List<String>>
	{

		@Override
		protected List<String> doInBackground(Void... params)
		{
			SystemClock.sleep(2000);
			for (int i = 0; i < 10; i++)
			{
				mListDatas.add("哈哈哈");
			}
			return mListDatas;
		}

		@Override
		protected void onPostExecute(List<String> result)
		{
			mAskAdapter.notifyDataSetChanged();
			mListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
