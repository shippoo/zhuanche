package com.baidu.zhuanche.ui.driver;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.utils.ToastUtils;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.driver
 * @类名: AcceptOrderUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-31 下午8:55:53
 * @描述: 接单详细
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class AcceptOrderUI extends BaseActivity implements OnClickListener
{
	private Button		mBtOrder;		// 接单按钮
	private ImageView	mIvPhoto;		// 头像
	private TextView	mTvName;		// 名称
	private TextView	mTvTime;		// 时间
	private TextView	mTvPrice;		// 价钱
	private TextView	mTvGetOn;		// 上车地点
	private TextView	mTvGetOff;		// 下车地点
	private TextView	mTvLevel;		// 等级
	private TextView	mTvCarPool;	// 类型
	private TextView	mTvSeaport;	// 口岸
	private TextView	mTvPeopleCount; // 人数
	private TextView	mTvXingLiCount; // 行李数
	private TextView	mTvSignType;	// 签证类型
	private TextView	mTvYuyuePrice;	// 预约价格
	private TextView	mTvFee;		// 小费
	private TextView	mTvHangBan;	// 航班号

	
	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("接单详细");
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mBtOrder.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mBtOrder)
		{
			ToastUtils.makeShortText(this, "接单成功！");
		}
	}
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_acceptorder);
		mBtOrder = (Button) findViewById(R.id.acceptorder_bt_acceptorder);
		mIvPhoto = (ImageView) findViewById(R.id.acceptorder_iv_photo);
		mTvName = (TextView) findViewById(R.id.acceptorder_tv_name);
		mTvTime = (TextView) findViewById(R.id.acceptorder_tv_time);
		mTvPrice = (TextView) findViewById(R.id.acceptorder_tv_price);
		mTvGetOn = (TextView) findViewById(R.id.acceptorder_tv_geton);
		mTvGetOff = (TextView) findViewById(R.id.acceptorder_tv_getoff);
		mTvLevel = (TextView) findViewById(R.id.acceptorder_tv_level);
		mTvCarPool = (TextView) findViewById(R.id.acceptorder_tv_carpool);
		mTvSeaport = (TextView) findViewById(R.id.acceptorder_tv_seaport);
		mTvPeopleCount = (TextView) findViewById(R.id.acceptorder_tv_peoplecount);
		mTvXingLiCount = (TextView) findViewById(R.id.acceptorder_tv_xingli);
		mTvSignType = (TextView) findViewById(R.id.acceptorder_tv_signtype);
		mTvYuyuePrice = (TextView) findViewById(R.id.acceptorder_tv_yuyueprice);
		mTvFee = (TextView) findViewById(R.id.acceptorder_tv_fee);
		mTvHangBan = (TextView) findViewById(R.id.acceptorder_tv_hangban);
	}

}
