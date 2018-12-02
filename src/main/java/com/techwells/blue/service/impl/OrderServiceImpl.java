package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.OrderMapper;
import com.techwells.blue.dao.UserInfoMapper;
import com.techwells.blue.dao.UserMapper;
import com.techwells.blue.domain.Order;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.UserInfo;
import com.techwells.blue.domain.rs.UserOrderVos;
import com.techwells.blue.service.OrderService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 订单的业务层的实现类
 * @author 陈加兵
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserInfoMapper userInfoMapper;
	
	
	@Override
	public Object addOrder(Order order) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=orderMapper.insertSelective(order);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getOrderById(Integer orderId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Order order=orderMapper.selectByPrimaryKey(orderId);
		
		if (order==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该订单不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(order);
		return resultInfo;
	}

	
	@Override
	public Object modifyOrderReturnObject(Order order) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=orderMapper.updateByPrimaryKeySelective(order);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyOrderReturnCount(Order order) throws Exception {
		return orderMapper.updateByPrimaryKeySelective(order);
	}

	
	@Override
	public Object deleteOrderReturnObject(Integer orderId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=orderMapper.deleteByPrimaryKey(orderId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteOrderReturnCount(Integer orderId) throws Exception {
		return orderMapper.deleteByPrimaryKey(orderId);
	}

	
	@Override
	public Object getOrderList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=orderMapper.countTotalOrderList(pagingTool);
		
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<Order> orders=orderMapper.selectOrderList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(orders);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	@Override
	public Object getOrderListBack(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=orderMapper.CountTotalOrderListBack(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<UserOrderVos> userOrderVos=orderMapper.selectOrderListBack(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(userOrderVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}


	@Override
	public Object payByPoint(Integer userId, Integer infoId, Integer point)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		User user = userMapper.selectByPrimaryKey(userId);
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户不存在");
			return resultInfo;
		}
		
		
		if (user.getPoint()<point) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("积分不足");
			return resultInfo;
		}
		
		user.setPoint(user.getPoint()-point);
		int count1=userMapper.updateByPrimaryKeySelective(user);
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支付失败");
			return resultInfo;
		}
		
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setInfoId(infoId);
		
		int count2 = userInfoMapper.insertSelective(userInfo);
		if (count2==0) {
			throw new RuntimeException();
		}
		resultInfo.setCode("-1");
		resultInfo.setMessage("支付成功");
		return resultInfo;
	}


	@Override
	public int addOrderReturnCount(Order order) throws Exception {
		return orderMapper.insertSelective(order);
	}


	@Override
	public Order getOrderByOrderByNum(String orderNum) {
		return orderMapper.selectOrderByOrderNum(orderNum);
	}


	@Override
	public int paySuccess(Order order) throws Exception {
		int count=orderMapper.updateByPrimaryKeySelective(order);
		if (count==0) {
			return 0;
		}
		
		//如果订单更新成功，那么需要在t_user_info表中添加一条记录
		UserInfo userInfo=new UserInfo();
		userInfo.setUserId(order.getUserId());
		userInfo.setInfoId(order.getInfoId());
		userInfo.setCreatedDate(new Date());
		
		int count1=userInfoMapper.insertSelective(userInfo);
		if (count1==0) {
			throw new RuntimeException();
		}
		
		return 1;
	}

	
	
	
}