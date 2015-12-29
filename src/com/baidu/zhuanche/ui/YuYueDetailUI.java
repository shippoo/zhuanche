package com.baidu.zhuanche.ui;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.bean.OrderListBean.OrderBean;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class YuYueDetailUI extends BaseActivity implements OnClickListener
{

	private TextView	mTvTitle;
	private OrderBean	mOrderBean;

	@Override
	public void init()
	{
		super.init();
		mOrderBean = (OrderBean) getIntent().getSerializableExtra("orderbean");
	}

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_yuyue_detail);
		mTvTitle = (TextView) findViewById(R.id.header_title);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("预约详情");
	}

	@Override
	public void initListener()
	{
		mIvLeftHeader.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if(v == mIvLeftHeader){
			finishActivity();
		}
	}

}
