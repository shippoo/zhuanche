package com.baidu.zhuanche.ui.user;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.utils.ToastUtils;


/**
 * @项目名: 	拼车
 * @包名:	com.baidu.zhuanche.ui.user
 * @类名:	NewsDetailUI
 * @创建者:	陈选文
 * @创建时间:	2016-1-7	下午6:13:37 
 * @描述:	新闻详情
 * 
 * @svn版本:	$Rev$
 * @更新人:	$Author$
 * @更新时间:	$Date$
 * @更新描述:	TODO
 */
public class NewsDetailUI extends BaseActivity implements OnClickListener
{
	private WebView mWebView;
	
	@Override
	public void initView()
	{
		setContentView(R.layout.ui_newsdetail);
		mWebView = (WebView) findViewById(R.id.newsdetail_webview);
	}
	@Override
	public void initData()
	{
		super.initData();
		mTvTitle.setText("新闻详情");
		
		mWebView.loadUrl("https://www.baidu.com/");
		mWebView.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				ToastUtils.showProgress(NewsDetailUI.this);
			}

			@Override
			public void onPageFinished(WebView view, String url)
			{
				ToastUtils.closeProgress();
			}
			
		});
	}
	@Override
	public void initListener()
	{
		mIvLeftHeader.setOnClickListener(this);
	}
	@Override
	public void onClick(View v)
	{
		if(v == mIvLeftHeader){
			finishActivity();
		}
	}
	@Override
	public void onBackPressed()
	{
		finishActivity();
	}
}
