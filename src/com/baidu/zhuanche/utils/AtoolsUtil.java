package com.baidu.zhuanche.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @项目名: ZhuanChe
 * @包名: com.baidu.zhuanche.utils
 * @类名: AtoolsUtil
 * @创建者: 陈选文
 * @创建时间: 2015-12-3 下午7:35:14
 * @描述: 項目中常用方法抽取
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class AtoolsUtil
{
	/**
	 * 获取卡号尾数最后4位
	 * @param num
	 * @return
	 */
	public static String getEndCardNum(String num){
		int len = num.length();
		if(len <= 4){
			return num;
		}
		return num.substring(len - 4);
	}
	/**
	 * 将unix时间转化为Window时间
	 * @param time
	 * @return
	 */
	public static String unixTimeToLocalTime(String time){
		long t = Long.parseLong(time) * 1000;
		return DateFormatUtil.getDateTimeStr(new Date(t));
	}
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
	 * 
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

	/***
	 * 获取是否是专车或者顺风车
	 * 
	 * @param carpool
	 * @return
	 */
	public static String getCarPool(String carpool)
	{
		return carpool.equals("1") ? "顺风车" : "专车";
	}

	/***
	 * 获取司机端订单状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getDriverStatus(String status)
	{
		String text = "未知";
		if ("0".equals(status))
		{
			text = "预约中";
		}
		else if ("1".equals(status))
		{
			text = "已预约";
		}
		else if ("2".equals(status))
		{
			text = "已付款";
		}
		else if ("3".equals(status))
		{
			text = "已完成";
		}
		else if ("4".equals(status))
		{
			text = "已取消";
		}
		else if ("5".equals(status))
		{
			text = "已确认";
		}
		return text;
	}
}
