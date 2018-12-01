package com.techwells.blue.domain;

import java.util.Date;

public class Answer {
    private Integer answerId;

    private Integer questionId;
    private String content;
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private Integer answer;

    private Integer scroeLevel;

    private String questionReason;

    private String questionReasonUrl;

    private String advise;

    private String adviseUrl;

    private String analysis;

    private String analysisUrl;

    private Integer status;

    private Integer activated;

    private Integer deleted;

    private Date updatedDate;

    private Date createdDate;

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
        this.questionReason = questionReason == null ? null : questionReason.trim();
    }

    public String getQuestionReasonUrl() {
        return questionReasonUrl;
    }

    public void setQuestionReasonUrl(String questionReasonUrl) {
        this.questionReasonUrl = questionReasonUrl == null ? null : questionReasonUrl.trim();
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