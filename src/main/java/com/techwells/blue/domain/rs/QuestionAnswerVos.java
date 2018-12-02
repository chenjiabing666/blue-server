package com.techwells.blue.domain.rs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 问题和答案的值对象，用于提交答案
 * 
 * @author 陈加兵
 * @since 2018年12月2日 上午11:46:12
 */
@ApiModel(value = "问题和答案的值对象，用于提交答案")
public class QuestionAnswerVos {
	@ApiModelProperty(value = "问题Id")
	private Integer questionId;
	@ApiModelProperty(value = "答案Id")
	private Integer answerId;
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	@Override
	public String toString() {
		return "QuestionAnswerVos [questionId=" + questionId + ", answerId="
				+ answerId + "]";
	}

}
