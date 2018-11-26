package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel(value="模块的实体类")
public class Module {
    private Integer moduleId;
    
    private String moduleName;

    private String description;

    private Integer highScoreMax;

    private Integer highScoreMin;

    private Integer mediumScoreMax;

    private Integer mediumScoreMin;

    private Integer lowScoreMax;

    private Integer lowScoreMin;

    private Integer activated;

    private Integer deleted;

    private Date createdDate;

    private Date updatedDate;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getHighScoreMax() {
        return highScoreMax;
    }

    public void setHighScoreMax(Integer highScoreMax) {
        this.highScoreMax = highScoreMax;
    }

    public Integer getHighScoreMin() {
        return highScoreMin;
    }

    public void setHighScoreMin(Integer highScoreMin) {
        this.highScoreMin = highScoreMin;
    }

    public Integer getMediumScoreMax() {
        return mediumScoreMax;
    }

    public void setMediumScoreMax(Integer mediumScoreMax) {
        this.mediumScoreMax = mediumScoreMax;
    }

    public Integer getMediumScoreMin() {
        return mediumScoreMin;
    }

    public void setMediumScoreMin(Integer mediumScoreMin) {
        this.mediumScoreMin = mediumScoreMin;
    }

    public Integer getLowScoreMax() {
        return lowScoreMax;
    }

    public void setLowScoreMax(Integer lowScoreMax) {
        this.lowScoreMax = lowScoreMax;
    }

    public Integer getLowScoreMin() {
        return lowScoreMin;
    }

    public void setLowScoreMin(Integer lowScoreMin) {
        this.lowScoreMin = lowScoreMin;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}