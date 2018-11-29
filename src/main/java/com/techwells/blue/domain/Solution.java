package com.techwells.blue.domain;

import java.util.Date;

public class Solution {
    private Integer solutionId;

    private Integer industryId;

    private Integer type;

    private String questionReason;

    private String questionReasonUrl;

    private String advise;

    private String adviseUrl;

    private String analysis;

    private String analysisUrl;

    private Integer moduleId;

    private Integer activated;

    private Integer deleted;

    private Date updatedDate;

    private Date createdDate;

    public Integer getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Integer solutionId) {
        this.solutionId = solutionId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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