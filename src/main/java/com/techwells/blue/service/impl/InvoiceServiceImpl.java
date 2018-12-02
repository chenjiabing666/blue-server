package com.techwells.blue.service.impl;

import java.math.BigDecimal;
import java.security.interfaces.RSAKey;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.InvoiceMapper;
import com.techwells.blue.dao.UserMapper;
import com.techwells.blue.domain.Invoice;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.InvoiceService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 发票的业务层的实现类
 * @author 陈加兵
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	@Resource
	private InvoiceMapper invoiceMapper;
	
	@Resource
	private UserMapper userMapper;
	
	
	@Override
	public Object addInvoice(Invoice invoice) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//根据用户Id获取用户信息
		User user=userMapper.selectByPrimaryKey(invoice.getUserId());
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该用户不存在");
			return resultInfo;
		}
		
		int count=invoiceMapper.insertSelective(invoice);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		//修改用户的发票总金额和已开具金额
		BigDecimal alreadyInvoice = user.getAlreadyInvoice();  
		user.setAlreadyInvoice(alreadyInvoice.add(invoice.getAmout()));
		
		if (user.getConsumeTotal().compareTo(user.getAlreadyInvoice())==-1) {
			throw new RuntimeException("已开发票的金额已经超出消费的总金额");  //回滚数据
		}
		
		int count1=userMapper.updateByPrimaryKeySelective(user);
		
		if (count1==0) {
			throw new RuntimeException();  //回滚数据
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	
	@Override
	public Object getInvoiceById(Integer invoiceId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Invoice invoice=invoiceMapper.selectByPrimaryKey(invoiceId);
		
		if (invoice==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该发票不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(invoice);
		return resultInfo;
	}

	
	@Override
	public Object modifyInvoiceReturnObject(Invoice invoice) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=invoiceMapper.updateByPrimaryKeySelective(invoice);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	
	@Override
	public int modifyInvoiceReturnCount(Invoice invoice) throws Exception {
		return invoiceMapper.updateByPrimaryKeySelective(invoice);
	}

	
	@Override
	public Object deleteInvoiceReturnObject(Integer invoiceId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=invoiceMapper.deleteByPrimaryKey(invoiceId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	
	@Override
	public int deleteInvoiceReturnCount(Integer invoiceId) throws Exception {
		return invoiceMapper.deleteByPrimaryKey(invoiceId);
	}

	
	@Override
	public Object getInvoiceList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=invoiceMapper.countTotalInvoiceList(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<Invoice> invoices=invoiceMapper.selectInvoiceList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(invoices);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	
}