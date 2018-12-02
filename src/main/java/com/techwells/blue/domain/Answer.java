package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "答案的实体类")
public class Answer {
	private Integer answerId;
	@ApiModelProperty(value = "问题Id")
	private Integer questionId;
	@ApiModelProperty(value = "选项的内容")
	private String content;
	@ApiModelProperty(value = "答案 1-5对应 A-E")
	private Integer answer;
	@ApiModelProperty(value = "1-5 分别对应0%， 25%，50%，75%，100%")
	private Integer scroeLevel;
	@ApiModelProperty(value = "常见问题")
	private String questionReason;
	private String questionReasonUrl;
	@ApiModelProperty(value = "建议")
	private String advise;
	private String adviseUrl;
	@ApiModelProperty(value = "解读")
	private String analysis;
	private String analysisUrl;
	private Integer status;

	private Integer activated;

	private Integer deleted;

	private Date updatedDate;

	private Date createdDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public Integer getScroeLevel() {
		return scroeLevel;
	}

	public void setScroeLevel(Integer scroeLevel) {
		this.scroeLevel = scroeLevel;
	}

	public String getQuestionReason() {
		return questionReason;
	}

	public void setQuestionReason(String questionReason) {
		this.questionReason = questionReason == null ? null : questionReason
				.trim();
	}

	public String getQuestionReasonUrl() {
		return questionReasonUrl;
	}

	public void setQuestionReasonUrl(String questionReasonUrl) {
		this.questionReasonUrl = questionReasonUrl == null ? null
				: questionReasonUrl.trim();
	}

	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise == null ? null : advise.trim();
	}

	public String getAdviseUrl() {
		return adviseUrl;
	}

	public void setAdviseUrl(String adviseUrl) {
		this.adviseUrl = adviseUrl == null ? null : adviseUrl.trim();
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis == null ? null : analysis.trim();
	}

	public String getAnalysisUrl() {
		return analysisUrl;
	}

	public void setAnalysisUrl(String analysisUrl) {
		this.analysisUrl = analysisUrl == null ? null : analysisUrl.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getActivated() {
		return activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}