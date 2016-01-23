package com.baidu.zhuanche.listener;

import org.apache.http.Header;
import org.json.JSONException;

import com.baidu.zhuanche.utils.JsonUtils;
import com.baidu.zhuanche.utils.PrintUtils;
import com.baidu.zhuanche.utils.ToastUtils;
import com.baidu.zhuanche.utils.UIUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @项目名: 拼车
 * @包名: com.baidu.zhuanche.listener
 * @类名: MyAsyncResponseHandler
 * @创建者: 陈选文
 * @创建时间: 2015-12-23 下午5:31:55
 * @描述: TODO
 * 
 * @svn版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * @更新描述: TODO
 */
public abstract class MyAsyncResponseHandler extends AsyncHttpResponseHandler
{
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3)
	{
		failure();
	}

	public void failure()
	{
		ToastUtils.closeProgress();// 关闭进度条
		ToastUtils.makeShortText(UIUtils.getContext(), "请求失败！请检查网络！");
	}

	@Override
	public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
	{
		String json = new String(arg2);
		long code = -1;
		try
		{
			ToastUtils.closeProgress();// 关闭进度条
			PrintUtils.print("json =" + json);
			code = JsonUtils.getCode(json);
			if (0 != code)
			{
				if (-1 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "系统繁忙");
					return;
				}
				if (40000 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "参数错误！");
					return;
				}
				if (40001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "未登录或登陆信息已失效！");
					return;
				}
				if (40002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "密码错误！");
					return;
				}
				if (40003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "短信验证码错误！");
					return;
				}
				if (41000 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "缺少请求参数！");
					return;
				}
				if (41001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "手机号码为空！");
					return;
				}
				if (41002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "登陆密码为空！");
					return;
				}
				if (41003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "短信验证码为空！");
					return;
				}
				if (41004 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "access_token为空！");
					return;
				}
				if (41005 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "枚举类型为空！");
					return;
				}
				if (41006 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "起始地为空！");
					return;
				}
				if (41007 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "出发地为空！");
					return;
				}
				if (43001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "需要POST请求！");
					return;
				}
				if (43002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "参数长度过短！");
					return;
				}
				if (43003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "参数不再接收范围内！");
					return;
				}
				if (43004 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "文件格式错误！");
					return;
				}
				if (43005 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "时间不正确！");
					return;
				}
				if (43006 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "手机号码不合法！");
					return;
				}
				if (45001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "图片大小超过系统限制！");
					return;
				}
				if (46001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "用户不存在！");
					return;
				}
				if (46002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "短信验证码未获取或已失效！");
					return;
				}
				if (46003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "目录不存在！");
					return;
				}
				if (46004 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "订单号不存在！");
					return;
				}
				if (49001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "请勿重复评论！");
					return;
				}
				if (49002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "认证信息已存在！");
					return;
				}
				if(49003 == code){
					ToastUtils.makeShortText(UIUtils.getContext(), "数据重复提交！");
					return;
				}
				if(49005 == code){
					ToastUtils.makeShortText(UIUtils.getContext(), "你還有訂單未完成，請完成後再來接單！");
					return;
				}
				if (50001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "账号被禁用！");
					return;
				}
				if (50002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "手机号码已注册！");
					return;
				}
				if (50003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "账号在其他地方登陆！");
					return;
				}
				if (60001 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "系统错误！");
					return;
				}
				if (60002 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "短信接口错误！");
					return;
				}
				if (60003 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "图片接口错误！");
					return;
				}
				if (40 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "常见错误/参数不合法！");
					return;
				}
				if (41 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "缺少参数！");
					return;
				}
				if (42 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "请求超时！");
					return;
				}
				if (43 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "数据格式不正确！");
					return;
				}
				if (45 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "参数内容过大！");
					return;
				}
				if (46 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "不存在！");
					return;
				}
				if (47 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "数据解析失败！");
					return;
				}
				if (48 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "接口未授权！");
					return;
				}
				if (49 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "已存在！");
					return;
				}
				if (5 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "用户账号故障！");
					return;
				}
				if (6 == code)
				{
					ToastUtils.makeShortText(UIUtils.getContext(), "系统错误！");
					return;
				}
			}
			if (0 == code)
			{
				success(json);
			}
			else
			{
				ToastUtils.makeShortText(UIUtils.getContext(), "未知状态码错误！");
				return;
			}
		}
		catch (JSONException e)
		{
			ToastUtils.closeProgress();// 关闭进度条
			ToastUtils.makeShortText(UIUtils.getContext(), "JSON解析异常！");
			e.printStackTrace();
		}

	}

	public abstract void success(String json);
}
