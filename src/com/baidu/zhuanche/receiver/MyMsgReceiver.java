package com.baidu.zhuanche.receiver;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.baidu.zhuanche.utils.ExampleUtil;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.receiver
 * @类名:	MyMsgReceiver
 * @创建者:	陈选文
 * @创建时间:	2016-1-12	下午2:29:59 
 * @描述:	极光推送消息接受者
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class MyMsgReceiver extends BroadcastReceiver
{

	private static final String TAG = "tylz";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		/*收到了自定义消息*/
		if(action.equals("cn.jpush.android.intent.MESSAGE_RECEIVED")){
			Bundle bundle = intent.getExtras();
			String title = bundle.getString(JPushInterface.EXTRA_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		}
	}

}
