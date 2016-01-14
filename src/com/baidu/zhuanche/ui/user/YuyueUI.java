package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.feezu.liuli.timeselector.TimeSelector;
import org.feezu.liuli.timeselector.Utils.DateUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.DialogAdapter;
import com.baidu.zhuanche.adapter.LevelAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.CartTypeBean;
import com.baidu.zhuanche.bean.CartTypeBean.LevelBean;
import com.baidu.zhuanche.bean.Location;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.bean.Yuyue;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.ui.user.GetOffUI.OnGetOffLocationListener;
import com.baidu.zhuanche.ui.user.GetOnUI.OnGetOnLocationListener;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.MD5Utils;
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
		mClient = AsyncHttpClientUtil.getInstance();
		
		
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
		List<String> dataSource = new ArrayList<String>();
		dataSource.add("专车");
		dataSource.add("顺风车");
		final DialogAdapter adapter = new DialogAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String carpool = (String) adapter.getItem(which);
				mTvType.setText(carpool);
				mYuyueData.carpool = carpool.equals("专车") ? "0" : "1";
			}
		});
		mBuilder.show();
	}

	/** 签证类型 */
	private void doClickSignType()
	{
		List<String> dataSource = new ArrayList<String>();
		dataSource.add("自由行");
		dataSource.add("团签");
		dataSource.add("回乡证");
		dataSource.add("护照");
		final DialogAdapter adapter = new DialogAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String signtype = (String) adapter.getItem(which);
				mTvSignType.setText(signtype);
				mYuyueData.signtype = signtype;
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
		List<String> dataSource = new ArrayList<String>();
		dataSource.add("皇岗口岸");
		dataSource.add("深圳湾口岸");
		dataSource.add("文锦渡口岸");
		dataSource.add("沙头角口岸");
		final DialogAdapter adapter = new DialogAdapter(this, dataSource);
		mBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String seaport = (String) adapter.getItem(which);
				mTvPort.setText(seaport);
				mYuyueData.seaport = seaport;
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
