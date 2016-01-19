package com.baidu.zhuanche.utils;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.utils
 * @类名: AsyncHttpCilentUtil
 * @创建者: 陈选文
 * @创建时间: 2015-12-24 下午3:08:56
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class AsyncHttpClientUtil
{
	private static AsyncHttpClient	client;

	public synchronized static AsyncHttpClient getInstance(Context paramContext)
	{
		if (client == null)
		{
			synchronized (AsyncHttpClientUtil.class)
			{
				client = new AsyncHttpClient();
				PersistentCookieStore myCookieStore = new PersistentCookieStore(paramContext);
				client.setCookieStore(myCookieStore);
			}

		}
		return client;
	}

	public static AsyncHttpClient getInstance()
	{
//		if (client == null)
//		{
//			client = new AsyncHttpClient();
//			PersistentCookieStore myCookieStore = new PersistentCookieStore(UIUtils.getContext());
//			client.setCookieStore(myCookieStore);
//		}
		
		return getInstance(UIUtils.getContext());
	}
}
