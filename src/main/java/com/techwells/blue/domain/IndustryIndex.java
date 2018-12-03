package com.techwells.blue.domain;

import java.util.Date;

public class IndustryIndex {
    private Integer industryIndexId;

    private String name;

    private String pureRate;

    private String impureRate;

    private String flowRate;

    private String depositRate;

    private String accountRate;

    private String businessRate;

    private String profitRate;

    private Integer activated;

    private Integer deleted;

    private Date createdDate;

    private Date updatedDate;

    public Integer getIndustryIndexId() {
        return industryIndexId;
    }

    public void setIndustryIndexId(Integer industryIndexId) {
        this.industryIndexId = industryIndexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPureRate() {
        return pureRate;
    }

    public void setPureRate(String pureRate) {
        this.pureRate = pureRate == null ? null : pureRate.trim();
    }

    public String getImpureRate() {
        return impureRate;
    }

    public void setImpureRate(String impureRate) {
        this.impureRate = impureRate == null ? null : impureRate.trim();
    }

    public String getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(String flowRate) {
        this.flowRate = flowRate == null ? null : flowRate.trim();
    }

    public String getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(String depositRate) {
        this.depositRate = depositRate == null ? null : depositRate.trim();
    }

    public String getAccountRate() {
        return accountRate;
    }

    public void setAccountRate(String accountRate) {
        this.accountRate = accountRate == null ? null : accountRate.trim();
    }

    public String getBusinessRate() {
        return businessRate;
    }

    public void setBusinessRate(String businessRate) {
        this.businessRate = businessRate == null ? null : businessRate.trim();
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate == null ? null : profitRate.trim();
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