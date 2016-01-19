package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.feezu.liuli.timeselector.TimeSelector;
import org.feezu.liuli.timeselector.Utils.DateUtil;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.DialogGetSeaportAdapter;
import com.baidu.zhuanche.adapter.DialogSignAdapter;
import com.baidu.zhuanche.adapter.LevelAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.CartTypeBean;
import com.baidu.zhuanche.bean.CartTypeBean.LevelBean;
import com.baidu.zhuanche.bean.GetSeaPortBean;
import com.baidu.zhuanche.bean.GetSeaport;
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
import com.baidu.zhuanche.utils.JsonUtils;
import com.baidu.zhuanche.utils.PrintUtils;
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
	private EditText			mEtLevel;
	/** 类型容器 */
	private RelativeLayout		mContainerType;
	private EditText			mEtType;
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
	private EditText			mEtGetOn;
	/** 下车地点 */
	private RelativeLayout		mContainerGetOff;
	private EditText			mEtGetOff;

	/** 估价 */
	private RelativeLayout		mContainerBudget;
	private TextView			mTvBudget;
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
		mEtLevel = (EditText) view.findViewById(R.id.yuyue_et_level);
		// 类型
		mContainerType = (RelativeLayout) view.findViewById(R.id.yuyueContainer_type);
		mEtType = (EditText) view.findViewById(R.id.yuyue_et_type);
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
		mEtGetOn = (EditText) view.findViewById(R.id.yuyue_et_geton);
		// 下车地点
		mContainerGetOff = (RelativeLayout) view.findViewById(R.id.yuyueContainer_getoff);
		mEtGetOff = (EditText) view.findViewById(R.id.yuyue_et_getoff);
		// 估价
		mContainerBudget = (RelativeLayout) view.findViewById(R.id.yuyue_container_budget);
		mTvBudget = (TextView) view.findViewById(R.id.yuyue_tv_price);
		mBtYuyue = (Button) findViewById(R.id.home_bt_yuyue);
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

		mEtLevel.addTextChangedListener(new BudgetListener(1));
		mEtType.addTextChangedListener(new BudgetListener(2));
		mEtGetOff.addTextChangedListener(new BudgetListener(3));
		mEtGetOn.addTextChangedListener(new BudgetListener(4));
		
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
		else if (v == mContainerBudget)
		{
			getBudget();
		}
	}

	private void getBudget()
	{
		if (TextUtils.isEmpty(mYuyueData.cartype))
		{
			ToastUtils.makeShortText("请选择车级别！");
			return;
		}
		if (TextUtils.isEmpty(mYuyueData.carpool))
		{
			ToastUtils.makeShortText("请选择车类型！");
			return;
		}
		Location offLocation = mYuyueData.getOffLocation;
		Location onLocation = mYuyueData.getOnLocation;
		if (TextUtils.isEmpty(offLocation.address))
		{
			ToastUtils.makeShortText("请选择下车地点！");
			return;
		}
		if (TextUtils.isEmpty(onLocation.address))
		{
			ToastUtils.makeShortText("请选择上车地点！");
			return;
		}
		String url = URLS.BASESERVER + URLS.User.budget;
		RequestParams params = new RequestParams();
		params.put(URLS.ACCESS_TOKEN, mUser.access_token);
		params.put("from_province", mYuyueData.getOnLocation.province);
		params.put("from_city", mYuyueData.getOnLocation.city);
		params.put("from_district", mYuyueData.getOnLocation.district);
		params.put("to_province", mYuyueData.getOffLocation.province);
		params.put("to__city", mYuyueData.getOffLocation.city);
		params.put("to_district", mYuyueData.getOffLocation.district);
		params.put("cartype", mYuyueData.cartype);
		params.put("carpool", mYuyueData.carpool);
		mClient.post(url, params, new MyAsyncResponseHandler() {

			@Override
			public void success(String json)
			{
				processBudgetJson(json);
			}
		});
	}

	protected void processBudgetJson(String json)
	{
		try
		{
			JSONObject jsonObject = JsonUtils.getContent(json);
			String budget = jsonObject.getString("budget");
			mYuyueData.budget = budget;
			mTvBudget.setText(budget + "元");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
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
		params.put(URLS.CARTYPE, mYuyueData.cartype);
		params.put(URLS.CARPOOL, mYuyueData.carpool);
		params.put("from", mYuyueData.getOnLocation.address);
		params.put("from_province", mYuyueData.getOnLocation.province);
		params.put("from_city", mYuyueData.getOnLocation.city);
		params.put("from_district", mYuyueData.getOnLocation.district);
		params.put("to", mYuyueData.getOffLocation.address);
		params.put("to_province", mYuyueData.getOffLocation.province);
		params.put("to_city", mYuyueData.getOffLocation.city);
		params.put("to_district", mYuyueData.getOffLocation.district);
		params.put("count", mYuyueData.peopleCount);
		params.put("luggage", mYuyueData.xingliCount);
		params.put("is_hk", mYuyueData.signtype);
		params.put("time", mYuyueData.time);
		params.put("seaport", mYuyueData.seaport);
		params.put("budget", mYuyueData.budget);
		params.put("fee", mYuyueData.fee);
		params.put("remark", mYuyueData.des);
		params.put("point", mYuyueData.getOnLocation.latLng.longitude+"," + mYuyueData.getOnLocation.latLng.latitude);
		mClient.post(url, params, new MyAsyncResponseHandler() {

			@Override
			public void success(String json)
			{
				ToastUtils.makeShortText("预约成功！");
				mYuyueData = new Yuyue();
				clearData();
			}
		});
	}

	protected void clearData()
	{
		
	}

	/** 级别 */
	private void doClickLevel()
	{
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(this);
		String url = URLS.BASESERVER + URLS.User.enum_;
		RequestParams params = new RequestParams();
		PrintUtils.print("level=" + mUser.access_token);
		params.put(URLS.ACCESS_TOKEN, BaseApplication.getUser().access_token);
		params.put("type", URLS.CARTYPE);
		ToastUtils.showProgress(this);
		client.post(url, params, new MyAsyncResponseHandler() {

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
						mEtLevel.setText(bean.name);
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
				mEtType.setText(sex.name);
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
		if (TextUtils.isEmpty(mYuyueData.time))
		{
			ToastUtils.makeShortText("请选择时间");
			return;
		}
		ToastUtils.showProgress(this);
		String url = URLS.BASESERVER + URLS.User.getSeaport;
		RequestParams params = new RequestParams();
		params.put(URLS.ACCESS_TOKEN, BaseApplication.getUser().access_token);
		params.put("time", mYuyueData.time);
		mClient.post(url, params, new MyAsyncResponseHandler() {

			@Override
			public void success(String json)
			{
				processJson(json);
			}
		});
	}
	@Override
	protected void onRestart()
	{
		super.onRestart();
		mUser = BaseApplication.getUser();
	}
	protected void processJson(String json)
	{
		List<GetSeaport> dataSource = new ArrayList<GetSeaport>();
		GetSeaPortBean seaPortBean = mGson.fromJson(json, GetSeaPortBean.class);
		dataSource = seaPortBean.content;
		GetSeaport sear= new GetSeaport();
		sear.eid = "0";
		sear.name = "不限定口岸";
		//dataSource.add(sear);
		PrintUtils.print(dataSource.get(0).toString());
		if(isListEmpty(dataSource)){
			return;
		}
		final DialogGetSeaportAdapter adapter = new DialogGetSeaportAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				GetSeaport sex = (GetSeaport) adapter.getItem(which);
				mTvPort.setText(sex.name);
				mYuyueData.seaport = sex.eid;
			}
		});
		mBuilder.show();
	}

	@Override
	public void onGetOnLocation(Location location)
	{
		mYuyueData.getOnLocation = location;
		mEtGetOn.setText(location.address);
	}

	@Override
	public void onGetOffLocation(Location location)
	{
		mYuyueData.getOffLocation = location;
		mEtGetOff.setText(location.address);
	}

	private class BudgetListener implements TextWatcher
	{
		private int	index;

		public BudgetListener(int i) {
			index = i;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			if (TextUtils.isEmpty(s)) { return; }
			if (TextUtils.isEmpty(mYuyueData.cartype)) { return; }
			if (TextUtils.isEmpty(mYuyueData.carpool)) { return; }
			Location offLocation = mYuyueData.getOffLocation;
			Location onLocation = mYuyueData.getOnLocation;
			if (TextUtils.isEmpty(offLocation.address)) { return; }
			if (TextUtils.isEmpty(onLocation.address)) { return; }
			getBudget();
		}

		@Override
		public void afterTextChanged(Editable s)
		{

		}

	}
}
