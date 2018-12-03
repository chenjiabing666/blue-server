package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "热身问题的实体类")
public class Warm {
	private Integer warmId;
	@ApiModelProperty(value = "用户Id")
	private Integer userId;
	@ApiModelProperty(value = "公司名称")
	private String company;
	@ApiModelProperty(value = "行业")
	private String industry;
	@ApiModelProperty(value = "企业的阶段 1 引入期 2 发展期 3 成熟期 4 衰退期")
	private Integer stage;

	private Integer activated;

	private Integer deleted;

	private Date createdDate;

	private Date updatedDate;

	public Integer getWarmId() {
		return warmId;
	}

	public void setWarmId(Integer warmId) {
		this.warmId = warmId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry == null ? null : industry.trim();
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
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