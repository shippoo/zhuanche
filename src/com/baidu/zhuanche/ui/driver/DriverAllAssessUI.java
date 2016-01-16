package com.baidu.zhuanche.ui.driver;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.AssessAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.AllAssessBean;
import com.baidu.zhuanche.bean.AllAssessBean.Assess;
import com.baidu.zhuanche.bean.AllAssessBean.DriverInfo;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.view.CircleImageView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.driver
 * @类名: DriverAllAssessUI
 * @创建者: 陈选文
 * @创建时间: 2016-1-6 下午3:41:44
 * @描述: 司机全部评价
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DriverAllAssessUI extends BaseActivity implements OnClickListener, OnRefreshListener<ListView>
{
	private PullToRefreshListView	mListView;
	private int						currentPage	= 1;
	private List<Assess>			mDatas = new ArrayList<AllAssessBean.Assess>();
	private DriverInfo				mDriverInfo;
	private AssessAdapter			mAdapter;
	private TextView				mTvName;
	private TextView				mTvCarNum;			// 车牌
	private TextView				mTvCarName;
	private TextView				mTvIdCard;
	private TextView				mTvRating;
	private TextView				mTvServiceCount;
	private TextView				mTvRanking;
	private CircleImageView			mCivPhoto;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_allassess);
		mListView = (PullToRefreshListView) findViewById(R.id.allassess_listview);
		mTvName = (TextView) findViewById(R.id.allassess_tv_name);
		mTvCarNum = (TextView) findViewById(R.id.allassess_tv_carnum);
		mTvCarName = (TextView) findViewById(R.id.allassess_tv_carname);
		mTvIdCard = (TextView) findViewById(R.id.allassess_tv_idcard);
		mTvRating = (TextView) findViewById(R.id.allassess_tv_rating);
		mTvServiceCount = (TextView) findViewById(R.id.allassess_tv_servercount);
		mTvRanking = (TextView) findViewById(R.id.allassess_tv_ranking);
		mCivPhoto = (CircleImageView) findViewById(R.id.allassess_civ_pic);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("評價列表");
		mListView.setMode(Mode.PULL_FROM_END);
		mAdapter = new AssessAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
		setEmptyView(mListView, "沒有評價記錄！");
		String url = URLS.BASESERVER + URLS.Driver.allComment;
		RequestParams params = new RequestParams();
		params.add(URLS.ACCESS_TOKEN, BaseApplication.getDriver().access_token);
		params.add(URLS.CURRENTPAGER, "" + currentPage);
		mClient.post(url, params, new MyAsyncResponseHandler() {

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
		AllAssessBean allAssessBean = gson.fromJson(json, AllAssessBean.class);
		mDriverInfo = allAssessBean.content.driverInfo;
		if(allAssessBean.content.comment != null && allAssessBean.content.comment.size() > 0){
			mDatas.addAll(allAssessBean.content.comment);
			mAdapter.notifyDataSetChanged();
			currentPage++;
		}
		mListView.onRefreshComplete();
		/*
		 * 设置司机信息
		 */
		mTvCarNum.setText(mDriverInfo.carid);
		mTvName.setText(mDriverInfo.name);
		mTvIdCard.setText(mDriverInfo.citizenid);
		mImageUtils.display(mCivPhoto, URLS.BASE + mDriverInfo.icon);
		mTvCarName.setText(mDriverInfo.type);
	}

	@Override
	public void initListener()
	{
		mIvLeftHeader.setOnClickListener(this);
		mListView.setOnRefreshListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
	}

	@Override
	public void onBackPressed()
	{
		finishActivity();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView)
	{
		setPullRefreshListDriverLoadMoreData(refreshView);
		mListView.postDelayed(new LoadMoreTask(), 1000);
	}

	private class LoadMoreTask implements Runnable
	{

		@Override
		public void run()
		{
			String url = URLS.BASESERVER + URLS.Driver.allComment;
			RequestParams params = new RequestParams();
			params.add(URLS.ACCESS_TOKEN, BaseApplication.getDriver().access_token);
			params.add(URLS.CURRENTPAGER, "" + currentPage);
			mClient.post(url, params, new MyAsyncResponseHandler() {

				@Override
				public void success(String json)
				{
					processJson(json);
				}
			});
		}

	}
}
