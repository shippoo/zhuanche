package com.baidu.zhuanche.ui.driver;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.ui.driver
 * @类名:	IdentityCheckUI
 * @创建者:	陈选文
 * @创建时间:	2016-1-4	下午2:03:32 
 * @描述:	身份审核
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class IdentityCheckUI extends BaseActivity implements OnClickListener
{
	private RelativeLayout mContainerLevel;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_identity_check);
		mContainerLevel = (RelativeLayout) findViewById(R.id.ic_container_level);
	}
	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("身份审核");
	}
	@Override
	public void initListener()
	{
		mIvLeftHeader.setOnClickListener(this);
		mContainerLevel.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		if(v == mIvLeftHeader){
			finishActivity();
		}else if(v == mContainerLevel){
			doClickLevel();
		}
	}
	/**车型等级*/
	private void doClickLevel()
	{
		PopupWindow popupWindow = new PopupWindow(this);
		//popupWindow.setContentView(contentView);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
	}
	@Override
	public void onBackPressed()
	{
		finishActivity();
	}
}
