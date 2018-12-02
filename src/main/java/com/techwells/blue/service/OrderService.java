package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Order;
import com.techwells.blue.util.PagingTool;

/**
 * 订单的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface OrderService {
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 * @throws Exception
	 */
	Object addOrder(Order order)throws Exception;
	
	/**
	 * 根据订单Id获取信息
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	Object getOrderById(Integer orderId)throws Exception;
	
	
	/**
	 * 修改订单
	 * @param order
	 * @return
	 * @throws Exception
	 */
	Object modifyOrderReturnObject(Order order)throws Exception;
	
	
	/**
	 * 修改订单
	 * @param order
	 * @return
	 * @throws Exception
	 */
	int modifyOrderReturnCount(Order order)throws Exception;
	
	
	/**
	 * 根据订单Id删除数据
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	Object deleteOrderReturnObject(Integer orderId)throws Exception;
	
	/**
	 * 根据订单Id删除数据
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	int deleteOrderReturnCount(Integer orderId)throws Exception;
	
	
	/**
	 * 分页获取订单数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getOrderList(PagingTool pagingTool)throws Exception;

	/**
	 * 分页获取订单列表 （后台）
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getOrderListBack(PagingTool pagingTool)throws Exception;

	/**
	 * 通过积分付费
	 * @param userId
	 * @param infoId
	 * @param point
	 * @return
	 * @throws Exception
	 */
	Object payByPoint(Integer userId, Integer infoId, Integer point)throws Exception;

	int addOrderReturnCount(Order order)throws Exception;

	/**
	 * 根据订单号获取订单信息
	 * @param orderNum
	 * @return
	 */
	Order getOrderByOrderByNum(String orderNum)throws Exception;

	/**
	 * 支付成功，修改订单的状态
	 * @param order
	 * @return
	 * @throws Exception
	 */
	int paySuccess(Order order)throws Exception;
	
	
	
	
	
}
