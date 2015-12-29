package com.baidu.zhuanche.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.UserMsgAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.bean.Msg;
import com.baidu.zhuanche.utils.DateFormatUtil;
import com.baidu.zhuanche.utils.ToastUtils;
import com.baidu.zhuanche.utils.UIUtils;

/**
 * 
 * @项目名: ZhuanChe
 * @包名: com.baidu.zhuanche.activity
 * @类名: MyMessageActivity
 * @创建者: 陈选文
 * @创建时间: 2015-12-21 上午10:09:06
 * @描述: 我的消息界面
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class UserMessageUI extends BaseActivity implements OnClickListener, OnItemClickListener
{
	private ListView		mListView;
	private List<Msg>		mDatas;
	private UserMsgAdapter	mMsgAdapter;
	private TextView		mTvEmptyMsg;
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_user_message);
		mListView = (ListView) findViewById(R.id.msg_listview);
		mTvEmptyMsg = (TextView) findViewById(R.id.msg_emptymsg);
	}

	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("我的消息");
		mIvRightHeader.setImageResource(R.drawable.delete);
		mIvRightHeader.setVisibility(0);
		/**
		 * 模拟数据 TODO
		 */
		mDatas = new ArrayList<Msg>();
		ToastUtils.showProgress(this);
		mTvEmptyMsg.setVisibility(0);
		for (int i = 0; i < 50; i++)
		{
			Msg msg = new Msg();
			msg.msg = "你的预约已经成功！赶快去查查吧！";
			msg.date = DateFormatUtil.getDateTimeStr();
			mDatas.add(msg);
		}
		mMsgAdapter = new UserMsgAdapter(this, mDatas);
		mListView.setAdapter(mMsgAdapter);
		mTvEmptyMsg.setVisibility(8);
		ToastUtils.closeProgress();
	}
	
	@Override
	public void initListener()
	{
		super.initListener();
		mIvLeftHeader.setOnClickListener(this);
		mIvRightHeader.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mIvRightHeader)
		{
			mDatas.clear();
			mMsgAdapter.notifyDataSetChanged();
			mTvEmptyMsg.setVisibility(0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		ToastUtils.makeShortText(UIUtils.getContext(), "你点击了第" + position + "条消息");
	}

}
