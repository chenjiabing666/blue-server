package com.techwells.blue.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "资料库的实体类")
public class Information {
	private Integer informationId;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "发布人Id")
	private Integer adminId;
	@ApiModelProperty(value = "分类  1:图片视频库 2：文字资料库")
	private Integer type;
	@ApiModelProperty(value = "付款形式 1免费2企业3会员4付费(可多选) ，用逗号分割")
	private String payType;
	@ApiModelProperty(value = "价格")
	private BigDecimal price;
	@ApiModelProperty(value = "积分")
	private Integer point;
	@ApiModelProperty(value = "模块的Id")
    private Integer moduleId;
	@ApiModelProperty(value = "文件路径，如果视频分类就是视频的url，如果是文件的分类就是文件的url")
	private String fileUrl;
//	@ApiModelProperty(value = "审核状态   1待审核   2审核通过   3审核拒绝")
	private Integer status;

	private Integer activated;

	private Integer deleted;

	private Date createTime;

	private Date updateTime;

	private String content;

	public Integer getInformationId() {
		return informationId;
	}
	

	public Integer getModuleId() {
		return moduleId;
	}


	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}


	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl == null ? null : fileUrl.trim();
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}