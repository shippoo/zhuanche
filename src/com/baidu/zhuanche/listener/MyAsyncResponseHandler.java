package com.baidu.zhuanche.listener;

import org.apache.http.Header;
import org.json.JSONException;

import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.utils.JsonUtils;
import com.baidu.zhuanche.utils.PrintUtils;
import com.baidu.zhuanche.utils.ToastUtils;
import com.baidu.zhuanche.utils.UIUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.listener
 * @类名:	MyAsyncResponseHandler
 * @创建者:	陈选文
 * @创建时间:	2015-12-23	下午5:31:55 
 * @描述:	TODO
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public abstract class MyAsyncResponseHandler extends AsyncHttpResponseHandler
{
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3)
	{
		failure();
	}
	public void failure()
	{
		ToastUtils.closeProgress();//关闭进度条
		ToastUtils.makeShortText(UIUtils.getContext(), "请求失败！请检查网络！");
	}
	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
	{
		String json = new String(arg2);
		long code = -1;
		try
		{
			ToastUtils.closeProgress();//关闭进度条
			code = JsonUtils.getCode(json);
			PrintUtils.print("json =" + json);
			if (0 != code)
			{
				ToastUtils.makeShortText(UIUtils.getContext(), "请求不成功！");
				return;
			}
			success(json);
		}
		catch (JSONException e)
		{
			ToastUtils.closeProgress();//关闭进度条
			ToastUtils.makeShortText(UIUtils.getContext(), "JSON解析异常！");
			e.printStackTrace();
		}
		
	}
	public abstract void success(String json);
}
