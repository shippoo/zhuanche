package com.baidu.zhuanche.bean;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.bean
 * @类名: Yuyue
 * @创建者: 陈选文
 * @创建时间: 2015-12-26 下午6:45:52
 * @描述: 预约接口所需要的值
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class Yuyue
{
	/** 预约接口所需要的值 */
	public String	cartype;		// 级别
	public String	carpool;		// 类型
	public String	signtype;		// 签证类型
	public String	time;			// 时间
	public String	seaport;		// 港口
	public Location	getOnLocation = new Location();	// 上车地点
	public Location	getOffLocation = new Location(); // 下车地点
	public String   peopleCount;
	public String   xingliCount;
	public String   fee;
	public String   des;
	public String	budget;
}
