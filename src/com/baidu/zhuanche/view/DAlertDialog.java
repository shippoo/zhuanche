package com.baidu.zhuanche.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.zhuanche.R;

public class DAlertDialog extends Dialog 
{
	private Context mContext;
	public DAlertDialog(Context context) {
		super(context, R.style.dialog_style_comment);
		mContext = context;
	}

	public DAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_alert_progress);

//		Window window = this.getWindow();
//		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		WindowManager m = ((Activity) mContext).getWindowManager();
//		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//		p.width = d.getWidth();
//		window.setAttributes(p);

	}

	
}
