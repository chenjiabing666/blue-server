package com.techwells.blue.domain.rs;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提交答案的实体类
 * @author 陈加兵 	
 * @since 2018年12月4日 下午3:28:02
 */
@ApiModel(value = "提交答案的实体类")
public class SubmitVos {
	@ApiModelProperty(value = "1 整体测试 0 单模块测试")
	private Integer status;
	@ApiModelProperty(value = "用户Id")
	private Integer userId;  
	private List<QuestionAnswerVos> questionAnswerVos;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<QuestionAnswerVos> getQuestionAnswerVos() {
		return questionAnswerVos;
	}
	public void setQuestionAnswerVos(List<QuestionAnswerVos> questionAnswerVos) {
		this.questionAnswerVos = questionAnswerVos;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
