package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
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
import com.techwells.blue.domain.Invoice;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.InvoiceService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 发票的controller
 * @author 陈加兵
 */
@Api(description="发票的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class InvoiceController {
	
	@Resource
	private InvoiceService invoiceService;
	
	private Logger logger=LoggerFactory.getLogger(InvoiceController.class); //日志
	
	/**
	 * 添加发票
	 * @param request
	 * @return
	 */
	@PostMapping("/invoice/addInvoice")
	@ApiOperation(value="添加发票",response=Invoice.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = true, value = "发票类型 1 个人 2 公司", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "title", dataType="String", required = true, value = "发票抬头", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "number", dataType="String", required = true, value = "纳税人识别号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "amount", dataType="String", required = true, value = "金额", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "name", dataType="String", required = true, value = "姓名", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "email", dataType="String", required = true, value = "电子邮箱", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "address", dataType="String", required = true, value = "地址", defaultValue = ""),
	})
	public Object addInvoice(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String type=request.getParameter("type");  //发票类型 1 个人 2 公司
		String title=request.getParameter("title"); //发票抬头
		String number=request.getParameter("number");  //纳税人识别号
		String amount=request.getParameter("amount");  //金额
		String name=request.getParameter("name");  //姓名
		String mobile=request.getParameter("mobile");  //手机号码
		String email=request.getParameter("email"); //电子邮箱
		String address=request.getParameter("address");  //地址
		String userId=request.getParameter("userId"); //用户id
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票类型不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票抬头不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(number)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("纳税人识别号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(amount)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("金额不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(name)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("姓名不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("电子邮箱不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(address)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("地址不能为空");
			return resultInfo;
		}
		
		//封装数据
		Invoice invoice=new Invoice();
		invoice.setCreatedDate(new Date());
		invoice.setAddress(address);
		invoice.setAmout(new BigDecimal(amount));
		invoice.setEmail(email);
		invoice.setMobile(mobile);
		invoice.setName(name);
		invoice.setNumber(number);
		invoice.setTitle(title);
		invoice.setType(Integer.parseInt(type));
		invoice.setUserId(Integer.parseInt(userId));
		
		//调用service层的方法
		try {
			Object object=invoiceService.addInvoice(invoice);
			return object;
		} catch (Exception e) {
			logger.error("添加发票异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加发票失败");
			return resultInfo;
		}
	}
	
	/**
	 * 获取发票详情
	 * @param request
	 * @return
	 */
	@PostMapping("/invoice/getInvoiceById")
	@ApiOperation(value="获取发票详情",response=Invoice.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "invoiceId", dataType="int", required = true, value = "发票的invoiceId", defaultValue = "1"),
	})
	public Object getInvoiceById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String invoiceId=request.getParameter("invoiceId");
		
		if (StringUtils.isEmpty(invoiceId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=invoiceService.getInvoiceById(Integer.parseInt(invoiceId));
			return object;
		} catch (Exception e) {
			logger.error("获取发票详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改发票的状态（后台处理） 
	 * @param request
	 * @return
	 */
	@PostMapping("/invoice/modifyInvoice")
	@ApiOperation(value="修改发票",response=Invoice.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "invoiceId", dataType="int", required = true, value = "发票的invoiceId", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = true, value = "发票的状态  1 已开具 2 未处理  3 已邮寄", defaultValue = "1"),
	})
	public Object modifyInvoice(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String invoiceId=request.getParameter("invoiceId");  //发票Id
		String status=request.getParameter("status");  //发票的状态  1 已开具 2 未处理  3 已邮寄
		
		if (StringUtils.isEmpty(invoiceId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票状态不能为空");
			return resultInfo;
		}
		
		//封装数据
		Invoice invoice=new Invoice();
		invoice.setInvoiceId(Integer.parseInt(invoiceId));
		invoice.setStatus(Integer.parseInt(status));
		
		//调用service层的方法
		try {
			Object object=invoiceService.modifyInvoiceReturnObject(invoice);
			return object;
		} catch (Exception e) {
			logger.error("修改发票异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除发票
	 * @param request
	 * @return
	 */
	@PostMapping("/invoice/deleteInvoiceById")
	@ApiOperation(value="根据Id删除发票",response=Invoice.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "invoiceId", dataType="int", required = true, value = "发票的invoiceId", defaultValue = "1"),
	})
	public Object deleteInvoice(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String invoiceId=request.getParameter("invoiceId");
		
		if (StringUtils.isEmpty(invoiceId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发票Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=invoiceService.deleteInvoiceReturnObject(Integer.parseInt(invoiceId));
			return object;
		} catch (Exception e) {
			logger.error("删除发票异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取发票列表（后台）
	 * @param request
	 * @return
	 */
	@PostMapping("/invoice/getInvoiceList")
	@ApiOperation(value="分页获取发票列表（后台）",response=Invoice.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = false, value = "发票类型 1 个人 2 公司", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "number", dataType="String", required = false, value = "纳税人识别号", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = false, value = "手机号码", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = false, value = "状态 1 已开具 2 未处理 3 已邮寄", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "title", dataType="String", required = false, value = "发票抬头", defaultValue = ""),
	})
	public Object getInvoiceList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String type=request.getParameter("type"); //发票类型 1 个人 2 公司
		String number=request.getParameter("number");  //纳税人识别号
		String mobile=request.getParameter("mobile");  //手机号码
		String status=request.getParameter("status"); //状态 1 已开具 2 未处理 3 已邮寄
		String title=request.getParameter("title");  //发票抬头
		
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
		
		if (!StringUtils.isEmpty(type)) {
			params.put("type", Integer.parseInt(type));
		}
		
		if (!StringUtils.isEmpty(number)) {
			params.put("number", number);
		}
		
		if (!StringUtils.isEmpty(mobile)) {
			params.put("mobile", mobile);
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		if (!StringUtils.isEmpty(title)) {
			params.put("title",title);
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=invoiceService.getInvoiceList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取发票列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
	}
}
