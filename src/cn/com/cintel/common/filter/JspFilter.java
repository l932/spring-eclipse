package cn.com.cintel.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Constants;

public class JspFilter implements Filter {

	// 设置不需要过滤的url
	private static final String[] noFilters = new String[] { "index.jsp" };

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String uri = request.getRequestURI().replaceAll(request.getContextPath() + "/", "");

		boolean beFilter = true;
		if (uri.equals("")) {
			beFilter = false;
		} else {
			for (String s : noFilters) {
				if (uri.indexOf(s) != -1) {
					beFilter = false;
					break;
				}
			}
		}

		if (beFilter) {
			Manager manager = (Manager) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
			if (manager == null) {
				response.sendRedirect(request.getContextPath() + "/nosession.html");
				return;
			} else {
				// 在这里可以做权限的校验
				arg2.doFilter(req, res);
				return;
			}
		} else {
			arg2.doFilter(req, res);
			return;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
