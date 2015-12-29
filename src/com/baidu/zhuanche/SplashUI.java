package com.baidu.zhuanche;

import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.ui.UserHomeUI;
import com.baidu.zhuanche.ui.UserHomeUI;
import com.baidu.zhuanche.ui.UserLoginSelectUI;
import com.baidu.zhuanche.utils.SPUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashUI extends BaseActivity implements OnClickListener
{
	private Button mBtUser;
	private Button mBtDriver;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_splash);
		mBtDriver = (Button) findViewById(R.id.splash_button_driver);
		mBtUser = (Button) findViewById(R.id.splash_button_user);
	}
	@Override
	public void initListener()
	{
		super.initListener();
		mBtDriver.setOnClickListener(this);
		mBtUser.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		if(v == mBtDriver){
			
		}else if(v == mBtUser){
			startActivityAndFinish(UserHomeUI.class);
		}
	}
}
