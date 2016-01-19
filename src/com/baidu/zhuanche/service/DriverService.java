package com.baidu.zhuanche.service;

import org.apache.http.Header;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.Driver;
import com.baidu.zhuanche.bean.DriverBean;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.bean.UserBean;
import com.baidu.zhuanche.conf.MyConstains;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.PrintUtils;
import com.baidu.zhuanche.utils.UIUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.service
 * @类名: MyService
 * @创建者: 陈选文
 * @创建时间: 2016-1-13 上午9:53:56
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DriverService extends Service
{
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					lianjieDriver();
					break;

				default:
					break;
			}
		}
		
	};
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
			public void run()
			{
				while (true)
				{
					SystemClock.sleep(MyConstains.TIME_PERIOD );
					mHandler.sendEmptyMessage(0);
				}
			}
			
		}).start();
	}

	public void lianjieDriver()
	{
		String url = URLS.BASESERVER + URLS.Driver.login;
		RequestParams params = new RequestParams();
		params.add("mobile", BaseApplication.getDriver().mobile);
		params.add("password", BaseApplication.getDriver().password);
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(UIUtils.getContext());
		client.post(url, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				String json = new String(arg2);
				Gson gson = new Gson();
				DriverBean driverBean = gson.fromJson(json, DriverBean.class);
				// 保存全局用戶信息
				String password = BaseApplication.getDriver().password;
				Driver driver = driverBean.content.driver_data;
				driver.password = password;
				driver.access_token = driverBean.content.access_token;
				BaseApplication.setDriver(driver);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3)
			{
			}
		});
	}
	

}
