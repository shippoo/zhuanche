package com.baidu.zhuanche.ui.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.base.MyBaseApdater;
import com.baidu.zhuanche.bean.DriverCenterOrderListBean;
import com.baidu.zhuanche.bean.DriverCenterOrderListBean.OrderBean;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.utils.AtoolsUtil;
import com.baidu.zhuanche.utils.DateFormatUtil;
import com.baidu.zhuanche.utils.ToastUtils;
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
 * @类名: DriverHomeUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-31 下午1:58:49
 * @描述: 司机端首页
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DriverHomeUI extends BaseActivity implements OnClickListener, OnItemClickListener, OnRefreshListener<ListView>
{
	private PullToRefreshListView	mListView;
	private DriverOrderListAdapter	mAdapter;
	private List<OrderBean>			mDatas		= new ArrayList<DriverCenterOrderListBean.OrderBean>();
	private int						currentPage	= 1;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driverhome);
		mListView = (PullToRefreshListView) findViewById(R.id.driverhome_listview);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("首頁");
		mListView.setMode(Mode.PULL_FROM_END);
		mIvRightHeader.setVisibility(0);
		mIvLeftHeader.setVisibility(8);
		mIvRightHeader.setImageResource(R.drawable.picture_31);

		mAdapter = new DriverOrderListAdapter(this, mDatas);
		mListView.setAdapter(mAdapter);
		setEmptyView(mListView, "沒有訂單列表數據");
		ToastUtils.showProgress(this);
		loadMore();

	}

	public void loadMore()
	{
		String url = URLS.BASESERVER + URLS.Driver.orderList;
		RequestParams params = new RequestParams();
		params.add(URLS.ACCESS_TOKEN, BaseApplication.getDriver().access_token);
		params.add("limit", "" + 10);
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
		DriverCenterOrderListBean driverOrderListBean = gson.fromJson(json, DriverCenterOrderListBean.class);
		if (driverOrderListBean.content != null && driverOrderListBean.content.size() > 0)
		{
			mDatas.addAll(driverOrderListBean.content);
			mAdapter.notifyDataSetChanged();
			currentPage++;
		}
		mListView.onRefreshComplete();
	}

	@Override
	public void initListener()
	{
		super.initListener();
		// mIvLeftHeader.setOnClickListener(this);
		mIvRightHeader.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnRefreshListener(this);
	}

	/** 司机端订单适配器 */
	class DriverOrderListAdapter extends MyBaseApdater<OrderBean>
	{

		public DriverOrderListAdapter(Context context, List<OrderBean> datas) {
			super(context, datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.item_driverhome_listview, null);
				convertView.setTag(holder);
				holder.ivPic = (CircleImageView) convertView.findViewById(R.id.item_driverhome_civ_photo);
				holder.tvTime = (TextView) convertView.findViewById(R.id.driverhome_tv_time);
				holder.tvGetOn = (TextView) convertView.findViewById(R.id.driverhome_tv_geton);
				holder.tvGetOff = (TextView) convertView.findViewById(R.id.driverhome_tv_getoff);
				holder.tvLevel = (TextView) convertView.findViewById(R.id.driverhome_tv_level);
				holder.tvCarPool = (TextView) convertView.findViewById(R.id.driverhome_tv_carpool);
				holder.tvPrice = (TextView) convertView.findViewById(R.id.driverhome_tv_price);
				holder.tvFee = (TextView) convertView.findViewById(R.id.driverhome_tv_fee);
				holder.tvSpace = (TextView) convertView.findViewById(R.id.driverhome_tv_distance);
				holder.btAcceptOrder = (Button) convertView.findViewById(R.id.driverhome_bt_acceptorder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.btAcceptOrder.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					startActivity(DriverUI.class);
				}
			});
			OrderBean bean = (OrderBean) getItem(position);
			holder.tvTime.setText(DateFormatUtil.getDateTimeStr(new Date(Long.parseLong(bean.time) * 1000)));
			holder.tvGetOn.setText(bean.from);
			holder.tvGetOff.setText(bean.to);
			holder.tvCarPool.setText(AtoolsUtil.getCarPool(bean.carpool));
			holder.tvLevel.setText(bean.cartype);
			holder.tvPrice.setText(bean.budget);
			holder.tvFee.setText(bean.fee);
			mImageUtils.display(holder.ivPic, URLS.BASE + bean.icon);
			return convertView;
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			// finishActivity(SplashUI.class);
		}
		else if (v == mIvRightHeader)
		{
			startActivity(DriverUI.class);
		}
	}

	private class ViewHolder
	{
		CircleImageView	ivPic;
		TextView		tvTime;
		TextView		tvGetOn;
		TextView		tvGetOff;
		TextView		tvLevel;
		TextView		tvCarPool;
		TextView		tvPrice;
		TextView		tvFee;
		TextView		tvSpace;
		Button			btAcceptOrder;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO 这里需要传值，将本条目的值传过去
		Bundle bundle = new Bundle();
		bundle.putSerializable("orderbean", mDatas.get(position));
		startActivity(AcceptOrderUI.class, bundle);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView)
	{
		setPullRefreshListDriverLoadMoreData(refreshView);
		mListView.postDelayed(new LoadMore(), 1000);
	}

	private class LoadMore implements Runnable
	{

		@Override
		public void run()
		{
			loadMore();
		}

	}
}
