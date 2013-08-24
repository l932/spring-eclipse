package cn.com.cintel.manager;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Page;

@Service
@Transactional
public class ManagerService {
	@Autowired
	private ManagerDao mdao;

	@Transactional(readOnly = true)
	public List<Manager> login(String loginname) {
		return mdao.findManagerByLoginName(loginname);
	}

	@Transactional(readOnly = true)
	public Page findPageManagers(Manager m, Integer index, Integer maxCount) {
		return mdao.findPageManagers(m, index, maxCount);
	}

	@Transactional(readOnly = true)
	public Manager findManagerById(Long managerid) {
		return mdao.get(managerid);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int delManagers(String ids) {
		return mdao.delManagerById(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean updManagers(Manager newManager) {
		Manager oldManager = mdao.get(newManager.getManagerid());
		oldManager.setLoginname(newManager.getLoginname());
		oldManager.setRole(newManager.getRole());
		if (mdao.save(oldManager) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addManagers(Manager m) {
		Serializable pk = mdao.save(m);
		if (pk != null) {
			return true;
		} else {
			return false;
		}
	}
}
