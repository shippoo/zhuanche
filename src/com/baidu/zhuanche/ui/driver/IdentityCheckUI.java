package com.baidu.zhuanche.ui.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.zhuanche.R;
import com.baidu.zhuanche.adapter.DialogDriverAdapter;
import com.baidu.zhuanche.base.BaseActivity;
import com.baidu.zhuanche.utils.PhotoUtilChange;
import com.baidu.zhuanche.view.SelectPicPopupWindow;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.ui.driver
 * @类名: IdentityCheckUI
 * @创建者: 陈选文
 * @创建时间: 2016-1-4 下午2:03:32
 * @描述: 身份审核
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public class IdentityCheckUI extends BaseActivity implements OnClickListener
{
	private Dialog					mDialog;										// 底部对话框
	private int						selectedLevelPosition	= 0;					// 默认选中位置
	private Button					mBtOK;
	private Button					mBtCancel;
	private ListView				mListView;
	private List<String>			mLevelDatas;									// 等级数据
	private SelectPicPopupWindow	mPopupWindow;
	private RelativeLayout			mContainerLevel;
	/* 审核的三个状态图标 */
	private ImageView				mIvStatusCheck;
	private ImageView				mIvStatusChecking;
	private ImageView				mIvStatusChecked;
	private static String			path					= "/sdcard/myHead/";	// sd路径
	/* 审核数据 */
	private TextView				mTvLevel;										// 等级
	private EditText				mEtCarpool;									// 车型
	private EditText				mEtCarNum;										// 车牌号
	private ImageView				mIvCarNum;										// 车牌号照片
	private ImageView				mIvZjzh;										// 准驾证号照片
	private ImageView				mIvIdcard;										// 身份证
	private Bitmap					carnum;										// 车牌号Bitmap
	private Bitmap					zjzh;											// 准驾证号Bitmap
	private Bitmap					idcard;										// 身份证Bitmap

	@Override
	public void initView()
	{
		setContentView(R.layout.ui_identity_check);
		mIvStatusCheck = (ImageView) findViewById(R.id.ic_iv_status01);
		mIvStatusChecking = (ImageView) findViewById(R.id.ic_iv_status02);
		mIvStatusChecked = (ImageView) findViewById(R.id.ic_iv_status03);
		mContainerLevel = (RelativeLayout) findViewById(R.id.ic_container_level);
		mTvLevel = (TextView) findViewById(R.id.ic_tv_carlevel);
		mEtCarpool = (EditText) findViewById(R.id.ic_et_carpool);
		mEtCarNum = (EditText) findViewById(R.id.ic_et_carnum);
		mIvCarNum = (ImageView) findViewById(R.id.ic_iv_carpai);
		mIvZjzh = (ImageView) findViewById(R.id.ic_iv_zjzh);
		mIvIdcard = (ImageView) findViewById(R.id.ic_iv_idcard);
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
		mIvCarNum.setOnClickListener(this);
		mIvZjzh.setOnClickListener(this);
		mIvIdcard.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == mIvLeftHeader)
		{
			finishActivity();
		}
		else if (v == mContainerLevel)
		{
			showLevelDialog();
		}
		else if (v == mBtCancel)
		{
			dismiss();
		}
		else if (v == mBtOK)
		{
			dismiss();
			mTvLevel.setText(mLevelDatas.get(selectedLevelPosition));
		}
		else if (v == mIvCarNum)
		{
			setOnModifyCarNumIcon(mIvCarNum, "carnum.jpg");
		}
		else if (v == mIvZjzh)
		{
			setOnModifyZjzhIcon(mIvZjzh, "zjzh.jpg");
		}
		else if (v == mIvIdcard)
		{
			setOnModifyIdCardIcon(mIvIdcard, "idcard.jpg");
		}
	}

	/** 车型等级对话框 */
	private void showLevelDialog()
	{

		if (mDialog == null)
		{
			mDialog = new Dialog(this, R.style.CustomBottomDialog);
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(false);
			// 获取对话框，并设置窗口参数
			Window window = mDialog.getWindow();
			LayoutParams params = new LayoutParams();
			// 不能写成这样,否则Dialog显示不出来
			// LayoutParams params = new
			// LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			// 对话框窗口的宽和高
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.WRAP_CONTENT;
			// 对话框显示的位置
			params.x = 120;
			params.y = 350;
			window.setAttributes(params);
			// 设置对话框布局
			mDialog.setContentView(R.layout.layout_dialog);
			mBtCancel = (Button) mDialog.findViewById(R.id.dialog_cancel);
			mBtOK = (Button) mDialog.findViewById(R.id.dialog_ok);
			mListView = (ListView) mDialog.findViewById(R.id.dialog_listview);
			// 设置点击事件
			mBtCancel.setOnClickListener(this);
			mBtOK.setOnClickListener(this);
			mLevelDatas = new ArrayList<String>();
			// TODO 模拟加载数据
			mLevelDatas.add("五人豪华型");
			mLevelDatas.add("五人普通型");
			mLevelDatas.add("七人豪华型");
			mLevelDatas.add("七人普通型");
			mListView.setAdapter(new DialogDriverAdapter(this, mLevelDatas));
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					view.setSelected(true);
					selectedLevelPosition = position;
				}
			});
		}
		mDialog.show();

	}

	@Override
	public void onBackPressed()
	{
		finishActivity();
	}

	/**
	 * 隐藏对话框
	 */
	public void dismiss()
	{
		// 隐藏对话框之前先判断对话框是否存在，以及是否正在显示
		if (mDialog != null && mDialog.isShowing())
		{
			mDialog.dismiss();
			mDialog = null;
		}
	}

	/**
	 * 身份证拍照
	 * 
	 * @param iv
	 * @param photoName
	 *            照片名称
	 */
	public void setOnModifyIdCardIcon(ImageView iv, final String photoName)
	{
		mPopupWindow = PhotoUtilChange.getPicPopupWindow(this, new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// 点击按钮,框就消失
				if (mPopupWindow != null)
				{
					mPopupWindow.dismiss();
				}
				switch (v.getId())
				{
					case R.id.btn_cancel:
						break;
					case R.id.btn_take_photo:// 拍照
						Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), photoName)));
						startActivityForResult(intent2, 21);// 采用ForResult打开
						break;
					case R.id.btn_pick_photo: // 从相册取照片
						Intent intent1 = new Intent(Intent.ACTION_PICK, null);
						intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent1, 20);
						break;
					default:
						break;
				}
			}
		}, iv);

	}

	/**
	 * 准驾证号拍照
	 * 
	 * @param iv
	 * @param photoName
	 *            照片名称
	 */
	public void setOnModifyZjzhIcon(ImageView iv, final String photoName)
	{
		mPopupWindow = PhotoUtilChange.getPicPopupWindow(this, new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// 点击按钮,框就消失
				if (mPopupWindow != null)
				{
					mPopupWindow.dismiss();
				}
				switch (v.getId())
				{
					case R.id.btn_cancel:
						break;
					case R.id.btn_take_photo:// 拍照
						Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), photoName)));
						startActivityForResult(intent2, 11);// 采用ForResult打开
						break;
					case R.id.btn_pick_photo: // 从相册取照片
						Intent intent1 = new Intent(Intent.ACTION_PICK, null);
						intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent1, 10);
						break;
					default:
						break;
				}
			}
		}, iv);

	}

	/**
	 * 车牌照拍照
	 * 
	 * @param iv
	 * @param photoName
	 *            照片名称
	 */
	public void setOnModifyCarNumIcon(ImageView iv, final String photoName)
	{
		mPopupWindow = PhotoUtilChange.getPicPopupWindow(this, new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// 点击按钮,框就消失
				if (mPopupWindow != null)
				{
					mPopupWindow.dismiss();
				}
				switch (v.getId())
				{
					case R.id.btn_cancel:
						break;
					case R.id.btn_take_photo:// 拍照
						Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), photoName)));
						startActivityForResult(intent2, 2);// 采用ForResult打开
						break;
					case R.id.btn_pick_photo: // 从相册取照片
						Intent intent1 = new Intent(Intent.ACTION_PICK, null);
						intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						startActivityForResult(intent1, 1);
						break;
					default:
						break;
				}
			}
		}, iv);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			/* 车牌号 */
			case 1:
				if (resultCode == RESULT_OK) // 从相册取
				{
					cropPhoto(data.getData(), 3);// 裁剪图片
				}
				break;
			case 2:
				if (resultCode == RESULT_OK) // 拍照
				{
					File temp = new File(Environment.getExternalStorageDirectory() + "/carnum.jpg");
					cropPhoto(Uri.fromFile(temp), 3);// 裁剪图片
				}
				break;
			case 3: // 车牌号照片
				if (data != null)
				{
					Bundle extras = data.getExtras();
					carnum = extras.getParcelable("data");
					if (carnum != null)
					{
						setPicToView(carnum, "carnum.jpg");// 保存在SD卡中
						mIvCarNum.setImageBitmap(carnum);// 用ImageView显示出来
					}
				}
				break;

			/* 准驾证号 */
			case 10:
				if (resultCode == RESULT_OK) // 从相册取
				{
					cropPhoto(data.getData(), 12);// 裁剪图片
				}
				break;
			case 11:
				if (resultCode == RESULT_OK) // 拍照
				{
					File temp = new File(Environment.getExternalStorageDirectory() + "/zjzh.jpg");
					cropPhoto(Uri.fromFile(temp), 12);// 裁剪图片
				}
				break;
			case 12: // 准驾照片
				if (data != null)
				{
					Bundle extras = data.getExtras();
					zjzh = extras.getParcelable("data");
					if (zjzh != null)
					{
						setPicToView(zjzh, "zjzh.jpg");// 保存在SD卡中
						mIvZjzh.setImageBitmap(zjzh);// 用ImageView显示出来
					}
				}
				break;
			/* 身份证号 */
			case 20:
				if (resultCode == RESULT_OK) // 从相册取
				{
					cropPhoto(data.getData(), 22);// 裁剪图片
				}
				break;
			case 21:
				if (resultCode == RESULT_OK) // 拍照
				{
					File temp = new File(Environment.getExternalStorageDirectory() + "/idcard.jpg");
					cropPhoto(Uri.fromFile(temp), 22);// 裁剪图片
				}
				break;
			case 22: // 身份证照片
				if (data != null)
				{
					Bundle extras = data.getExtras();
					idcard = extras.getParcelable("data");
					if (idcard != null)
					{
						setPicToView(idcard, "idcard.jpg");// 保存在SD卡中
						mIvIdcard.setImageBitmap(idcard);// 用ImageView显示出来
					}
				}
				break;
			default:
				break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 调用系统的裁剪
	 * 
	 * @param uri
	 */
	public void cropPhoto(Uri uri, int requestCode)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	/** 设置图片 */
	private void setPicToView(Bitmap bitmap, String photName)
	{
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
		{ // 检测sd是否可用
			return;
		}
		FileOutputStream b = null;
		File file = new File(path);
		file.mkdirs();// 创建文件夹
		String fileName = path + photName;// 图片名字
		try
		{
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// 关闭流
				b.flush();
				b.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	}

}