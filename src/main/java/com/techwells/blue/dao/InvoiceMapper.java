package com.techwells.blue.dao;

import java.util.List;

import com.techwells.blue.domain.Invoice;
import com.techwells.blue.util.PagingTool;

public interface InvoiceMapper {
    int deleteByPrimaryKey(Integer invoiceId);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Integer invoiceId);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    /**
     * 分页获取列表
     * @param pagingTool
     * @return
     */
	List<Invoice> selectInvoiceList(PagingTool pagingTool);

	int countTotalInvoiceList(PagingTool pagingTool);
}