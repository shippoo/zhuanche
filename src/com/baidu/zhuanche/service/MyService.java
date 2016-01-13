package com.baidu.zhuanche.service;

import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.Driver;
import com.baidu.zhuanche.bean.DriverBean;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.PrintUtils;
import com.baidu.zhuanche.utils.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.service
 * @类名:	MyService
 * @创建者:	陈选文
 * @创建时间:	2016-1-13	上午9:53:56 
 * @描述:	TODO
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class MyService extends Service
{
	
	@Override
	public IBinder onBind(Intent intent)
	{
		
		return null;
	}
	@Override
	public void onCreate()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run(){
			}
		}).start();
	}
	/**
	 * 获取Android手机唯一标志串
	 * 
	 * @return DEVICE_ID
	 */
	public String getDeviceId()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		PrintUtils.print("设备id=" + DEVICE_ID);
		return DEVICE_ID;
	}
}
