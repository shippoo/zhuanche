package com.baidu.zhuanche.ui;

import java.io.File;

import org.apache.http.Header;

import android.content.Intent;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.conf.MyConstains;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.DataCleanManager;
import com.baidu.zhuanche.utils.FileUtils;
import com.baidu.zhuanche.utils.ToastUtils;
import com.baidu.zhuanche.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SettingUI extends BaseActivity implements OnClickListener
{
	private RelativeLayout	mContainerPassword;
	private RelativeLayout	mContainerCache;
	private RelativeLayout	mContainerMsg;
	private RelativeLayout	mContainerCheck;
	private RelativeLayout	mContainerUs;
	private RelativeLayout	mContainerFanKui;
	private Button			mBtLogout;
	private TextView		mTvCache;
	private ToggleButton	mTbToggle;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_user_setting);
		mContainerPassword = (RelativeLayout) findViewById(R.id.setting_xgpassword);
		mContainerCache = (RelativeLayout) findViewById(R.id.setting_container_cache);
		mContainerCheck = (RelativeLayout) findViewById(R.id.setting_container_check);
		mContainerFanKui = (RelativeLayout) findViewById(R.id.setting_container_fankui);
		mContainerMsg = (RelativeLayout) findViewById(R.id.setting_container_msg);
		mContainerUs = (RelativeLayout) findViewById(R.id.setting_container_us);
		mTvCache = (TextView) findViewById(R.id.setting_tv_cache);
		mTbToggle = (ToggleButton) findViewById(R.id.setting_tb_toggle);
		mBtLogout = (Button) findViewById(R.id.setting_bt_logout);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("设置");
		mTvCache.setText(Formatter.formatFileSize(this, getCache()));
	}

	/**
	 * 获取缓存大小
	 * 
	 * @return
	 */
	private long getCache()
	{
		long cache = 0;
		try
		{
			cache = DataCleanManager.getFolderSize(new File(FileUtils.getCacheDir()));
			cache += DataCleanManager.getFolderSize(new File(FileUtils.getIconDir()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return cache;
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mContainerPassword.setOnClickListener(this);
		mContainerCache.setOnClickListener(this);
		mBtLogout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mContainerPassword)
		{
		}
		else if (v == mContainerCache)
		{
			if (getCache() == 0)
			{
				ToastUtils.makeShortText(this, "没有缓存！");
			}
			else
			{
				ToastUtils.makeShortText(this, "清理" + Formatter.formatFileSize(this, getCache()) + "缓存！");
				DataCleanManager.cleanCustomCache(FileUtils.getIconDir());
				DataCleanManager.cleanCustomCache(FileUtils.getCacheDir());
				mTvCache.setText(Formatter.formatFileSize(this, getCache()));
			}
		}
		else if (v == mBtLogout)
		{
			logout();
		}
	}

	/**
	 * 退出
	 */
	private void logout()
	{
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance();
		String url = URLS.BASESERVER + URLS.User.logout;
		ToastUtils.showProgress(this);
		client.post(url, new MyAsyncResponseHandler() {
			
			@Override
			public void success(String json)
			{
				ToastUtils.makeShortText(getApplicationContext(), "你已经成功退出！");
				exit();
			}
		});
	}



}
