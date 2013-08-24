package cn.com.cintel.common.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Manager entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_MANAGER")
public class Manager extends cn.com.cintel.common.BasePojo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4986527996758830295L;
	private Long managerid;
	private String loginname;
	private String pwd;
	private String realname;
	private Integer role;
	private Date intime;
	private String flag1;
	private String flag2;

	// Constructors

	/** default constructor */
	public Manager() {
	}

	/** minimal constructor */
	public Manager(String loginname, String pwd, Integer role, Date intime) {
		this.loginname = loginname;
		this.pwd = pwd;
		this.role = role;
		this.intime = intime;
	}

	/** full constructor */
	public Manager(String loginname, String pwd, String realname, Integer role, Date intime, String flag1, String flag2) {
		this.loginname = loginname;
		this.pwd = pwd;
		this.realname = realname;
		this.role = role;
		this.intime = intime;
		this.flag1 = flag1;
		this.flag2 = flag2;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_MANAGER_ID") })
	@Column(name = "MANAGERID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getManagerid() {
		return managerid;
	}

	public void setManagerid(Long managerid) {
		this.managerid = managerid;
	}

	@Column(name = "LOGINNAME", nullable = false, length = 64)
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "PWD", nullable = false, length = 32)
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "REALNAME", length = 64)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "ROLE", nullable = false, precision = 1, scale = 0)
	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Column(name = "INTIME", nullable = false, length = 7)
	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	@Column(name = "FLAG1")
	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	@Column(name = "FLAG2")
	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	@Transient
	public String getRolestr() {
		String restr = "不详";
		switch (getRole()) {
		case 0:
			restr = "总管理员";
			break;
		case 1:
			restr = "管理员";
			break;
		default:
			break;
		}
		return restr;
	}

	@Transient
	public String getIntimestr() {
		if (getIntime() != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(intime);
		} else {
			return "不详";
		}
	}

}