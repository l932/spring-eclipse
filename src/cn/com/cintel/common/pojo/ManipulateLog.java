package cn.com.cintel.common.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ManipulateLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MANIPULATE_LOG")
public class ManipulateLog extends cn.com.cintel.common.BasePojo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8347390278139754999L;
	// Fields

	private BigDecimal logId;
	private String contentId;
	private Short mediaType;
	private Boolean mediaStatus;
	private Integer managerId;
	private Timestamp createDate;
	private String flag1;
	private String flag2;

	// Constructors

	/** default constructor */
	public ManipulateLog() {
	}

	/** minimal constructor */
	public ManipulateLog(String contentId, Short mediaType, Boolean mediaStatus, Integer managerId, Timestamp createDate) {
		this.contentId = contentId;
		this.mediaType = mediaType;
		this.mediaStatus = mediaStatus;
		this.managerId = managerId;
		this.createDate = createDate;
	}

	/** full constructor */
	public ManipulateLog(String contentId, Short mediaType, Boolean mediaStatus, Integer managerId, Timestamp createDate, String flag1, String flag2) {
		this.contentId = contentId;
		this.mediaType = mediaType;
		this.mediaStatus = mediaStatus;
		this.managerId = managerId;
		this.createDate = createDate;
		this.flag1 = flag1;
		this.flag2 = flag2;
	}

	// Property accessors
	@SequenceGenerator(name = "generator")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "LOG_ID", unique = true, nullable = false, precision = 30, scale = 0)
	public BigDecimal getLogId() {
		return this.logId;
	}

	public void setLogId(BigDecimal logId) {
		this.logId = logId;
	}

	@Column(name = "CONTENT_ID", nullable = false, length = 64)
	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	@Column(name = "MEDIA_TYPE", nullable = false, precision = 3, scale = 0)
	public Short getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(Short mediaType) {
		this.mediaType = mediaType;
	}

	@Column(name = "MEDIA_STATUS", nullable = false, precision = 1, scale = 0)
	public Boolean getMediaStatus() {
		return this.mediaStatus;
	}

	public void setMediaStatus(Boolean mediaStatus) {
		this.mediaStatus = mediaStatus;
	}

	@Column(name = "MANAGER_ID", nullable = false, precision = 9, scale = 0)
	public Integer getManagerId() {
		return this.managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	@Column(name = "CREATE_DATE", nullable = false, length = 7)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "FLAG1")
	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	@Column(name = "FLAG2")
	public String getFlag2() {
		return this.flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

}