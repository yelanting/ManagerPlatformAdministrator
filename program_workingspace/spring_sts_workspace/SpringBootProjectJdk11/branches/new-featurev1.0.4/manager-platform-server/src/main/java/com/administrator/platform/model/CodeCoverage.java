/**
 * @author : 孙留平
 * @since : 2019年3月11日 下午7:45:53
 * @see:
 */
package com.administrator.platform.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.administrator.platform.core.base.util.CalendarUtil;
import com.administrator.platform.enums.NewSourceType;
import com.administrator.platform.model.base.BaseDomain;
import com.administrator.platform.tools.codebuild.entity.BuildType;
import com.administrator.platform.tools.codebuild.impl.CodeBuildAnt;
import com.administrator.platform.tools.codebuild.impl.CodeBuildGradleAndroid;
import com.administrator.platform.tools.codebuild.impl.CodeBuildMaven;
import com.administrator.platform.tools.codebuild.intf.CodeBuildIntf;
import com.administrator.platform.tools.vcs.common.VCSType;

/**
 * @author : Administrator
 * @since : 2019年3月11日 下午7:45:53
 * @see : 代码覆盖率信息DO
 */
@Entity
@Table(name = "tb_code_coverage")
/**
 * @author : Administrator
 * @since : 2019年3月11日 下午4:50:49
 * @see :
 */
public class CodeCoverage extends BaseDomain {
	private static final long serialVersionUID = -8519947068338363038L;

	/**
	 * @see 主键自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * @see 项目名称
	 */
	private String projectName;

	/**
	 * 服务器地址
	 */
	private String tcpServerIp;

	/**
	 * jacoco的代理端口
	 */

	private int tcpServerPort;

	/**
	 * 全量覆盖率信息存放地址
	 */
	private String wholeCodeCoverageDataUrl;

	/**
	 * 增量覆盖率信息存放地址
	 */

	private String incrementCodeCoverageDataUrl;

	/**
	 * 代码管理系统用户名称
	 */
	private String username;

	/**
	 * 代码管理系统用户密码
	 */
	private String password;

	/**
	 * 版本控制系统类型
	 */
	private VCSType versionControlType;
	/**
	 * 旧版本代码路径
	 */
	private String olderRemoteUrl;

	/**
	 * 待比对的版本
	 */
	private String olderVersion;

	/**
	 * 新版本的版本路径
	 */
	private String newerRemoteUrl;
	/**
	 * 新版本
	 */
	private String newerVersion;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date createDate;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	private Date updateDate;

	/**
	 * 描述备注
	 */
	private String description;

	/**
	 * 构建类型
	 */
	private BuildType buildType;

	/**
	 * 是否需要编译
	 */
	private boolean needCompile;

	/**
	 * 依赖代码
	 */
	private String dependencyProjects;

	/**
	 * jdk版本
	 */
	private String jdkVersion;

	/**
	 * gradle需要的渠道名称
	 */

	private String channelName;

	/**
	 * 以下字段2019年8月1日 21:46:13新增，为适配从服务器上下载代码
	 */
	/**
	 * 代码所在服务器id
	 */
	private Long serverId;

	/**
	 * 服务器所在代码路径
	 */
	private String sourceCodePath;

	/**
	 * 新代码类型
	 */
	private NewSourceType newSourceType;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	@DateTimeFormat(pattern = CalendarUtil.DEFAULT_FORMAT_WHOLE)
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public String getTcpServerIp() {
		return tcpServerIp;
	}

	public void setTcpServerIp(String tcpServerIp) {
		this.tcpServerIp = tcpServerIp;
	}

	public int getTcpServerPort() {
		return tcpServerPort;
	}

	public void setTcpServerPort(int tcpServerPort) {
		this.tcpServerPort = tcpServerPort;
	}

	public String getWholeCodeCoverageDataUrl() {
		return wholeCodeCoverageDataUrl;
	}

	public void setWholeCodeCoverageDataUrl(String wholeCodeCoverageDataUrl) {
		this.wholeCodeCoverageDataUrl = wholeCodeCoverageDataUrl;
	}

	public String getIncrementCodeCoverageDataUrl() {
		return incrementCodeCoverageDataUrl;
	}

	public void setIncrementCodeCoverageDataUrl(
	        String incrementCodeCoverageDataUrl) {
		this.incrementCodeCoverageDataUrl = incrementCodeCoverageDataUrl;
	}

	public String getOlderRemoteUrl() {
		return olderRemoteUrl;
	}

	public void setOlderRemoteUrl(String olderRemoteUrl) {
		this.olderRemoteUrl = olderRemoteUrl;
	}

	public String getOlderVersion() {
		return olderVersion;
	}

	public void setOlderVersion(String olderVersion) {
		this.olderVersion = olderVersion;
	}

	public String getNewerRemoteUrl() {
		return newerRemoteUrl;
	}

	public void setNewerRemoteUrl(String newerRemoteUrl) {
		this.newerRemoteUrl = newerRemoteUrl;
	}

	public String getNewerVersion() {
		return newerVersion;
	}

	public void setNewerVersion(String newerVersion) {
		this.newerVersion = newerVersion;
	}

	public VCSType getVersionControlType() {
		return versionControlType;
	}

	public void setVersionControlType(VCSType versionControlType) {
		this.versionControlType = versionControlType;
	}

	/**
	 * @return the buildType
	 */
	public BuildType getBuildType() {
		return buildType;
	}

	/**
	 * @param buildType
	 *            the buildType to set
	 */
	public void setBuildType(BuildType buildType) {
		this.buildType = buildType;
	}

	/**
	 * @return the needCompile
	 */
	public boolean isNeedCompile() {
		return needCompile;
	}

	/**
	 * @param needCompile
	 *            the needCompile to set
	 */
	public void setNeedCompile(boolean needCompile) {
		this.needCompile = needCompile;
	}

	/**
	 * @return the dependencyProjects
	 */
	public String getDependencyProjects() {
		return dependencyProjects;
	}

	/**
	 * @param dependencyProjects
	 *            the dependencyProjects to set
	 */
	public void setDependencyProjects(String dependencyProjects) {
		this.dependencyProjects = dependencyProjects;
	}

	/**
	 * 从当前对象中解析出
	 * 
	 * @see :
	 * @param :
	 * @return : CodeBuildIntf
	 * @return
	 */

	public CodeBuildIntf parseCodeBuildFromThisObject() {

		switch (this.getBuildType()) {
			case ANT:
				return new CodeBuildAnt();
			case MAVEN:
				return new CodeBuildMaven();
			case GRADLE:

				CodeBuildGradleAndroid codeBuildGradleAndroid = new CodeBuildGradleAndroid();
				codeBuildGradleAndroid.setChannelName(this.getChannelName());
				return codeBuildGradleAndroid;
			default:
				return new CodeBuildMaven();
		}
	}

	public String getJdkVersion() {
		return jdkVersion;
	}

	public void setJdkVersion(String jdkVersion) {
		this.jdkVersion = jdkVersion;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getSourceCodePath() {
		return sourceCodePath;
	}

	public void setSourceCodePath(String sourceCodePath) {
		this.sourceCodePath = sourceCodePath;
	}

	public NewSourceType getNewSourceType() {
		return newSourceType;
	}

	public void setNewSourceType(NewSourceType newSourceType) {
		this.newSourceType = newSourceType;
	}

	@Override
	public String toString() {
		return "CodeCoverage [id=" + id + ", projectName=" + projectName
		        + ", tcpServerIp=" + tcpServerIp + ", tcpServerPort="
		        + tcpServerPort + ", wholeCodeCoverageDataUrl="
		        + wholeCodeCoverageDataUrl + ", incrementCodeCoverageDataUrl="
		        + incrementCodeCoverageDataUrl + ", username=" + username
		        + ", password=" + password + ", versionControlType="
		        + versionControlType + ", olderRemoteUrl=" + olderRemoteUrl
		        + ", olderVersion=" + olderVersion + ", newerRemoteUrl="
		        + newerRemoteUrl + ", newerVersion=" + newerVersion
		        + ", createDate=" + createDate + ", updateDate=" + updateDate
		        + ", description=" + description + ", buildType=" + buildType
		        + ", needCompile=" + needCompile + ", dependencyProjects="
		        + dependencyProjects + ", jdkVersion=" + jdkVersion
		        + ", channelName=" + channelName + ", serverId=" + serverId
		        + ", sourceCodePath=" + sourceCodePath + ", newSourceType="
		        + newSourceType + "]";
	}
}
