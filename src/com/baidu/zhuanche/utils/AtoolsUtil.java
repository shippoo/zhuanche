package com.baidu.zhuanche.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.text.TextUtils;

import com.baidu.zhuanche.conf.MyConstains;
import com.loopj.android.http.RequestParams;

/**
 * @项目名: ZhuanChe
 * @包名: com.baidu.zhuanche.utils
 * @类名: AtoolsUtil
 * @创建者: 陈选文
 * @创建时间: 2015-12-3 下午7:35:14
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class AtoolsUtil
{
	/**
	 * 隐藏手机号中间4位
	 * 
	 * @param phone2
	 * @return 返回例如 155****4398
	 */
	public static String mobile4(String phone2)
	{
		return phone2.substring(0, 3) + "****" + phone2.substring(7, phone2.length());
	}
	/**
	 * 字节流转化为字符串
	 * @param is
	 * @return
	 */
	public static String streamToString(InputStream is)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (is != null)
		{
			int len = -1;
			byte[] buf = new byte[1024];
			try
			{
				while ((len = is.read(buf)) != -1)
				{
					bos.write(buf, 0, len);
				}
				return bos.toString();
			}
			catch (IOException e)
			{
				e.printStackTrace();

			}
			finally
			{
				if (is != null)
				{
					try
					{
						is.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
}
