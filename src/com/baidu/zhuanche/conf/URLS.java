package com.baidu.zhuanche.conf;

/**
 * @项目名: ZhuanChe
 * @包名: com.baidu.zhuanche.utils
 * @类名: URLS
 * @创建者: 陈选文
 * @创建时间: 2015-12-3 下午2:00:07
 * @描述: 接口地址
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public interface URLS
{
	String	BASE		= "http://gjxc.fanc.com.cn";
	// String BASE = "http://192.168.1.12";
	/**
	 * 基地址
	 */
	// String BASESERVER = BASE + "/pinche/index.php/";
	String	BASESERVER	= BASE + "/index.php/";

	/**
	 * 
	 * @描述:用户模块接口
	 * 
	 */
	interface User
	{
		String	login				= "User/Login/login";
		String	register			= "User/Login/register";
		String	verify				= "User/Login/verify";
		String	enum_				= "User/User/enum";
		String	orderlist			= "User/User/orderList";		// 订单列表
		String	logout				= "User/Login/logout";
		String	findPassword		= "User/Login/findPassword";
		String	modifyIcon			= "User/User/modifyIcon";		// 修改 头像
		String	modifyUser			= "User/User/modifyUser";
		String	hotCity				= "User/User/hotCity";			// 热门城市
		String	addFee				= "User/User/addFee";
		String	makeOrder			= "User/User/makeOrder";		// 用戶预约接口
		String	index				= "User/Login/index";
		String	artilce_list		= "User/Login/artilce_list";
		String	myMessage			= "User/User/myMessage";
		String	cleanMessage		= "User/User/cleanMessage";
		String	orderCancel			= "User/User/orderCancel";
		String	addDriverComment	= "User/User/addDriverComment";
		String	commentDetail		= "User/User/commentDetail";
		String	getSeaport			= "User/User/getSeaport";
		String	budget				= "User/User/budget";
		String	article_detail		= "User/Login/article_detail";
		String	payment				= "User/Order/payment";
		// http://192.168.1.12/pinche/index.php/User/Login/article_detail
	}

	/**
	 * 
	 * @描述: 司机模块
	 * 
	 * @svn版本: $Rev$
	 */
	public interface Driver
	{
		String	login				= "Driver/Login/login";
		String	regist				= "Driver/Login/register";
		String	verify				= "Driver/Login/verify";
		String	todayMoney			= "Driver/Driver/todayMoney";
		String	myBalance			= "Driver/Driver/myBalance";
		String	historyMoney		= "Driver/Driver/historyMoney";
		String	myWithdraw			= "Driver/Driver/myWithdraw";
		String	todayMoneyList		= "Driver/Driver/todayMoneyList";
		String	historyMoneyList	= "Driver/Driver/historyMoneyList";
		String	withdrawApply		= "Driver/Driver/withdrawApply";
		String	allComment			= "Driver/Driver/allComment";
		String	orderList			= "Driver/Driver/orderList";
		String	cleanMessage		= "Driver/Driver/cleanMessage";
		String	driverMessage		= "Driver/Driver/driverMessage";
		String	enum_				= "Driver/Driver/enum";			// 枚舉值接口
		String	driverVerify		= "Driver/Driver/driverVerify";
		String	showVerifyInfo		= "Driver/Driver/showVerifyInfo";
		String	modifyDriver		= "Driver/Driver/modifyDriver";
		String	getModify			= "Driver/Driver/getModify";
		String	pushMessage			= "Driver/Driver/pushMessage";
		String	getorderList		= "Driver/Driver/getorderList";
		String	receiveOrder		= "Driver/Driver/receiveOrder";
		String	findPassword		= "Driver/Login/findPassword";
		String	orderDetail			= "Driver/Driver/orderDetail";
		String	cleanWithdraw		= "Driver/Driver/cleanWithdraw";
		String	finished			= "Driver/Driver/finished";
	}

	String	MOBILE			= "mobile";		// 手机号码
	String	PASSWORD		= "password";		// 密码
	String	VERIFY_CODE		= "verify";		// 验证码
	String	ACCESS_TOKEN	= "access_token";	// 全局token
	String	SEAPORT			= "seaport";		// 港口
	String	CARTYPE			= "cartype";		// 车辆级别
	String	COMMENT			= "comment";
	String	ICON			= "icon";
	String	CURRENTPAGER	= "currentpager";	// 当前页
	String	TYPE			= "type";			// 枚舉值類型
	String	CARPOOL			= "carpool";
}
