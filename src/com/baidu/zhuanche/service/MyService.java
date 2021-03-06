package com.baidu.zhuanche.service;

import org.apache.http.Header;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import com.baidu.zhuanche.base.BaseApplication;
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
public class MyService extends Service
{
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					lianjieUser();
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

	public void lianjieUser()
	{
		String url = URLS.BASESERVER + URLS.User.login;
		RequestParams params = new RequestParams();
		params.add("mobile", BaseApplication.getUser().mobile);
		params.add("password", BaseApplication.getUser().password);
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance(UIUtils.getContext());
		client.post(url, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
			{
				String json = new String(arg2);
				Gson gson = new Gson();
				UserBean userBean = gson.fromJson(json, UserBean.class);
				// 保存全局用戶信息
				PrintUtils.print("后台1 = " + BaseApplication.getUser().access_token);
				String password = BaseApplication.getUser().password;
				User user = userBean.content.member_data;
				user.password = password;
				user.access_token = userBean.content.access_token;
				
				BaseApplication.setUser(user);
				PrintUtils.print("后台2 = " + userBean.content.access_token);
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3)
			{
			}
		});
	}
	

}
