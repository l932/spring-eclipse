package cn.com.cintel.common;

import javax.servlet.http.HttpServletRequest;

import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Constants;

public class BaseController {
	protected Manager getSessionManager(HttpServletRequest req) {
		return (Manager) req.getSession().getAttribute(Constants.USER_SESSION_KEY);
	}

	protected void setSessionManager(HttpServletRequest req, Manager manager) {
		req.getSession().setAttribute(Constants.USER_SESSION_KEY, manager);
	}

}
