package com.baidu.zhuanche.ui.driver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.MyBaseApdater;
import com.baidu.zhuanche.utils.ToastUtils;
import com.baidu.zhuanche.view.CircleImageView;
import com.baidu.zhuanche.view.NoScrollListView;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.driver
 * @类名: DriverUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-31 下午3:36:29
 * @描述: 司机自己
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DriverUI extends BaseActivity implements OnClickListener
{
	private NoScrollListView	mListView;
	private ScrollView			mScrollView;
	private CircleImageView     mCivPhoto; //头像
	private TextView			mTvName; //名称
	private RatingBar			mRatingBar;
	
	private TextView  			mContainerIdentity;
	private TextView 			mContainerMessage;
	private TextView			mContainerOrder;
	private TextView			mContainerAccount;
	private TextView			mContainerAssess;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driver);
		mListView = (NoScrollListView) findViewById(R.id.driver_listview);
		mScrollView = (ScrollView) findViewById(R.id.driver_scrollview);
		mCivPhoto = (CircleImageView) findViewById(R.id.driver_civ_photo);
		mTvName = (TextView) findViewById(R.id.driver_tv_name);
		mRatingBar = (RatingBar) findViewById(R.id.driver_ratingbar);

		mContainerIdentity = (TextView) findViewById(R.id.driver_tv_checkidentity);
		mContainerMessage = (TextView) findViewById(R.id.driver_tv_mymsg);
		mContainerOrder = (TextView) findViewById(R.id.driver_tv_allorder);
		mContainerAccount = (TextView) findViewById(R.id.driver_tv_myaccount);
		mContainerAssess = (TextView) findViewById(R.id.driver_tv_allassess);
				
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("我");
		mIvRightHeader.setVisibility(0);
		mIvRightHeader.setImageResource(R.drawable.picture_19);
		List<String> datas = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			datas.add("" + i);
		}
		mListView.setAdapter(new DriverOrderListAdapter(this, datas));
		mScrollView.smoothScrollTo(0, 0);
	}

	@Override
	public void initListener()    
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mIvRightHeader.setOnClickListener(this);
		mContainerIdentity.setOnClickListener(this);
		mContainerMessage.setOnClickListener(this);
		mContainerOrder.setOnClickListener(this);
		mContainerAccount.setOnClickListener(this);
		mContainerAssess.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mIvRightHeader)
		{
			// TODO 设置按钮
		}else if(v == mContainerIdentity){
			startActivity(IdentityCheckUI.class);
		}else if(v == mContainerMessage){
			//TODO 我的消息
			ToastUtils.makeShortText(this, "我的消息");
		}else if(v == mContainerOrder){
			//TODO 我的订单
			ToastUtils.makeShortText(this, "全部订单");
		}else if(v == mContainerAccount){
			//TODO 我的账户
			ToastUtils.makeShortText(this, "我的账户");
		}else if(v == mContainerMessage){
			//TODO 全部评价
			ToastUtils.makeShortText(this, "全部评价");
		}
	}

	class DriverOrderListAdapter extends MyBaseApdater<String>
	{

		public DriverOrderListAdapter(Context context, List<String> dataSource) {
			super(context, dataSource);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = View.inflate(mContext, R.layout.item_driver_listview, null);
			return convertView;
		}
	}
	private class ViewHolder{
		
	}
	@Override
	public void onBackPressed()
	{
		finishActivity();
	}

	
}
