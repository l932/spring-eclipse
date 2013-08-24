package cn.com.cintel.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Constants {
	private final static Logger log = Logger.getLogger(Constants.class.getName());

	public Constants() {
		super();
	}

	/**
	 * 统一的由Action返回给JSP页面的信息
	 */
	public static final String REMSG_NAME_4_JSP = "remsg";

	public static final String REMSG_ERROR_VERCODE = "验证码错误";

	public static final String REMSG_NO_USER = "用户不存在";

	public static final String REMSG_ERROR_PWD = "密码错误";

	public static final String REMSG_USER_ANOMALY = "用户异常";

	public static final String REMSG_ADD_SUCCESS = "新建成功";

	public static final String REMSG_ADD_FAILE = "新建失败";

	public static final String REMSG_UPD_SUCCESS = "修改成功";

	public static final String REMSG_UPD_FAILE = "修改失败";

	public static final String REMSG_DEL_SUCCESS = "删除成功";

	public static final String REMSG_DEL_FAILE = "删除失败";

	public static final String REMSG_UPLOAD_SUCCESS = "上传成功";

	public static final String REMSG_UPLOAD_FAILE = "上传失败";
	/**
	 * ADMIN用户的主键
	 */
	public static final Long ADMIN_ID = 1L;

	/**
	 * 分页数据数目
	 */
	public static final Integer PAGE_SIZE = 10;

	/**
	 * 用户登录Session的Key值
	 */
	public static final String USER_SESSION_KEY = "usersessionkey";

	/**
	 * 用户登录密码的密钥
	 */
	public static final String PASSWORD_KEY = "12345678";

	/**
	 * 默认密码
	 */
	public static final String DEFAULT_PWD = "111111";

	/**
	 * DES加密密钥
	 */
	public static final String SERVERKEY = "12345678";

	/**
	 * 上传的总目录
	 */
	public static String UPLOAD_DIR = "";

	static {
		Properties prop = new Properties();
		try {
			prop.load(Constants.class.getResourceAsStream("/cfg.properties"));

			log.debug("-----读取配置文件-----Begin");
			UPLOAD_DIR = prop.getProperty("UPLOAD_DIR");
			log.debug("-----读取配置文件-----End");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
