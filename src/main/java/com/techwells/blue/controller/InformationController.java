package com.techwells.blue.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.techwells.blue.domain.Information;
import com.techwells.blue.domain.User;
import com.techwells.blue.domain.rs.AdminInformationVos;
import com.techwells.blue.service.InformationService;
import com.techwells.blue.util.BlueConstants;
import com.techwells.blue.util.PagingTool;
import com.techwells.blue.util.ResultInfo;
import com.techwells.blue.util.UploadFileUtils;
import com.techwells.blue.util.WangEditorDomin;

/**
 * 资料库的controller
 * @author 陈加兵
 */
@Api(description="资料库的api")   //标注说明改接口的作用
@RestController
@RequestMapping("*.do")    //配置访问的后缀，只有后缀为.do的url才能访问到接口
public class InformationController {
	
	@Resource
	private InformationService informationService;
	
	private Logger logger=LoggerFactory.getLogger(InformationController.class); //日志
	
	/**
	 * 添加资料库
	 * @param request
	 * @return
	 */
	@PostMapping("/information/addInformation")
	@ApiOperation(value="添加资料库",response=Information.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "title", dataType="String", required = true, value = "标题 18字以内", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "moduleId", dataType="int", required = true, value = "模块Id", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "payType", dataType="String", required = true, value = "付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "price", dataType="double", required = false, value = "价格  可选", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "point", dataType="int", required = false, value = "积分 可选", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="String", required = true, value = "分类  1:图片视频库 2：文字资料库", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "content", dataType="String", required = true, value = "内容 富文本的形式", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "adminId", dataType="int", required = true, value = "发布人Id", defaultValue = ""),
	})
	public Object addInformation(HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file){
		ResultInfo resultInfo=new ResultInfo();
		String title=request.getParameter("title");  //标题 18字以内
		String payType=request.getParameter("payType");  //付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割
		String price=request.getParameter("price");  //价格  可选
		String point=request.getParameter("point");  //积分 可选
		String type=request.getParameter("type");    //分类  1:图片视频库 2：文字资料库
		String content=request.getParameter("content");  //内容 富文本的形式
		String adminId=request.getParameter("adminId");  //发布人Id
		String moduleId=request.getParameter("moduleId"); //模块Id
		
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题不能为空");
			return resultInfo;
		}
		
		if (title.length()>18) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题必须在18字以内");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(payType)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("付费形式不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("内容不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布人Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		
		//如果是付费的话，那么价格和积分必须有一项
		if (payType.contains(String.valueOf(4))) {
			if (StringUtils.isEmpty(price)&&StringUtils.isEmpty(point)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请选择付费的形式");
				return resultInfo;
			}
		}
		
		
		if (payType.equals("1")&&(payType.equals("2")||payType.equals("3")||payType.equals("4"))) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("付费形式错误");
			return resultInfo;
		}
		
		
		//封装数据
		Information information=new Information();
		information.setTitle(title);
		information.setPayType(payType); 
		information.setAdminId(Integer.parseInt(adminId));
		information.setContent(content);
		information.setCreateTime(new Date());
		
		if (!StringUtils.isEmpty(price)) {
			information.setPrice(new BigDecimal(price));  //价格
		}
		
		if (!StringUtils.isEmpty(point)) {
			information.setPoint(Integer.parseInt(point));  //积分
		}
		
		information.setType(Integer.parseInt(type));
		information.setModuleId(Integer.parseInt(moduleId)); 
		
		//如果是文件资料，那么文件不能为空
		if (file == null && type.equals("2")) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("文件不能为空");
			return resultInfo;
		}

		// 上传文件
		String fileName = System.currentTimeMillis()
				+ file.getOriginalFilename();
		String path = BlueConstants.UPLOAD_PATH + "info-file/"; // 文件存放的路径
		String fileUrl = BlueConstants.UPLOAD_URL + "info-file/" + fileName;
		File targetFile = new File(path, fileName);

		try {
			UploadFileUtils.createChildFolder(targetFile);
		} catch (Exception e) {
			logger.error("创建子文件夹异常", e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("创建子文件夹异常");
			return resultInfo;
		}

		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error("上传文件异常", e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传文件异常");
			return resultInfo;
		}
		information.setFileUrl(fileUrl);
		
		
		
		//调用service层的方法
		try {
			Object object=informationService.addInformation(information);
			return object;
		} catch (Exception e) {
			logger.error("添加资料库异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取资料库详情
	 * @param request
	 * @return
	 */
	@PostMapping("/information/getInformationById")
	@ApiOperation(value="获取资料库详情",response=Information.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "informationId", dataType="int", required = true, value = "资料库的informationId", defaultValue = "1"),
	})
	public Object getInformationById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String informationId=request.getParameter("informationId");
		
		if (StringUtils.isEmpty(informationId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资料库Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=informationService.getInformationById(Integer.parseInt(informationId));
			return object;
		} catch (Exception e) {
			logger.error("获取资料库详细信息失败",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改资料库
	 * @param request
	 * @return
	 */
	@PostMapping("/information/modifyInformation")
	@ApiOperation(value="修改资料库",response=Information.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "informationId", dataType="int", required = true, value = "资料库的informationId", defaultValue = "1"),
	})
	public Object modifyInformation(HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file){
		ResultInfo resultInfo=new ResultInfo();
		String informationId=request.getParameter("informationId");  //资料Id
		String title=request.getParameter("title");  //标题 18字以内
		String payType=request.getParameter("payType");  //付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割
		String price=request.getParameter("price");  //价格  可选
		String point=request.getParameter("point");  //积分 可选
		String type=request.getParameter("type");    //分类  1:图片视频库 2：文字资料库
		String content=request.getParameter("content");  //内容 富文本的形式
		String moduleId=request.getParameter("moduleId"); //模块Id
		
		if (StringUtils.isEmpty(informationId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资料库Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题不能为空");
			return resultInfo;
		}
		
		if (title.length()>18) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题必须在18字以内");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(payType)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("付费形式不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("内容不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(moduleId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模块Id不能为空");
			return resultInfo;
		}
		
		
		//如果是付费的话，那么价格和积分必须有一项
		if (payType.contains(String.valueOf(4))) {
			if (StringUtils.isEmpty(price)&&StringUtils.isEmpty(point)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请填写价格或者积分");
				return resultInfo;
			}
		}
		
		
		if (payType.equals("1")&&(payType.equals("2")||payType.equals("3")||payType.equals("4"))) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("付费形式错误");
			return resultInfo;
		}
		
		
		//封装数据
		Information information=new Information();
		information.setInformationId(Integer.parseInt(informationId));
		information.setTitle(title);
		information.setPayType(payType); 
		information.setContent(content);
		information.setCreateTime(new Date());
		
		if (!StringUtils.isEmpty(price)) {
			information.setPrice(new BigDecimal(price));  //价格
		}
		
		if (!StringUtils.isEmpty(point)) {
			information.setPoint(Integer.parseInt(point));  //积分
		}
		
		information.setType(Integer.parseInt(type));
		information.setModuleId(Integer.parseInt(moduleId)); 
		
		
		if (file!=null) {
			// 上传文件
			String fileName = System.currentTimeMillis()
					+ file.getOriginalFilename();
			String path = BlueConstants.UPLOAD_PATH + "info-file/"; // 文件存放的路径
			String fileUrl = BlueConstants.UPLOAD_URL + "info-file/" + fileName;
			File targetFile = new File(path, fileName);

			try {
				UploadFileUtils.createChildFolder(targetFile);
			} catch (Exception e) {
				logger.error("创建子文件夹异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("创建子文件夹异常");
				return resultInfo;
			}

			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				logger.error("上传文件异常", e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			information.setFileUrl(fileUrl);
		}

		
		
		//调用service层的方法
		try {
			Object object=informationService.modifyInformationReturnObject(information);
			return object;
		} catch (Exception e) {
			logger.error("修改资料库异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据Id删除资料库
	 * @param request
	 * @return
	 */
	@PostMapping("/information/deleteInformationById")
	@ApiOperation(value="根据Id删除资料库",response=Information.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "informationId", dataType="int", required = true, value = "资料库的informationId", defaultValue = "1"),
	})
	public Object deleteInformation(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String informationId=request.getParameter("informationId");
		
		if (StringUtils.isEmpty(informationId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资料库Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=informationService.deleteInformationReturnObject(Integer.parseInt(informationId));
			return object;
		} catch (Exception e) {
			logger.error("删除资料库异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取资料库列表(后台)
	 * @param request
	 * @return
	 */
	@PostMapping("/information/getInformationListBack")
	@ApiOperation(value="分页获取资料库列表(后台)",response=Information.class,hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "title", dataType="String", required = false, value = "标题", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "payType", dataType="int", required = false, value = "付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = false, value = "分类  1:图片视频库 2：文字资料库", defaultValue = ""),
	})
	public Object getInformationListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String title=request.getParameter("title");  //标题
		String payType=request.getParameter("payType");//付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割
		String type=request.getParameter("type");   //分类  1:图片视频库 2：文字资料库
		
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
		
		if (!StringUtils.isEmpty(title)) {
			params.put("title", title);
		}
		
		if (!StringUtils.isEmpty(payType)) {
			params.put("payType", Integer.parseInt(payType));
		}
		
		if (!StringUtils.isEmpty(type)) {
			params.put("type", Integer.parseInt(type));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=informationService.getInformationListBack(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取资料库列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 分页获取资料库列表(前台)
	 * 			1、需要判断这个用户有没有权限访问付费的文件（免费的除外）
	 * 			2、这里返回的资料库中有个一个pow字段表示当前用户有没有这个权限下载或者观看
	 * 			
	 * @param request
	 * @return
	 */
	@PostMapping("/information/getInformationListForeground")
	@ApiOperation(value="分页获取资料库列表(前台)",response=AdminInformationVos.class,hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", dataType="int", required = true, value = "每页显示的数量", defaultValue = "10"),
		@ApiImplicitParam(paramType = "query", name = "title", dataType="String", required = false, value = "标题", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "moduleName", dataType="String", required = false, value = "模块名称", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "type", dataType="int", required = false, value = "分类  1:图片视频库 2：文字资料库", defaultValue = ""),
		@ApiImplicitParam(paramType = "query", name = "userId", dataType="int", required = true, value = "用户Id，", defaultValue = ""),
	})
	public Object getInformationListForeground(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String title=request.getParameter("title");  //标题
		String type=request.getParameter("type");   //分类  1:图片视频库 2：文字资料库
		String moduleName=request.getParameter("moduleName");  //模块名称
		String userId=request.getParameter("userId");  //用户Id，
		
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
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		params.put("userId", Integer.parseInt(userId));  //设置用户id
		
		
		if (!StringUtils.isEmpty(title)) {
			params.put("title", title);
		}
		
		if (!StringUtils.isEmpty(moduleName)) {
			params.put("moduleName",moduleName);
		}
		
		if (!StringUtils.isEmpty(type)) {
			params.put("type", Integer.parseInt(type));
		}
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=informationService.getInformationListForeground(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取资料库列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	

	/**
	 * WangEditor上传图片
	 * 
	 * @param files
	 *            上传的图片文件 这里的@RequestParam("myFile")一定要和前端设置的名字一样，否则将会接受不到
	 * @param files
	 * @return
	 */
	@ApiOperation(value="WangEditor上传图片",response=Information.class,hidden=true)
	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "pageNum", dataType="int", required = true, value = "当前的页数", defaultValue = "1"),
	})
	@RequestMapping("/information/uploadImage")
	public Object uploadImage(@RequestParam("myFile") MultipartFile[] files) {
		WangEditorDomin wangEditorDomin = new WangEditorDomin(); // 封装结果集
		List<String> data = new ArrayList<String>(); // 存储图片的url
		// 遍历图片
		for (MultipartFile file : files) {
			String fileName = System.currentTimeMillis()
					+ file.getOriginalFilename(); // 图片的名字
			String filepath =BlueConstants.UPLOAD_PATH
					+ "information-image\\";
			String imageUrl = BlueConstants.UPLOAD_URL
					+ "information-image/" + fileName; // 图片的访问路径
			File targetFile = new File(filepath, fileName); // 文件的路径

			// 如果文件夹不存在，那么创建一个即可
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs(); // 递归创建文件夹
			}

			try {
				file.transferTo(targetFile); // 保存图片
				data.add(imageUrl); // 添加url
//				data.add("http://img3.imgtn.bdimg.com/it/u=3258641584,555286175&fm=26&gp=0.jpg");
			} catch (Exception e) {
				wangEditorDomin.setErrno("-1"); // 上传失败
				return wangEditorDomin;
			}
		}

		// 执行到了这里，说明上传成功了，那么返回即可
		wangEditorDomin.setData(data);
		return wangEditorDomin;
	}


}
