package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Order;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.OrderService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 订单的controller
 * @author 陈加兵
 */
@Api(description="订单（消费记录）的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	private Logger logger=LoggerFactory.getLogger(OrderController.class); //日志
	
	/**
	 * 添加订单
	 * @param request
	 * @return
	 */
	@PostMapping("/order/addOrder")
	@ApiOperation(value="添加订单",response=Order.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderName", dataType="String", required = true, value = "订单的orderName", defaultValue = "Tom"),
	})
	public Object addOrder(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String orderName=request.getParameter("orderName");
		
		if (StringUtils.isEmpty(orderName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("orderName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Order order=new Order();
		
		//调用service层的方法
		try {
			Object object=orderService.addOrder(order);
			return object;
		} catch (Exception e) {
			logger.error("添加订单异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取订单详情
	 * @param request
	 * @return
	 */
	@PostMapping("/order/getOrderById")
	@ApiOperation(value="获取订单详情",response=Order.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", dataType="int", required = true, value = "订单的orderId", defaultValue = "1"),
	})
	public Object getOrderById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String orderId=request.getParameter("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("订单Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=orderService.getOrderById(Integer.parseInt(orderId));
			return object;
		} catch (Exception e) {
			logger.error("获取订单详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改订单
	 * @param request
	 * @return
	 */
	@PostMapping("/order/modifyOrder")
	@ApiOperation(value="修改订单",response=Order.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", dataType="int", required = true, value = "订单的orderId", defaultValue = "1"),
	})
	public Object modifyOrder(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String orderId=request.getParameter("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("订单Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Order order=new Order();
		order.setOrderId(Integer.parseInt(orderId));
		
		//调用service层的方法
		try {
			Object object=orderService.modifyOrderReturnObject(order);
			return object;
		} catch (Exception e) {
			logger.error("修改订单异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除订单
	 * @param request
	 * @return
	 */
	@PostMapping("/order/deleteOrderById")
	@ApiOperation(value="根据Id删除订单",response=Order.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "orderId", dataType="int", required = true, value = "订单的orderId", defaultValue = "1"),
	})
	public Object deleteOrder(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String orderId=request.getParameter("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("订单Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=orderService.deleteOrderReturnObject(Integer.parseInt(orderId));
			return object;
		} catch (Exception e) {
			logger.error("删除订单异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取订单列表(后台)
	 * @param request
	 * @return
	 */
	@PostMapping("/order/getOrderListBack")
	@ApiOperation(value="分页获取订单列表(后台)",response=Order.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = false, value = "手机号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "orderNum", dataType="String", required = false, value = "订单号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = false, value = "收支类型  2收入 1支出", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "userType", dataType="int", required = false, value = "用户类型  1、普通用户，2、企业用户，3、vip", defaultValue = ""),
	})
	public Object getOrderListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String mobile=request.getParameter("mobile");  //手机号
		String orderNum=request.getParameter("orderNum"); //订单号
		String status=request.getParameter("status");  //收支类型  2收入 1支出
		String userType=request.getParameter("userType"); //用户类型  1、普通用户，2、企业用户，3、vip
		
		//校验数据
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(mobile)) {
			params.put("mobile",mobile);
		}
		
		if (!StringUtils.isEmpty(orderNum)) {
			params.put("orderNum",orderNum);
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status",Integer.parseInt(status));
		}
		
		if (!StringUtils.isEmpty(userType)) {
			params.put("userType",Integer.parseInt(userType));
		}
		
		pagingTool.setParams(params);
		
		
		//调用service方法
		try {
			Object object=orderService.getOrderListBack(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取订单列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取消费记录（前台）
	 * @param request
	 * @return
	 */
	@PostMapping("/order/getOrderListForeground")
	@ApiOperation(value="分页获取消费记录（前台）",response=Order.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = false, value = "订单的状态 1 充值 2 支出 3退款 4 过期  不填是全部  可选", defaultValue = ""),
	})
	public Object getOrderListForeground(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String userId=request.getParameter("userId"); //用户Id
		String status=request.getParameter("status"); //订单的状态 1 充值 2 支出 3退款 4 过期  不填是全部  可选
		
		//校验数据
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		params.put("userId", Integer.parseInt(userId));
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		pagingTool.setParams(params);
		
		
		//调用service方法
		try {
			Object object=orderService.getOrderList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取订单列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 通过积分支付
	 * @param request
	 * @return
	 */
	@PostMapping("/order/payByPoint")
	@ApiOperation(value="分页获取订单列表(后台)",response=Order.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "infoId", dataType="int", required = true, value = "资料Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "point", dataType="int", required = true, value = "积分", defaultValue = "1"),
	})
	public Object payByPoint(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String infoId=request.getParameter("infoId"); //资料Id
		String userId=request.getParameter("userId"); //用户Id
		String point=request.getParameter("point"); //积分
		
		if (StringUtils.isEmpty(infoId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资料Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(point)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("积分不能为空");
			return resultInfo;
		}
		
		try {
			Object object=orderService.payByPoint(Integer.parseInt(userId),Integer.parseInt(infoId),Integer.parseInt(point));
			return object;
		} catch (Exception e) {
			logger.error("支付积分异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("支付积分失败");
			return resultInfo;
		}
		
		
		
		
		
		
	}
	
	
	
}
