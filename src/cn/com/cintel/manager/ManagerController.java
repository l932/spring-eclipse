package cn.com.cintel.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cintel.common.BaseController;
import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Constants;
import cn.com.cintel.common.util.Page;
import cn.com.cintel.common.util.PublicUtil;

import com.google.gson.Gson;

@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {

	private static Logger log = Logger.getLogger(ManagerController.class.getName());

	@Autowired
	private ManagerService mservice;
	
	@RequestMapping(value = "loginj.do", method = RequestMethod.GET)
	@ResponseBody
	public String loginJson(HttpServletResponse resp,@RequestParam(value = "msg", required = true) String msg) {
		Gson gson = new Gson();
		ManagerJson mjs = gson.fromJson(msg, ManagerJson.class);
		System.out.println(mjs.getUsername());
		ManagerJson mj = new ManagerJson();
		mj.setLoginname("admin");
		mj.setPwd("111");
		mj.setUsername("足球");
		return gson.toJson(mj);
	}

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "loginname") String loginname, @RequestParam(value = "pwd") String pwd,
			@RequestParam(value = "vercode") String vercode, HttpServletRequest req, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String rejsp = "index";
		List<Manager> ms = null;
		String imgvercode = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (imgvercode != null && imgvercode.equalsIgnoreCase(vercode)) {
			ms = mservice.login(loginname);
			if (ms == null || ms.size() == 0) {
				mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_NO_USER);
			} else if (ms.size() > 1) {
				mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_USER_ANOMALY);
			} else {
				Manager m = ms.get(0);
				if (m.getPwd().equalsIgnoreCase(PublicUtil.md5Pwd(pwd))) {
					// 如果需要详细的权限控制，则需要在此时查询出该用户所属角色的权限列表
					// 将权限列表压入session，供过滤器进行鉴别
					// 如果需要登录后左侧菜单栏的菜单也是动态的，则需要把html拼好成一个字符串
					// 页面通过EL表达式取这个字符串
					setSessionManager(req, m);
					rejsp = "frame";
				} else {
					mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_ERROR_PWD);
				}

			}
		} else {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_ERROR_VERCODE);
		}

		mav.setViewName(rejsp);
		return mav;
	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		Manager m = getSessionManager(req);
		if (m != null) {
			req.getSession().removeAttribute(Constants.USER_SESSION_KEY);
		}
		mav.setViewName("index");
		return mav;

	}

	@RequestMapping(value = "findmanagers.do")
	public ModelAndView findManagers(@RequestParam(value = "loginname", required = false) String loginname,
			@RequestParam(value = "pageNo", required = false) Integer index) {
		ModelAndView mav = new ModelAndView();

		index = index == null ? 1 : index;
		Manager m = new Manager();
		m.setLoginname(loginname);
		return searchPageList(mav, m, index);
	}

	@RequestMapping(value = "delmanagers.do", method = RequestMethod.POST)
	public ModelAndView delManagers(@RequestParam(value = "ids4del") String ids4del) {

		int renum = mservice.delManagers(ids4del);
		// 返回列表显示页面
		ModelAndView mav = new ModelAndView();
		mav.addObject(Constants.REMSG_NAME_4_JSP, renum + "条记录" + Constants.REMSG_DEL_SUCCESS);

		return searchPageList(mav, new Manager(), 1);
	}

	@RequestMapping(value = "addmanager.do", method = RequestMethod.POST)
	public ModelAndView addManagers(@ModelAttribute("manager") Manager m) {
		ModelAndView mav = new ModelAndView();

		// m.setIntime(new Timestamp(System.currentTimeMillis()));
		m.setPwd(PublicUtil.md5Pwd(Constants.DEFAULT_PWD));

		if (mservice.addManagers(m)) {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_ADD_SUCCESS);
		} else {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_ADD_FAILE);
		}
		// 返回列表显示页面
		return searchPageList(mav, new Manager(), 1);
	}

	@RequestMapping(value = "updmanager.do", method = RequestMethod.POST)
	public ModelAndView updManagers(@ModelAttribute("manager") Manager m) {
		ModelAndView mav = new ModelAndView();

		if (mservice.updManagers(m)) {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_UPD_SUCCESS);
		} else {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_UPD_FAILE);
		}
		// 返回列表显示页面
		return searchPageList(mav, new Manager(), 1);
	}

	@RequestMapping(value = "managerinfo_{id}.do", method = RequestMethod.GET)
	public ModelAndView findManagerById(@PathVariable("id") Long managerid) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("manager", mservice.findManagerById(managerid));
		mav.setViewName("manager/managerinfo");
		return mav;
	}

	private ModelAndView searchPageList(ModelAndView mav, Manager obj, Integer index) {
		Page pageResult = mservice.findPageManagers(obj, index, Constants.PAGE_SIZE);
		mav.addObject("manager", obj);
		mav.addObject("pageResult", pageResult);
		mav.setViewName("manager/managers");
		return mav;
	}

}
