package com.baidu.zhuanche.base;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.utils.SPUtils;

/**
 * @创建者 Administrator
 * @创时间 2015-11-17 下午4:42:45
 * @描述 TODO
 * 
 * @版本 $Rev: 64 $
 * @更新者 $Author: admin $
 * @更新时间 $Date: 2015-11-17 16:56:48 +0800 (星期二, 17 十一月 2015) $
 * @更新描述 TODO
 */
public abstract class BaseActivity extends Activity
{
	/**
	 * 1.不用关心相关的生命周期方法,只需要关心自己定义的方法即可 2.可以决定哪些方法是必须实现,哪些方法是选择性实现
	 * 3.放置共有的属性或者方法-->减少代码的书写
	 */
	// activity的完全退出
	public static LinkedList<BaseActivity>	allActivitys	= new LinkedList<BaseActivity>();

	// 再按一次退出应用程序
	private long							mPreClickTime;
	// 属性
	public SPUtils							mSpUtils;

	public TextView							mTvTitle;											// 头标题
	public ImageView						mIvLeftHeader;										// 左菜单
	public ImageView						mIvRightHeader;										// 右菜单

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mSpUtils = new SPUtils(this);

		init();
		initView();
		initActionBar();
		initData();
		initListener();
	}

	@Override
	protected void onResume()
	{
		allActivitys.add(this);
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		allActivitys.remove(this);
		super.onDestroy();
	}

	public void init()
	{
		// TODO

	}

	public abstract void initView();

	public void initActionBar()
	{
		// TODO

	}

	public void initData()
	{
		// TODO
		mTvTitle = (TextView) findViewById(R.id.header_title);
		mIvLeftHeader = (ImageView) findViewById(R.id.header_leftMenu);
		mIvRightHeader = (ImageView) findViewById(R.id.header_rightMenu);
	}

	public void initListener()
	{
		// TODO

	}

	/**
	 * 完全退出
	 */
	public void exit()
	{
		BaseApplication.setUser(new User());
		for (BaseActivity activity : allActivitys)
		{
			activity.finish();
		}
	}

	@Override
	public void onBackPressed()
	{
		if (this instanceof BaseActivity)
		{
			if (System.currentTimeMillis() - mPreClickTime > 2000)
			{// 两次连续点击的时间间隔>2s
				Toast.makeText(getApplicationContext(), "再按一次,退出应用程序", 0).show();
				mPreClickTime = System.currentTimeMillis();
				return;
			}
			else
			{// 点的快
				exit();// 完全退出
			}
		}
		else
		{
			super.onBackPressed();// finish
		}
	}

	/**
	 * 开启一个新界面,并且结束当前界面
	 * 
	 * @param clazz
	 */
	public void startActivityAndFinish(Class clazz)
	{
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
	}

	/**
	 * 开启一个新界面
	 * 
	 * @param clazz
	 */
	public void startActivity(Class clazz)
	{
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
	}
	
	/**
	 * 结束当前界面
	 * 
	 * @param clazz
	 */
	public void finishActivity()
	{
		finish();
		overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
	}
	/**
	 * 开启一个新界面,并且结束当前界面
	 * 
	 * @param clazz
	 */
	public void finishActivity(Class clazz)
	{
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_enter, R.anim.pre_exit);
	}
}
