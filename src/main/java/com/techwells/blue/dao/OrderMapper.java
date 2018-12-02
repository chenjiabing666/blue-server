package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Order;
import com.techwells.blue.domain.rs.UserOrderVos;
import com.techwells.blue.util.PagingTool;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 分页获取订单列表（后台收支）
     * @param pagingTool
     * @return
     */
    List<UserOrderVos> selectOrderListBack(PagingTool pagingTool);
    
	int CountTotalOrderListBack(PagingTool pagingTool);

	/**
     * 分页获取订单列表(个人消费记录)
     * @param pagingTool
     * @return
     */
	List<Order> selectOrderList(PagingTool pagingTool);

	int countTotalOrderList(PagingTool pagingTool);
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderNum
	 * @return
	 */
	Order selectOrderByOrderNum(String orderNum);

	

}