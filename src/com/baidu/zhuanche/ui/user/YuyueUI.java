package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.feezu.liuli.timeselector.TimeSelector;
import org.feezu.liuli.timeselector.Utils.DateUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.DialogSignAdapter;
import com.baidu.zhuanche.adapter.LevelAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.CartTypeBean;
import com.baidu.zhuanche.bean.CartTypeBean.LevelBean;
import com.baidu.zhuanche.bean.Location;
import com.baidu.zhuanche.bean.Sex;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.bean.Yuyue;
import com.baidu.zhuanche.conf.MyConstains;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.ui.user.GetOffUI.OnGetOffLocationListener;
import com.baidu.zhuanche.ui.user.GetOnUI.OnGetOnLocationListener;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui
 * @类名: YuyueUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-26 下午5:50:58
 * @描述: 预约界面
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class YuyueUI extends BaseActivity implements OnClickListener, OnGetOnLocationListener, OnGetOffLocationListener
{
	private Yuyue				mYuyueData;									// 预约所需值，都封装在这个class中

	private LinearLayout		mGroupView;									// 各条目容器

	private User				mUser;
	private AlertDialog.Builder	mBuilder;										// 对话框
	private AsyncHttpClient		mClient	= AsyncHttpClientUtil.getInstance();
	/** 级别容器 */
	private RelativeLayout		mContainerLevel;
	private TextView			mTvLevel;
	/** 类型容器 */
	private RelativeLayout		mContainerType;
	private TextView			mTvType;
	/** 签证类型 */
	private RelativeLayout		mContainerSignType;
	private TextView			mTvSignType;
	/** 时间 */
	private RelativeLayout		mContainerTime;
	private TextView			mTvTime;
	/** 口岸 */
	private RelativeLayout		mContainerPort;
	private TextView			mTvPort;
	/** 上车地点 */
	private RelativeLayout		mContainerGetOn;
	private TextView			mTvGetOn;
	/** 下车地点 */
	private RelativeLayout		mContainerGetOff;
	private TextView			mTvGetOff;
	private Button				mBtYuyue;
	private EditText			mEtPeopleCount;
	private EditText			mEtXingLiCount;
	private EditText			mEtFee;
	private EditText			mEtDes;

	@Override
	public void init()
	{
		super.init();
		setUserAligs();
	}

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_yuyue);
		mGroupView = (LinearLayout) findViewById(R.id.yuyue_groupview);
		View view = View.inflate(this, R.layout.layout_group_children, null);
		mGroupView.addView(view);
		// 级别
		mContainerLevel = (RelativeLayout) view.findViewById(R.id.yuyueContainer_level);
		mTvLevel = (TextView) view.findViewById(R.id.yuyue_tv_level);
		// 类型
		mContainerType = (RelativeLayout) view.findViewById(R.id.yuyueContainer_type);
		mTvType = (TextView) view.findViewById(R.id.yuyue_tv_type);
		// 签证类型
		mContainerSignType = (RelativeLayout) view.findViewById(R.id.yuyueContainer_signtype);
		mTvSignType = (TextView) view.findViewById(R.id.yuyue_tv_signtype);
		// 时间
		mContainerTime = (RelativeLayout) view.findViewById(R.id.yuyueContainer_date);
		mTvTime = (TextView) view.findViewById(R.id.yuyue_tv_time);
		// 口岸
		mContainerPort = (RelativeLayout) view.findViewById(R.id.yuyueContainer_port);
		mTvPort = (TextView) view.findViewById(R.id.yuyue_tv_port);
		// 上车地点
		mContainerGetOn = (RelativeLayout) view.findViewById(R.id.yuyueContainer_geton);
		mTvGetOn = (TextView) view.findViewById(R.id.yuyue_tv_geton);
		// 下车地点
		mContainerGetOff = (RelativeLayout) view.findViewById(R.id.yuyueContainer_getoff);
		mTvGetOff = (TextView) view.findViewById(R.id.yuyue_tv_getoff);

		mBtYuyue = (Button)findViewById(R.id.home_bt_yuyue);
		mEtPeopleCount = (EditText) view.findViewById(R.id.childer_et_peopleCount);
		mEtXingLiCount = (EditText) view.findViewById(R.id.childer_et_luggageCount);
		mEtDes = (EditText) view.findViewById(R.id.yuyue_tv_des);
		mEtFee = (EditText) view.findViewById(R.id.yuyue_et_fee);
	}

	@Override
	public void initData()
	{
		super.initData();
		mUser = BaseApplication.getUser();
		mIvRightHeader.setVisibility(0);
		mIvRightHeader.setImageResource(R.drawable.page_02);
		mTvTitle.setText("预约");
		mYuyueData = new Yuyue();
		mBuilder = new Builder(this);
	}

	@Override
	public void initListener()
	{
		super.initListener();
		GetOnUI.setOnGetOnLocationListener(this);// 上车地点接口回调
		GetOffUI.setOnGetOffLocationListener(this);
		mIvLeftHeader.setOnClickListener(this);
		mIvRightHeader.setOnClickListener(this);
		mContainerLevel.setOnClickListener(this);
		mContainerType.setOnClickListener(this);
		mContainerSignType.setOnClickListener(this);
		mContainerTime.setOnClickListener(this);
		mContainerPort.setOnClickListener(this);
		mContainerGetOn.setOnClickListener(this);
		mContainerGetOff.setOnClickListener(this);
		mBtYuyue.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mContainerLevel)
		{
			doClickLevel();
		}
		else if (v == mIvLeftHeader)
		{
			finishActivity(UserHomeUI.class);
		}
		else if (v == mIvRightHeader)
		{
			startActivity(UserCenterUI.class);
		}
		else if (v == mContainerType)
		{
			doClickType();
		}
		else if (v == mContainerSignType)
		{
			doClickSignType();
		}
		else if (v == mContainerTime)
		{
			doClickTime();
		}
		else if (v == mContainerPort)
		{
			doClickPort();
		}
		else if (v == mContainerGetOn)
		{
			startActivity(GetOnUI.class);
		}
		else if (v == mContainerGetOff)
		{
			startActivity(GetOffUI.class);
		}
		else if (v == mBtYuyue)
		{
			yuyueOrder();
		}
	}

	private void yuyueOrder()
	{
		mYuyueData.peopleCount = mEtPeopleCount.getText().toString().trim();
		mYuyueData.xingliCount = mEtXingLiCount.getText().toString().trim();
		mYuyueData.fee = mEtFee.getText().toString().trim();
		mYuyueData.des = mEtDes.getText().toString().trim();
		if (TextUtils.isEmpty(mYuyueData.cartype))
		{
			ToastUtils.makeShortText("请选择级别");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.carpool))
		{
			ToastUtils.makeShortText("请选择类型");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.peopleCount))
		{
			ToastUtils.makeShortText("请输入乘车人数");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.xingliCount))
		{
			ToastUtils.makeShortText("请输入行李数");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.signtype))
		{
			ToastUtils.makeShortText("请选择签证类型");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.time))
		{
			ToastUtils.makeShortText("请选择时间");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.seaport))
		{
			ToastUtils.makeShortText("请选择口岸");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.getOnLocation.address))
		{
			ToastUtils.makeShortText("请选择上车地点");
			return;
		}
		else if (TextUtils.isEmpty(mYuyueData.getOffLocation.address))
		{
			ToastUtils.makeShortText("请选择下车地点");
			return;
		}
		String url = URLS.BASESERVER + URLS.User.makeOrder;
		RequestParams params = new RequestParams();
		params.put(URLS.ACCESS_TOKEN, mUser.access_token);
		params.put(URLS.CARTYPE,mYuyueData.cartype);
		params.put(URLS.CARPOOL, mYuyueData.carpool);
		params.put("from",mYuyueData.getOnLocation.address );
		params.put("from_province", mYuyueData.getOnLocation.province);
		params.put("from_city", mYuyueData.getOnLocation.city);
		params.put("from_district", mYuyueData.getOnLocation.district);
		params.put("to", mYuyueData.getOffLocation.address);
		params.put("to_province", mYuyueData.getOffLocation.province);
		params.put("to_city", mYuyueData.getOffLocation.city);
		params.put("to_district", mYuyueData.getOffLocation.district);
		params.put("count", mYuyueData.peopleCount);
		params.put("luggage",mYuyueData.xingliCount);
		params.put("is_hk", mYuyueData.signtype);
		params.put("time", mYuyueData.time);
		params.put("seaport", mYuyueData.seaport);
		params.put("budget", "6666");
		params.put("fee", mYuyueData.fee);
		params.put("remark", mYuyueData.des);
		mClient.post(url, params, new MyAsyncResponseHandler() {
			
			@Override
			public void success(String json)
			{
				ToastUtils.makeShortText("接单成功！");
				mYuyueData = new Yuyue();
			}
		});
	}

	/** 级别 */
	private void doClickLevel()
	{
		String url = URLS.BASESERVER + URLS.User.enum_;
		RequestParams params = new RequestParams();
		params.put(URLS.ACCESS_TOKEN, mUser.access_token);
		params.put("type", URLS.CARTYPE);
		ToastUtils.showProgress(this);
		mClient.post(url, params, new MyAsyncResponseHandler() {

			@Override
			public void success(String json)
			{
				Gson gson = new Gson();
				CartTypeBean cartTypeBean = gson.fromJson(json, CartTypeBean.class);
				final List<LevelBean> datas = cartTypeBean.content;
				LevelAdapter adapter = new LevelAdapter(datas);
				mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						LevelBean bean = datas.get(which);
						mYuyueData.cartype = bean.value;
						mTvLevel.setText(bean.name);
					}
				});
				mBuilder.show();
			}
		});

	}

	/** 类型 */
	private void doClickType()
	{
		List<Sex> dataSource = new ArrayList<Sex>();
		dataSource.add(new Sex("专车", "0"));
		dataSource.add(new Sex("顺风车", "1"));
		final DialogSignAdapter adapter = new DialogSignAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Sex sex = (Sex) adapter.getItem(which);
				mTvType.setText(sex.name);
				mYuyueData.carpool = sex.value;
			}
		});
		mBuilder.show();
	}

	/** 签证类型 */
	private void doClickSignType()
	{
		List<Sex> dataSource = new ArrayList<Sex>();
		dataSource.add(new Sex("自由行", "0"));
		dataSource.add(new Sex("团签", "1"));
		dataSource.add(new Sex("回乡证", "2"));
		dataSource.add(new Sex("护照", "3"));
		final DialogSignAdapter adapter = new DialogSignAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Sex sex = (Sex) adapter.getItem(which);
				mTvSignType.setText(sex.name);
				mYuyueData.signtype = sex.value;
			}
		});
		mBuilder.show();
	}

	/** 时间 */
	private void doClickTime()
	{
		String initStartDateTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"); // 初始化开始时间
		TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
			@Override
			public void handle(String time)
			{
				Date date = DateUtil.parse(time, "yyyy-MM-dd HH:mm");
				mTvTime.setText(time);
				long time1 = (date.getTime() / 1000);
				mYuyueData.time = "" + time1;
			}
		}, initStartDateTime, "2100-01-01 00:00");
		timeSelector.show();
	}

	/** 口岸 */
	private void doClickPort()
	{
		List<Sex> dataSource = new ArrayList<Sex>();
		dataSource.add(new Sex("皇岗", "0"));
		dataSource.add(new Sex("深圳湾", "1"));
		dataSource.add(new Sex("文锦渡", "2"));
		dataSource.add(new Sex("沙头角", "3"));
		final DialogSignAdapter adapter = new DialogSignAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Sex sex = (Sex) adapter.getItem(which);
				mTvPort.setText(sex.name);
				mYuyueData.seaport = sex.value;
			}
		});
		mBuilder.show();
	}

	@Override
	public void onGetOnLocation(Location location)
	{
		mYuyueData.getOnLocation = location;
		mTvGetOn.setText(location.address);
	}

	@Override
	public void onGetOffLocation(Location location)
	{
		mYuyueData.getOffLocation = location;
		mTvGetOff.setText(location.address);
	}
}
