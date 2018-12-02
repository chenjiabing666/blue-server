package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Invoice;
import com.techwells.blue.util.PagingTool;

/**
 * 发票的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface InvoiceService {
	
	/**
	 * 添加发票
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	Object addInvoice(Invoice invoice)throws Exception;
	
	/**
	 * 根据发票Id获取信息
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	Object getInvoiceById(Integer invoiceId)throws Exception;
	
	
	/**
	 * 修改发票
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	Object modifyInvoiceReturnObject(Invoice invoice)throws Exception;
	
	
	/**
	 * 修改发票
	 * @param invoice
	 * @return
	 * @throws Exception
	 */
	int modifyInvoiceReturnCount(Invoice invoice)throws Exception;
	
	
	/**
	 * 根据发票Id删除数据
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	Object deleteInvoiceReturnObject(Integer invoiceId)throws Exception;
	
	/**
	 * 根据发票Id删除数据
	 * @param invoiceId
	 * @return
	 * @throws Exception
	 */
	int deleteInvoiceReturnCount(Integer invoiceId)throws Exception;
	
	
	
	/**
	 * 分页获取发票数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getInvoiceList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
