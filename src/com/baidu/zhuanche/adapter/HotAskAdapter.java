package com.baidu.zhuanche.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.MyBaseApdater;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.adapter
 * @类名: HotAskAdapter
 * @创建者: 陈选文
 * @创建时间: 2015-12-28 下午5:41:01
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class HotAskAdapter extends MyBaseApdater<String>
{

	

	public HotAskAdapter(Context context, List<String> dataSource) {
		super(context, dataSource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HotAskViewHolder holder = null;
		if (convertView == null)
		{
			holder = new HotAskViewHolder();
			convertView = View.inflate(mContext, R.layout.item_user_home_listview, null);
			convertView.setTag(holder);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_user_home_iv_icon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.item_user_home_tv_title);
			holder.tvText = (TextView) convertView.findViewById(R.id.item_user_home_tv_text);
		}
		else
		{
			holder = (HotAskViewHolder) convertView.getTag();
		}

		return convertView;
	}
}

class HotAskViewHolder
{
	ImageView	ivIcon;
	TextView	tvTitle;
	TextView	tvText;
}
