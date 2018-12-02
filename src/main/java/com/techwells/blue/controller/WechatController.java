package com.techwells.blue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Order;
import com.techwells.blue.service.OrderService;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.wechat.ConstantWeChat;
import com.techwells.blue.util.wechat.WeChatUtils;

/**
 * 微信支付的controller
 * 
 * @author 陈加兵
 * @since 2018年12月2日 下午5:13:31
 */
@Api(description="微信支付的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")
public class WechatController {
	
	@Resource
	private OrderService orderService;
	
	private Logger logger=LoggerFactory.getLogger(WechatController.class);
	
	/**
	 * 微信预支付
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/wetchat/pay")
	@ApiOperation(value="添加管理员",response=Order.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "infoId", dataType="int", required = true, value = "资料Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "amout", dataType="double", required = true, value = "金额", defaultValue = ""),
	})
	public Object pay(HttpServletRequest request,HttpServletResponse response) {
		ResultInfo resultInfo = new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String infoId=request.getParameter("infoId");  //资料Id
		String amount=request.getParameter("amout");  //金额
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(infoId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资料Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(amount)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("金额不能为空");
			return resultInfo;
		}
		
		String total = String.valueOf((int) (Double.parseDouble(amount) * 100)); // 订单金额
		String orderNum = System.currentTimeMillis() + ""; // 订单号,商户系统的内部订单号，相当于流水号（这里可以和订单号一样，这样就不用多存一个字段了）
		String nonceStr = WeChatUtils.getRandomString(32); // 生成随机字符串
		
		// 拉起预支付订单
		Map<String, String> responMap;
		try {
			responMap = WeChatUtils.preparePay(orderNum, total, nonceStr,
					orderNum, request);
		} catch (Exception e1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("统一下单异常");
			return resultInfo;
		}

		// 如果通信成功
		if (responMap.get("return_code").equals(ConstantWeChat.SUCCESS)
				&& responMap.get("result_code").equals(ConstantWeChat.SUCCESS)) {
			// 交易成功
			if (responMap.get("result_code").equals(ConstantWeChat.SUCCESS)) {
//				// 禁止图像缓存。
				response.setContentType("image/png");// 设置相应类型,告诉浏览器输出的内容为图片
				response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expire", 0);
				ServletOutputStream outputStream;
				try {
					outputStream = response.getOutputStream();
				} catch (IOException e) {
					logger.error("获取输出流异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("异常");
					return resultInfo;
				}
				
				String url=responMap.get("code_url"); //获取二维码链接
				BufferedImage bufferedImage = null;
				try {
					bufferedImage = WeChatUtils.getResponseEntity(url, 200, 200, "png");
				} catch (Exception e) {
					logger.error("生成二维码异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("生成二维码异常");
					return resultInfo;
				}
				
				if (bufferedImage==null) {
					resultInfo.setCode("-1");
					resultInfo.setMessage("生成二维码失败");
					return resultInfo;
				}
				
				//生成订单
				Order order=new Order();
				order.setUserId(Integer.parseInt(userId));
				order.setOrderId(Integer.parseInt(infoId));
				order.setOrderNum(orderNum);
				order.setDeleted(1);  //待付款
				order.setPayType(1);  //微信支付
				order.setDescription("查看资料");
				order.setStatus(2);  //支出
				order.setAmount(new BigDecimal(amount));
				order.setActivated(2);  //支出
				order.setCreatedDate(new Date());
				order.setInfoId(Integer.parseInt(infoId));
				try {
					int count=orderService.addOrderReturnCount(order); 
				} catch (Exception e) {
					logger.error("添加订单异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("添加订单异常");
					return resultInfo;
				}
				
				try {
					return ImageIO.write(bufferedImage, "png", outputStream);
				} catch (IOException e) {
					logger.error("写入输出流异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("异常");
					return resultInfo;
				}
			}
		}
		resultInfo.setCode("-1");
		resultInfo.setMessage("支付失败");
		return resultInfo;
	}
	
	/**
	 * 支付成功的回调函数，改变订单的状态 1、需要判断当前订单的状态是否已经支付完成，如果完成了，则什么也不干
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wechat/receiveNotifyUrl")
	@ApiOperation(value="微信支付的回调",response=Order.class,hidden=true)
	@ApiImplicitParams({
	})
	public Object receiveNotifyUrl(HttpServletRequest request) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>(); // 封装返回的结果集
		Map<String, String> responseMap = new HashMap<String, String>(); // 响应结果
		try {
			responseMap = WeChatUtils.getCallbackResult(request);
		} catch (Exception e) {
			logger.error("获取结果集异常",e);
			resultMap.put("return_code", ConstantWeChat.FAIL);
			resultMap.put("return_msg", "获取结果集失败");
			try {
				return WeChatUtils.mapToXml(resultMap);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
//		logger.info("------------------------------"+responseMap+"---------------------------");

		// 验证签名
		if (ConstantWeChat.SUCCESS.equals(responseMap.get("return_code"))) {
			// 签名正确,业务也是正确的
			String orderNum = responseMap.get("out_trade_no"); // 获取支付的流水号,这里就是订单号

			Order order=orderService.getOrderByOrderByNum(orderNum);

			if (order == null) {
				resultMap.put("return_code", ConstantWeChat.FAIL);
				resultMap.put("return_msg", "订单不存在");
				return WeChatUtils.mapToXml(resultMap);
			}

			// 如果未支付状态，那么需要支付
			if (order.getDeleted().equals(1)) {
				order.setDeleted(2); // 已支付
				int count = orderService.paySuccess(order);
				if (count == 1) {
					resultMap.put("return_code", ConstantWeChat.SUCCESS);
					resultMap.put("return_msg", "成功");
					return WeChatUtils.mapToXml(resultMap);
				} else {
					resultMap.put("return_code", ConstantWeChat.FAIL);
					resultMap.put("return_msg", "异常");
					return WeChatUtils.mapToXml(resultMap);
				}
			}
		}
		resultMap.put("return_code", ConstantWeChat.SUCCESS);
		resultMap.put("return_msg", "成功");
		return WeChatUtils.mapToXml(resultMap);
	}
	
	
	

}
