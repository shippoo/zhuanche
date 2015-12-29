package com.baidu.zhuanche.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.utils.UIUtils;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.adapter
 * @类名: HomeGridAdapter
 * @创建者: 陈选文
 * @创建时间: 2015-12-28 下午7:07:20
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class HomeGridAdapter extends BaseAdapter
{
	private Context			mContext;
	private List<Integer>	mDatas;

	public HomeGridAdapter(Context context, List<Integer> datas) {
		mContext = context;
		mDatas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HomeGridViewHolder holder = null;
		if (convertView == null)
		{
			holder = new HomeGridViewHolder();
			convertView = View.inflate(UIUtils.getContext(), R.layout.item_user_home_gridview, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.item_uh_gridview_pic);
			holder.tv = (TextView) convertView.findViewById(R.id.item_uh_gridview_name);
			convertView.setTag(holder);
		}
		else
		{
			holder = (HomeGridViewHolder) convertView.getTag();
		}
		Integer i = (Integer) getItem(position);
		holder.iv.setImageResource(i);
		return convertView;
	}

	@Override
	public int getCount()
	{
		if(mDatas != null){
			return mDatas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		if(mDatas != null){
			return mDatas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

}

class HomeGridViewHolder
{
	ImageView	iv;
	TextView  	tv;
}
