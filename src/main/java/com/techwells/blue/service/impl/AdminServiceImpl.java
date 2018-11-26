package com.techwells.blue.service.impl;

import java.security.interfaces.RSAKey;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

import com.techwells.blue.annotation.PrintLog;
import com.techwells.blue.dao.AdminAuthorityMapper;
import com.techwells.blue.dao.AdminMapper;
import com.techwells.blue.domain.Admin;
import com.techwells.blue.domain.AdminAuthority;
import com.techwells.blue.service.AdminService;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;

/**
 * 管理员的业务层的实现类
 * @author 陈加兵
 */
@Service
public class AdminServiceImpl implements AdminService{
	
	@Resource
	private AdminMapper adminMapper;
	
	@Resource
	private AdminAuthorityMapper adminAuthorityMapper;
	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object addAdmin(Admin admin) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=adminMapper.insertSelective(admin);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getAdminById(Integer adminId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Admin admin=adminMapper.selectByPrimaryKey(adminId);
		
		if (admin==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该管理员不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(admin);
		return resultInfo;
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object modifyAdminReturnObject(Admin admin) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=adminMapper.updateByPrimaryKeySelective(admin);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int modifyAdminReturnCount(Admin admin) throws Exception {
		return adminMapper.updateByPrimaryKeySelective(admin);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object deleteAdminReturnObject(Integer adminId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=adminMapper.deleteByPrimaryKey(adminId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public int deleteAdminReturnCount(Integer adminId) throws Exception {
		return adminMapper.deleteByPrimaryKey(adminId);
	}

	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object getAdminList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Admin> admins=adminMapper.selectAdminBatch(pagingTool);
		int count=adminMapper.countTotal(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(count);
		resultInfo.setResult(admins);
		return resultInfo;
	}

	
	
	@PrintLog  //输出异常信息到日志文件中
	@Override
	public Object login(String account, String password) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//根据管理员账号查找是否存在
		Admin admin=adminMapper.selectAdminByAccount(account,1);
		
		if (admin==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该账号不存在或者被禁用了");
			return resultInfo;
		}
		
		//该账号存在，比较密码
		if (!admin.getPassword().equals(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码错误");
			return resultInfo;
		}
		
		//密码相同，那么登录成功，返回权限列表
		AdminAuthority authority=adminAuthorityMapper.selectByPrimaryKey(admin.getAdminId());
		
		Map<String, Object> map=new HashedMap<String, Object>();
		map.put("authoritys", authority.getAuthoritys().split(","));
		map.put("adminId", admin.getAdminId());
		map.put("admin", admin);
		
		resultInfo.setMessage("登录成功");	
		resultInfo.setResult(map);
		resultInfo.setTotal(1);
		return resultInfo;
	}

	
	
	
}
