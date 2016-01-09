package com.baidu.zhuanche.ui.driver;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.utils.ToastUtils;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.driver
 * @类名: DriverFindPasswordUI
 * @创建者: 陈选文
 * @创建时间: 2015-12-29 上午10:13:30
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class DriverFindPasswordNextUI extends BaseActivity implements OnClickListener
{
	private EditText	mEtPassword;
	private EditText	mEtRePassword;
	private Button		mBtConfirm;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driver_findpassword_next);
		mEtPassword = (EditText) findViewById(R.id.df_next_et_password);
		mEtRePassword = (EditText) findViewById(R.id.df_next_et_repassword);
		mBtConfirm = (Button) findViewById(R.id.df_next_bt_cofirm);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("找回密码");
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mBtConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}else if(v == mBtConfirm){
			doClickConfirm();
		}
	}
	/**找回密码*/
	private void doClickConfirm()
	{
		String pwd = mEtPassword.getText().toString();
		String repwd = mEtRePassword.getText().toString();
		if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(repwd)){
			ToastUtils.makeShortText(this, "请完善好信息！");
			return;
		}
		if(!pwd.equals(repwd)){
			ToastUtils.makeShortText(this, "两次输入密码不一致！");
			return;
		}
		ToastUtils.makeShortText(this, "找回密码"); //TODO
	}

}
