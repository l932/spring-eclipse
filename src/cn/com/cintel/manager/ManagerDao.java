package cn.com.cintel.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.cintel.common.BaseDaoOracle;
import cn.com.cintel.common.pojo.Manager;
import cn.com.cintel.common.util.Page;

@Repository
public class ManagerDao extends BaseDaoOracle<Manager> {

	public List<Manager> findManagerByLoginName(String loginname) {
		StringBuffer hql = new StringBuffer("from Manager t where t.loginname = ?");
		List<Manager> ms = new ArrayList<Manager>();
		ms = this.find(hql.toString(), loginname);
		if (ms != null && ms.size() > 0) {
			return ms;
		} else {
			return null;
		}
	}

	public Page findPageManagers(Manager m, Integer index, Integer maxCount) {
		StringBuffer hql = new StringBuffer("from Manager t where 1=1");
		List<Object> values = new ArrayList<Object>();
		if (m.getLoginname() != null && !m.getLoginname().equals("")) {
			hql.append(" and t.loginname like ?");
			values.add("%" + m.getLoginname() + "%");
		}
		hql.append(" order by t.intime desc");
		Page page = pageQuery(hql.toString(), index, maxCount, values);
		if (page != null) {
			return page;
		} else {
			return new Page();
		}
	}

	public int delManagerById(String ids) {
		String hql = "delete Manager where managerid in (" + ids + ")";
		return this.remove(hql);
	}
}
