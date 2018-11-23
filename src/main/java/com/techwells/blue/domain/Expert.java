package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value="专家的实体类")
public class Expert {
    private Integer expertId;

    private String expertName;
    private String mobile;
    private String expertIntroduction;
    @ApiModelProperty(value="专家技能   逗号隔开")
    private String expertSkill;
    private String expertPhoto;
    private Integer expertSort;
    @ApiModelProperty(value="专家状态 1启用 0 关闭")
    private Integer status;

    private Double price;

    private Integer activated;

    private Integer deleted;

    private Date createdDate;

    private Date updatedDate;
    
    @ApiModelProperty(value="专家的技能列表")
    List<Skill> skillList;  
    

    public List<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName == null ? null : expertName.trim();
    }

    public String getExpertIntroduction() {
        return expertIntroduction;
    }

    public void setExpertIntroduction(String expertIntroduction) {
        this.expertIntroduction = expertIntroduction == null ? null : expertIntroduction.trim();
    }

    public String getExpertSkill() {
        return expertSkill;
    }

    public void setExpertSkill(String expertSkill) {
        this.expertSkill = expertSkill == null ? null : expertSkill.trim();
    }

    public String getExpertPhoto() {
        return expertPhoto;
    }

    public void setExpertPhoto(String expertPhoto) {
        this.expertPhoto = expertPhoto == null ? null : expertPhoto.trim();
    }

    public Integer getExpertSort() {
        return expertSort;
    }

    public void setExpertSort(Integer expertSort) {
        this.expertSort = expertSort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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