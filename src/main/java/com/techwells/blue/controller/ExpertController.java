package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.blue.domain.Expert;
import com.techwells.blue.domain.User;
import com.techwells.blue.service.ExpertService;
import com.techwells.blue.util.BlueConstants;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.UploadFileUtils;

/**
 * 专家的controller
 * @author 陈加兵
 */
@Api(description="专家的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class ExpertController {
	
	@Resource
	private ExpertService expertService;
	
	private Logger logger=LoggerFactory.getLogger(ExpertController.class); //日志
	
	/**
	 * 添加专家
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/addExpert")
	@ApiOperation(value="添加专家",response=Expert.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "expertName", dataType="String", required = true, value = "专家名称", defaultValue = "Tom"),
		@ApiImplicitParam(paramType = "query", name = "introduction", dataType="String", required = true, value = "专家介绍", defaultValue = "专家介绍"),
		@ApiImplicitParam(paramType = "query", name = "skillIds", dataType="String", required = true, value = "专家技能，多个技能用逗号分割", defaultValue = "1,2"),
		@ApiImplicitParam(paramType = "query", name = "sort", dataType="int", required = true, value = "专家排序", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "price", dataType="double", required = true, value = "专家价格", defaultValue = "10000"),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = true, value = "专家状态 1启用 0 关闭", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
	})
	public Object addExpert(HttpServletRequest request,@RequestParam(value="photo",required=false)MultipartFile photo){
		ResultInfo resultInfo=new ResultInfo();
		String expertName=request.getParameter("expertName"); //专家名称
		String introduction=request.getParameter("introduction");  //专家介绍
		String skillIds=request.getParameter("skillIds");  //专家技能，多个技能用逗号分割
		String sort=request.getParameter("sort");  //专家排序
		String price=request.getParameter("price"); //专家价格
		String status=request.getParameter("status"); //专家状态 1启用 0 关闭
		String mobile=request.getParameter("mobile"); //手机号码
		
		if (StringUtils.isEmpty(expertName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(introduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家介绍不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(skillIds)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家技能不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(sort)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家排序不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(price)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家价格不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家状态不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (photo==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家照片不能为空");
			return resultInfo;
		}
		
		//封装数据
		Expert expert=new Expert();
		expert.setExpertName(expertName);
		expert.setExpertIntroduction(introduction);
		expert.setCreatedDate(new Date());
		expert.setExpertSkill(skillIds);
		expert.setExpertSort(Integer.parseInt(sort));
		expert.setPrice(Double.parseDouble(price));
		expert.setStatus(Integer.parseInt(status));
		expert.setMobile(mobile);
		
		
		//上传照片
		String photoName=System.currentTimeMillis()+photo.getOriginalFilename();
		String path=BlueConstants.UPLOAD_PATH+"expert-image/";
		String url=BlueConstants.UPLOAD_URL+"expert-image/"+photoName; //链接
		File targetFile=new File(path,photoName);  //目标路径
		
		try {
			UploadFileUtils.createChildFolder(targetFile);
		} catch (Exception e) {
			logger.error("创建子文件夹异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		//上传
		try {
			photo.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("上传照片异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		expert.setExpertPhoto(url);  //照片地址
		
		//调用service层的方法
		try {
			Object object=expertService.addExpert(expert);
			return object;
		} catch (Exception e) {
			logger.error("添加专家异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取专家详情
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/getExpertById")
	@ApiOperation(value="获取专家详情",response=Expert.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "expertId", dataType="int", required = true, value = "专家的expertId", defaultValue = "1"),
	})
	public Object getExpertById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String expertId=request.getParameter("expertId");
		
		if (StringUtils.isEmpty(expertId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=expertService.getExpertById(Integer.parseInt(expertId));
			return object;
		} catch (Exception e) {
			logger.error("获取专家详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改专家
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/modifyExpert")
	@ApiOperation(value="修改专家",response=Expert.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "expertId", dataType="int", required = true, value = "专家Id", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "expertName", dataType="String", required = true, value = "专家名称", defaultValue = "Tom"),
		@ApiImplicitParam(paramType = "query", name = "introduction", dataType="String", required = true, value = "专家介绍", defaultValue = "专家介绍"),
		@ApiImplicitParam(paramType = "query", name = "skillIds", dataType="String", required = true, value = "专家技能，多个技能用逗号分割", defaultValue = "1,2"),
		@ApiImplicitParam(paramType = "query", name = "sort", dataType="int", required = true, value = "专家排序", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "price", dataType="double", required = true, value = "专家价格", defaultValue = "10000"),
		@ApiImplicitParam(paramType = "query", name = "status", dataType="int", required = true, value = "专家状态 1启用 0 关闭", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "mobile", dataType="String", required = true, value = "手机号码", defaultValue = "18796327106"),
	})
	public Object modifyExpert(HttpServletRequest request,@RequestParam(value="photo",required=false)MultipartFile photo){
		ResultInfo resultInfo=new ResultInfo();
		String expertId=request.getParameter("expertId");  //专家Id
		String expertName=request.getParameter("expertName"); //专家名称
		String introduction=request.getParameter("introduction");  //专家介绍
		String skillIds=request.getParameter("skillIds");  //专家技能，多个技能用逗号分割
		String sort=request.getParameter("sort");  //专家排序
		String price=request.getParameter("price"); //专家价格
		String status=request.getParameter("status"); //专家状态 1启用 0 关闭
		String mobile=request.getParameter("mobile"); //手机号码
		
		if (StringUtils.isEmpty(expertId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(expertName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(introduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家介绍不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(skillIds)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家技能不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(sort)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家排序不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(price)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家价格不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家状态不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		
		//封装数据
		Expert expert=new Expert();
		expert.setExpertId(Integer.parseInt(expertId));
		expert.setExpertName(expertName);
		expert.setExpertIntroduction(introduction);
		expert.setCreatedDate(new Date());
		expert.setExpertSkill(skillIds);
		expert.setExpertSort(Integer.parseInt(sort));
		expert.setPrice(Double.parseDouble(price));
		expert.setStatus(Integer.parseInt(status));
		expert.setMobile(mobile);
		
		//如果图片不为空，那么需要重新上传
		if (photo!=null) {
			//上传照片
			String photoName=System.currentTimeMillis()+photo.getOriginalFilename();
			String path=BlueConstants.UPLOAD_PATH+"expert-image/";
			String url=BlueConstants.UPLOAD_URL+"expert-image/"+photoName; //链接
			File targetFile=new File(path,photoName);  //目标路径
			
			try {
				UploadFileUtils.createChildFolder(targetFile);
			} catch (Exception e) {
				logger.error("创建子文件夹异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("异常");
				return resultInfo;
			}
			
			//上传
			try {
				photo.transferTo(targetFile);
			} catch (Exception e) {
				logger.error("上传照片异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("异常");
				return resultInfo;
			}
			
			expert.setExpertPhoto(url);  //照片地址
		}
		
		
		//调用service层的方法
		try {
			Object object=expertService.modifyExpertReturnObject(expert);
			return object;
		} catch (Exception e) {
			logger.error("修改专家异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除专家
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/deleteExpertById")
	@ApiOperation(value="根据Id删除专家",response=ResultInfo.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "expertId", dataType="int", required = true, value = "专家的expertId", defaultValue = "1"),
	})
	public Object deleteExpert(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String expertId=request.getParameter("expertId");
		
		if (StringUtils.isEmpty(expertId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("专家Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=expertService.deleteExpertReturnObject(Integer.parseInt(expertId));
			return object;
		} catch (Exception e) {
			logger.error("删除专家异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取专家列表(后台)
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/getExpertListBack")
	@ApiOperation(value="分页获取专家列表(后台)",response=Expert.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "expertName", dataType="String", required = false, value = "专家名字", defaultValue = "Tom"),
	})
	public Object getExpertListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String expertName=request.getParameter("expertName");  //专家名字
		
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
		
		if (!StringUtils.isEmpty(expertName)) {
			params.put("expertName", expertName);
		}
		
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=expertService.getExpertList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取专家列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}

	
	/**
	 * 分页获取专家列表 前台
	 * @param request
	 * @return
	 */
	@PostMapping("/expert/getExpertListForeground")
	@ApiOperation(value="分页获取专家列表 前台(专家对话页面)",response=Expert.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
	})
	public Object getExpertListForeground(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		
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
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=expertService.getExpertListForeground(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取专家列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	


}
