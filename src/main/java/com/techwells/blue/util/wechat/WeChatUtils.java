package com.techwells.blue.util.wechat;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.techwells.blue.util.HttpUtils;
import com.techwells.blue.util.wechat.ConstantWeChat;

/**
 * <p>
 * 微信支付的工具类
 * </p>
 * 
 * @author 陈加兵
 * @since 2018-9-13
 */
public class WeChatUtils {

	/**
	 * 获取回调url中返回结果，并且将其转换成Map集合
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getCallbackResult(
			HttpServletRequest request) throws Exception {
		// 创建输入流
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			stringBuffer.append(line);
		}
		return WXPayUtil.xmlToMap(stringBuffer.toString());
	}

	/**
	 * 将xml格式的数据转换成Map
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String mapToXml(Map<String, String> map) throws Exception {
		return WXPayUtil.mapToXml(map);
	}

	/**
	 * 将xml字符串转成Map
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> xmlToMap(String xml) throws Exception {
		return WXPayUtil.xmlToMap(xml);
	}

	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	public static String generateNonceStr() {
		return WXPayUtil.generateNonceStr();
	}

	/**
	 * 将map和私钥key生成签名
	 * 
	 * @param map
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String generateSignature(Map<String, String> map, String key)
			throws Exception {
		return WXPayUtil.generateSignature(map, key);
	}

	/**
	 * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
	 *
	 * @param data
	 *            Map类型数据
	 * @param key
	 *            API密钥
	 * @return 签名是否正确
	 * @throws Exception
	 */
	public static boolean isSignatureValid(Map<String, String> data, String key)
			throws Exception {
		return WXPayUtil.isSignatureValid(data, key);
	}

	/**
	 * 拉起预支付订单
	 * 
	 * @param orderNum
	 *            订单号
	 * @param total
	 *            总金额 单位为分 整数
	 * @param nonceStr
	 *            随机字符串
	 * @param outTradeNo
	 *            商户系统的订单号，不是数据库中的订单号，需要根据其在回调的时候查询指定的订单
	 * @throws Exception
	 */
	public static Map<String, String> preparePay(String orderNum, String total,
			String nonceStr, String outTradeNo, HttpServletRequest request)
			throws Exception {
		SortedMap<String, String> params = new TreeMap<String, String>(); // 封装请求参数
		params.put("appid", ConstantWeChat.APPID); // 设置appId
		params.put("mch_id", ConstantWeChat.MCH_ID); // 设置商户号
		params.put("body", ConstantWeChat.BODY); // 设置Body
		params.put("nonce_str", nonceStr); // 设置随机字符串
		params.put("out_trade_no", outTradeNo); // 设置订单号
		params.put("total_fee", total); // 设置金额,单位为分
		params.put("notify_url", ConstantWeChat.NOTIFY_URL); // 设置回调地址
		params.put("trade_type", ConstantWeChat.TRADE_TYPE); // 设置支付类型
		params.put("spbill_create_ip", HttpUtils.getIp(request)); // 设置服务器的IP地址
		// String sign = WXPayUtil.generateSignature(params,
		// ConstantWeChat.PRIVATE_KEY); // 生成签名
		String sign = createSign(params, ConstantWeChat.PRIVATE_KEY);
		params.put("sign", sign); // 设置签名
		// 请求url，获取返回结果
		String responseXml = HttpUtils.doPost(ConstantWeChat.PREPAY_URL,
				WXPayUtil.mapToXml(params));
		return xmlToMap(responseXml); // 返回结果集
	}

	/**
	 * 创建签名
	 * 
	 * @param parameters
	 *            需要签名的参数，用sortMap包裹
	 * @param apiKey
	 *            私钥
	 * @return
	 */
	public static String createSign(SortedMap<String, String> parameters,
			String apiKey) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + apiKey);// 最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
		try {
			String sign = MD5.encrypt(sb.toString(), "UTF-8").toUpperCase();
			return sign;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 获取随机数
	 * 
	 * @param length
	 *            随机数字的位数
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int number = 0;
		for (int i = 0; i < length; i++) {
			number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	
	/**
     * 生成并下载二维码
     * @param url 二维码对于URL
     * @param width 二维码宽
     * @param height 二维码高
     * @param format  二维码格式
     * @throws WriterException
     * @throws IOException
     */
	public static BufferedImage getResponseEntity(String url,
			int width, int height, String format) throws Exception{
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url,
				BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		// 将矩阵转为Image
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		return image;
	}
	

	public static Map<String, String> refund(String nonceStr,
			String outTradeNo, String outRefundNo, String totalFee,
			String refundFee) {
		SortedMap<String, String> params = new TreeMap<String, String>(); // 封装请求参数
		params.put("appid", ConstantWeChat.APPID); // 设置appId
		params.put("mch_id", ConstantWeChat.MCH_ID); // 设置商户号
		params.put("nonce_str", nonceStr); // 随机字符串
		params.put("out_trade_no", outTradeNo); // 商户系统内部订单号（微信支付的流水号）
		params.put("out_refund_no", outRefundNo); // 商户系统内部的退款单号
		params.put("total_fee", totalFee); // 订单的总金额，单位为分，必须是整数
		params.put("refund_fee", refundFee); // 退款的总金额，单位为分，必须是整数
		return null;
	}
}

class MD5 {

	public static String encrypt(String source) throws NoSuchAlgorithmException {
		return encrypt(source, null);
	}

	public static String encrypt(String source, String encode)
			throws NoSuchAlgorithmException {
		java.security.MessageDigest md5;
		md5 = java.security.MessageDigest.getInstance("MD5");
		if (encode != null) {
			try {
				md5.update(source.getBytes(encode));
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			md5.update(source.getBytes());
		}

		byte[] byteDigest = md5.digest();
		return String.valueOf(toHexChar(byteDigest));
	}

	private static String toHexChar(byte[] b) // 浜岃鍒惰浆瀛楃涓?
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}

		return hs.toUpperCase();
	}
}
