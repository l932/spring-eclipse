package cn.com.cintel.common.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Constants;

public class SpringInteceptor implements HandlerInterceptor {
	// 设置不需要过滤的url
	private static final String[] noFilters = new String[] { "login.do", "logout.do", "getvercode.do","loginj.do"};

	/**
	 * 该方法会在render view完成后执行，也可以说在请求过程（request processing）完成之后执行。该方法可以用来清理资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 有ModelAndView 传进来，那么我们就可以在render view之前往view中添加额外的model对象，或者对view的去处进行修改
	 * 比如，一个实体类的添加，要添加当前登录人的id、姓名，jsp页面传入的实体类中添加人的字段为空，这里就可以进行处理。
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 该方法会在Controller的方法执行前会被调用，可以使用这个方法来中断或者继续执行链的处理，当返回true时，处理执行链会继续，
	 * 当返回false时，则不会去执行Controller的方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
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
				return false;
			} else {
				// 在这里可以做权限的校验
				// 用HashSet做比对最快
				return true;
			}
		} else {
			return true;
		}
	}

}
