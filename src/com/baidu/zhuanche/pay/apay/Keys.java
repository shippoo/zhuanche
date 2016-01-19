/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.baidu.zhuanche.pay.apay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088411660193638";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "services@iasmall.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALqrlMEqxGw84ITRSeB2tfOUjZUrRoDIYeIMmYG+7iY9ToYJYlfJ9ozG6kLLCShbM+FTI5ienGacjTmdVZz51jhfcYHnU39Til3IidxvINZkdCsQWSubT9diBVvn13U0nPgNFMNbmHbyE40/Gc66xbRLTopGGyaysedqE70GR31ZAgMBAAECgYAKXLf3OR6KO57jAJMvSKe1C8wIOczCHV2BpN1onGav7LtNKXwyD14GVoxuhMLPYLCyk2DoefWIpKwaRKeZ/SxtAD5ladK/w7xL/R40G99VC/ykuOSPnYgWQ8PW6G3dNXp1CnPM4FP09UO23nxmMxVC8xmxwHocKwM0u18GRMBQGQJBAPYnFyg8Li2kU4+KvBVmWTnkCKjeReMcoRBBSkvpCbqPAGvAMw1PW7IycpbHDfDfCRqzL5AMFcGdk0tnvIcQlUMCQQDCI1D56FcGXoepAmrvgvlYCvKWHUWN//+VT+nnB8ckgpn572vYkjLytVNcPHfsa2GPIvbqtD7iNJNFHDHFQqszAkAw5Q7qhRm/Izcd+jbmIVBz+WHm+U/0jwlHS1DQx1eRYTp6LNF3QV47hV3RqXSRdhw+230FJgFkVkKd5dtDEP41AkADKSYJW5IHJYTZ6JMrIRvEJjF65jEatb9IhAuP2l2Qp5uwKQi9duvjbbZUuxtMuxbUiMIyYgrgDfET3/ijeIlnAkAXR4yX+17+bP7HVbO+uPZqvqyXjQWN4ap8eQR74wOgMXoDwGq8OyJpj7d7qd0OjCJi0m0PTrKJIYwsiU09V5Fm";
	

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
