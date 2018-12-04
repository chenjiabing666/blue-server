package com.techwells.blue.domain;

import java.util.Date;

public class Radar {
    private Integer radarId;

    private Integer moduleId;

    private Integer industryId;

    private Double avgLevel;

    private Integer activated;

    private Integer deleted;

    private Date createdDate;

    private Date updatedDate;

    public Integer getRadarId() {
        return radarId;
    }

    public void setRadarId(Integer radarId) {
        this.radarId = radarId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Double getAvgLevel() {
        return avgLevel;
    }

    public void setAvgLevel(Double avgLevel) {
        this.avgLevel = avgLevel;
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