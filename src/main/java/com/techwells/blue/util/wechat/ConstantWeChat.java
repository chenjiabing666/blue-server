package com.techwells.blue.util.wechat;

/**
 * 全局变量
 * 
 * @author shone
 * 
 */
public class ConstantWeChat {
	public static final int AGENTID = 1;
	public static final String TOKEN = "qiaodiao2016";
	public static final String APPID = "wxa63897b9819e4171"; // appId
	public static final String SECRET = "40e860aabe090259a67ae3940cd0c1dd"; // 密钥
	public static final String MCH_ID = "1517496741";// 商户号
	public static final String BODY = "蓝色按钮-下单";// body支付描述
	public static final String PRIVATE_KEY = "abcdefghijkopqrstuvwxyz123456789"; // 商户私钥
	public static final String TRADE_TYPE = "NATIVE";// 交易类型，这里是扫码支付
	public static final String NOTIFY_URL = "http://www.emoonbow.com:8081/blue-server/wechat/receiveNotifyUrl.do";// 回调地址

	public static final String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一支付接口地址

	public static final String WXREFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";// 微信退款申请接口

	public final static String SUCCESS = "SUCCESS"; // 成功的状态字符串

	public final static String FAIL = "FAIL"; // 失败的状态字符串

}
