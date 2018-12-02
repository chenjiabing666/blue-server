package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="user实体类")
public class User {
	
    private Integer userId;

    private String userIcon;

    private String userName;
    
    private String nickName;

    private String password;
    
    @ApiModelProperty(value="用户类型：1、普通用户，2、认证企业，3、vip企业")
    private Integer userType;

    private String email;
    
    private String emailCode;

    private String mobile;

    private String address;
    
    @ApiModelProperty(value="性别 1 男 2 女")
    private Integer gender;

    private String description;
    
    @ApiModelProperty(value="积分")
    private Integer point;
    
    @ApiModelProperty(value="邀请码")
    private String invitedCode;
    @ApiModelProperty(value="推荐人Id")
    private Integer recommendId;
    @ApiModelProperty(value="最后登录的时间")
    private Date lastLoginDate;
    @ApiModelProperty(value="总的消费金额")
    private BigDecimal consumeTotal;
    @ApiModelProperty(value="已开发票的金额")
    private BigDecimal alreadyInvoice;
    @ApiModelProperty(value="公司名称")
    private String company;  
    private String industry;
    @ApiModelProperty(value="组织机构")
    private String organization;
    @ApiModelProperty(value="营业执照")
    private String license;
    
    private Integer activated;

    private Integer deleted;

    private Date createdDate;

    private Date updatedDate;

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
		this.company = company;
	}

	public String getIndustry() {
		return industry;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}

	public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public BigDecimal getConsumeTotal() {
		return consumeTotal;
	}

	public void setConsumeTotal(BigDecimal consumeTotal) {
		this.consumeTotal = consumeTotal;
	}

	public BigDecimal getAlreadyInvoice() {
		return alreadyInvoice;
	}

	public void setAlreadyInvoice(BigDecimal alreadyInvoice) {
		this.alreadyInvoice = alreadyInvoice;
	}

	public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode == null ? null : invitedCode.trim();
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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