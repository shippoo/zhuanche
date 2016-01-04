package com.baidu.zhuanche.ui.driver;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.SplashUI;
import com.baidu.zhuanche.adapter.QuhaoAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.base.BaseApplication;
import com.baidu.zhuanche.bean.User;
import com.baidu.zhuanche.bean.UserBean;
import com.baidu.zhuanche.conf.URLS;
import com.baidu.zhuanche.listener.MyAsyncResponseHandler;
import com.baidu.zhuanche.ui.user.UserRegistUI;
import com.baidu.zhuanche.ui.user.YuyueUI;
import com.baidu.zhuanche.utils.AsyncHttpClientUtil;
import com.baidu.zhuanche.utils.PrintUtils;
import com.baidu.zhuanche.utils.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.ui.driver
 * @类名:	DriverLoginUI
 * @创建者:	陈选文
 * @创建时间:	2015-12-29	上午9:37:22 
 * @描述:	TODO
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class DriverLoginUI extends BaseActivity implements OnClickListener
{
	
	private TextView		mTvQuhao;
	private EditText		mEtNumber;
	private EditText		mEtPassword;
	private CheckBox		mCbJizhu;
	private Button			mBtLogin;
	private TextView		mTvWangji;
	private TextView		mTvRegist;
	private String			mPassword;
	private LinearLayout	mContainerQuhao;
	private List<String>	mDatas;
	private QuhaoAdapter	mAdapter;
	private String			mNumber;
	private String			mQuhao;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driver_login);
		mTvQuhao = (TextView) findViewById(R.id.dl_tv_quhao);
		mEtNumber = (EditText) findViewById(R.id.dl_et_number);
		mEtPassword = (EditText) findViewById(R.id.dl_et_password);
		mCbJizhu = (CheckBox) findViewById(R.id.dl_cb_jizhu);
		mBtLogin = (Button) findViewById(R.id.dl_bt_login);
		mTvWangji = (TextView) findViewById(R.id.dl_tv_wangji);
		mTvRegist = (TextView) findViewById(R.id.dl_tv_regist);
		mContainerQuhao = (LinearLayout) findViewById(R.id.dl_container_quhao);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("登陆");
		/** 区号适配器数据 */
		mDatas = new ArrayList<String>();
		mDatas.add("+86");
		mDatas.add("+852");
		mAdapter = new QuhaoAdapter(this, mDatas);
		/** 初始化界面数据 */
		String lastQuhao = mSpUtils.getString("driver_quhao", mDatas.get(0));// 上次的区号
		String lastMobile = mSpUtils.getString("driver_mobile", "");// 上次的手机号
		String lastPassword = mSpUtils.getString("driver_password", ""); // 上次的密码
		mEtNumber.setText(lastMobile);
		mEtPassword.setText(lastPassword);
		if(!TextUtils.isEmpty(lastQuhao) && !TextUtils.isEmpty(lastMobile) && !TextUtils.isEmpty(lastPassword)){
			mCbJizhu.setChecked(true); 
		}
	}

	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mBtLogin.setOnClickListener(this);
		mTvRegist.setOnClickListener(this);
		mContainerQuhao.setOnClickListener(this);
		mTvWangji.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity(SplashUI.class);
		}
		else if (v == mBtLogin)
		{
			startActivityAndFinish(DriverHomeUI.class);
			//doClickLogin(); TODO
		}
		else if (v == mTvWangji)
		{
			startActivity(DriverFindPasswordUI.class);
		}
		else if (v == mTvRegist)
		{
			startActivity(DriverRegistUI.class);
		}
		else if (v == mContainerQuhao)
		{
			doClickQuhao();
		}
	}

	/** 区号选择 */
	private void doClickQuhao()
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setAdapter(mAdapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String quhao = (String) mAdapter.getItem(which);
				mTvQuhao.setText(quhao);
			}
		});
		builder.show();
	}

	/** 登陆 */
	private void doClickLogin()
	{
		mQuhao = mTvQuhao.getText().toString();
		mNumber = mEtNumber.getText().toString();
		mPassword = mEtPassword.getText().toString();
		if (TextUtils.isEmpty(mNumber))
		{
			ToastUtils.makeShortText(this, "请输入手机号码！");
			return;
		}
		if (TextUtils.isEmpty(mPassword))
		{
			ToastUtils.makeShortText(this, "请输入密码！");
			return;
		}
		ToastUtils.showProgress(this);
		String url = URLS.BASESERVER + URLS.Driver.login;
		AsyncHttpClient client = AsyncHttpClientUtil.getInstance();
		RequestParams params = new RequestParams();
		params.add("mobile", mNumber);
		params.add("password", mPassword);
		client.post(url, params, new MyAsyncResponseHandler() {
			@Override
			public void success(String json)
			{
				/** 是否保存账户信息 */
				if(mCbJizhu.isChecked()){
					mSpUtils.putString("driver_quhao", mQuhao);
					mSpUtils.putString("driver_mobile", mNumber);
					mSpUtils.putString("driver_password", mPassword);
				}else {
					mSpUtils.removeKey("driver_quhao");
					mSpUtils.removeKey("driver_mobile");
					mSpUtils.removeKey("driver_password");
				}
				processJson(json);
			}

		});

	}

	private void processJson(String json)
	{
		Gson gson = new Gson();
	}
}

