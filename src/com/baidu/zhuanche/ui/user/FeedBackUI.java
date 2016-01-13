package com.baidu.zhuanche.ui.user;

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
 * @类名: FeedBackUI
 * @创建者: 陈选文
 * @创建时间: 2016-1-9 上午11:46:09
 * @描述: 司机端意见反馈
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class FeedBackUI extends BaseActivity implements OnClickListener
{
	private EditText	mEtSuggestion;
	private EditText	mEtMail;
	private Button		mBtSubmit;

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_driver_feedback);
		mEtMail = (EditText) findViewById(R.id.feedback_et_email);
		mEtSuggestion = (EditText) findViewById(R.id.feedback_et_suggestion);
		mBtSubmit = (Button) findViewById(R.id.feedback_bt_submit);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("用户反馈");
	}

	@Override
	public void initListener()
	{
		mIvLeftHeader.setOnClickListener(this);
		mBtSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mBtSubmit)
		{
			if(TextUtils.isEmpty(mEtSuggestion.getText().toString())){
				ToastUtils.makeShortText(this, "请输入你的宝贵意见！");
				return;
			}
			ToastUtils.makeShortText(this, "非常感谢你提供的宝贵意见，我们会尽快处理！");
			finishActivity();
		}
	}

	@Override
	public void onBackPressed()
	{
		finishActivity();
	}
}
