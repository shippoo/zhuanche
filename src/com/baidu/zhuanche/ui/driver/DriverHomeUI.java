package com.baidu.zhuanche.ui.driver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.co;
import com.baidu.zhuanche.R;
import com.baidu.zhuanche.SplashUI;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.MyBaseApdater;
import com.baidu.zhuanche.utils.ToastUtils;

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
public class DriverHomeUI extends BaseActivity implements OnClickListener, OnItemClickListener
{
	private ListView				mListView;
	private DriverOrderListAdapter	mAdapter;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driverhome);
		mListView = (ListView) findViewById(R.id.driverhome_listview);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("首页");
		mIvRightHeader.setVisibility(0);
		mIvRightHeader.setImageResource(R.drawable.picture_31);
		List<String> datas = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			datas.add("" + i);
		}
		mAdapter = new DriverOrderListAdapter(this, datas);
		mListView.setAdapter(mAdapter);
		if (mListView != null)
		{
			mListView.setOnItemClickListener(this);
		}
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mIvRightHeader.setOnClickListener(this);

	}

	class DriverOrderListAdapter extends MyBaseApdater<String>
	{

		public DriverOrderListAdapter(Context context, List<String> dataSource) {
			super(context, dataSource);
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
				holder.ivPic = (ImageView) convertView.findViewById(R.id.item_driverhome_civ_photo);
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
					ToastUtils.makeShortText(getApplicationContext(), "接单成功！");
					startActivity(DriverUI.class);
				}
			});
			return convertView;
		}
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity(SplashUI.class);
		}
		else if (v == mIvRightHeader)
		{
			startActivity(DriverUI.class);
		}
	}

	class ViewHolder
	{
		ImageView	ivPic;
		TextView	tvTime;
		TextView	tvGetOn;
		TextView	tvGetOff;
		TextView	tvLevel;
		TextView	tvCarPool;
		TextView	tvPrice;
		TextView	tvFee;
		TextView	tvSpace;
		Button		btAcceptOrder;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO 这里需要传值，将本条目的值传过去
		ToastUtils.makeShortText(this, "子条目被点击！");
		startActivity(AcceptOrderUI.class);
	}
}
