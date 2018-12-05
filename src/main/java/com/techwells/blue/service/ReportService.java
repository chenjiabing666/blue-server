package com.techwells.blue.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.blue.domain.Report;
import com.techwells.blue.util.PagingTool;

/**
 * 报告的service
 * @author 陈加兵
 */
@Transactional  //添加事务管理器
public interface ReportService {
	
	/**
	 * 添加报告
	 * @param report
	 * @return
	 * @throws Exception
	 */
	Object addReport(Report report)throws Exception;
	
	/**
	 * 根据报告Id获取信息
	 * @param reportId
	 * @return
	 * @throws Exception
	 */
	Object getReportById(Integer reportId)throws Exception;
	
	
	/**
	 * 修改报告
	 * @param report
	 * @return
	 * @throws Exception
	 */
	Object modifyReportReturnObject(Report report)throws Exception;
	
	
	/**
	 * 修改报告
	 * @param report
	 * @return
	 * @throws Exception
	 */
	int modifyReportReturnCount(Report report)throws Exception;
	
	
	/**
	 * 根据报告Id删除数据
	 * @param reportId
	 * @return
	 * @throws Exception
	 */
	Object deleteReportReturnObject(Integer reportId)throws Exception;
	
	/**
	 * 根据报告Id删除数据
	 * @param reportId
	 * @return
	 * @throws Exception
	 */
	int deleteReportReturnCount(Integer reportId)throws Exception;
	
	
	
	/**
	 * 分页获取报告数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getReportList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
