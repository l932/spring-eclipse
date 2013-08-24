package cn.com.cintel.common.pojo2;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Manager entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "manager", catalog = "jsk")//catalog = "jsk"代表表空间，可以去掉也可以不去掉，如果不去掉，现网数据库的表空间必须与之一致
public class Manager extends cn.com.cintel.common.BasePojo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3750483065436021734L;
	private String managerid;
	private String pwd;
	private String username;
	private Timestamp intime;
	private Integer gender;

	// Constructors

	/** default constructor */
	public Manager() {
	}

	/** full constructor */
	public Manager(String managerid,  String pwd, String username, Timestamp intime, Integer gender) {
		this.managerid = managerid;
		this.pwd = pwd;
		this.username = username;
		this.intime = intime;
		this.gender = gender;
	}

	// Property accessors
	@Id
	@Column(name = "managerid", unique = true, nullable = false, length = 36)
	public String getManagerid() {
		return this.managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	@Column(name = "pwd", nullable = false, length = 32)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "username", nullable = false, length = 64)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "intime", nullable = false, length = 19)
	public Timestamp getIntime() {
		return this.intime;
	}

	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

	@Column(name = "gender", nullable = false)
	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	//Transient说明下面的方法与数据库的字段没有对应关系
	//getXXXstr()方法主要是处理类似0女、1男这种字段，在JSP页面上可以显示汉字，而非数字
	@Transient
	public String getGenderstr() {
		String restr = "不详";
		switch (getGender()) {
		case 0:
			restr = "女";
			break;
		case 1:
			restr = "男";
			break;
		default:
			break;
		}
		return restr;
	}

	//格式化时间类型的字段，方便在页面上输出
	@Transient
	public String getIntimestr() {
		if (getIntime() != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(intime);
		} else {
			return "不详";
		}
	}

}