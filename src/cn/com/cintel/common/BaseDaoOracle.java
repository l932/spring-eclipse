package cn.com.cintel.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

import cn.com.cintel.common.util.Page;

/**
 * DAO基类，其它DAO可以直接继承这个DAO，不但可以复用共用的方法，还可以获得泛型的好处。
 */
public class BaseDaoOracle<T> {
	private Class<T> entityClass;
	@Autowired
	private MyHibernateTemplateOracle myHibernateTemplateOracle;

	/**
	 * 通过反射获取子类确定的泛型类
	 */
	public BaseDaoOracle() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	/**
	 * 根据ID加载PO实例
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T load(Serializable id) {
		return getMyHibernateTemplateOracle().load(entityClass, id);
	}

	/**
	 * 根据ID获取PO实例
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T get(Serializable id) {
		return getMyHibernateTemplateOracle().get(entityClass, id);
	}

	/**
	 * 获取PO的所有对象
	 * 
	 * @return
	 */
	public List<T> loadAll() {
		return getMyHibernateTemplateOracle().loadAll(entityClass);
	}

	/**
	 * 保存PO
	 * 
	 * @param entity
	 */
	public Serializable save(T entity) {
		return getMyHibernateTemplateOracle().save(entity);
	}

	/**
	 * 批量保存PO
	 * 
	 * @param entities
	 */
	public void saveList(Collection entities) {
		this.getMyHibernateTemplateOracle().saveOrUpdateAll(entities);
	}

	/**
	 * 删除PO
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		getMyHibernateTemplateOracle().delete(entity);
	}

	public int remove(String hql) {
		return getMyHibernateTemplateOracle().bulkUpdate(hql);
	}

	/**
	 * 更改PO
	 * 
	 * @param entity
	 */
	public void update(T entity) {
		getMyHibernateTemplateOracle().update(entity);
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param sql
	 * @return 查询结果
	 */
	public List<T> find(String hql) {
		return this.getMyHibernateTemplateOracle().find(hql);
	}

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param sql
	 * @param params
	 * @return 查询结果
	 */
	public List<T> find(String hql, Object... params) {
		return this.getMyHibernateTemplateOracle().find(hql, params);
	}

	/**
	 * 执行带参的HQL查询，参数用ArrayList存放
	 * 
	 * @param hql
	 * @param params
	 * @return 查询结果
	 */
	public List<T> findByList(String hql, List<Object> params) {
		return this.getMyHibernateTemplateOracle().find(hql, params);
	}

	public ScrollableResults findScrollableRs(String hql, ArrayList params) {
		return this.getMyHibernateTemplateOracle().findScrollableResults(hql, params);
	}

	/**
	 * 分页查询函数，使用hql.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 */

	public Page pageQuery(String hql, Integer pageNo, Integer pageSize, List<Object> values) {
		Assert.hasText(hql);
		// 查询记录总数
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getMyHibernateTemplateOracle().find(countQueryString, values);
		Long totalCount = (Long) countlist.get(0);

		if (totalCount < 1) {
			return null;
		} else {
			// 实际查询返回分页对象

			int startIndex = Page.getStartOfPage(pageNo, pageSize);
			Query query = createQuery(hql, values);
			if (pageSize != null && pageSize != 0) {
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
			}
			List list = query.list();

			return new Page(startIndex, totalCount, pageSize, list);
		}
	}

	public Page pagedQueryScrResults(String hql, Integer pageNo, Integer pageSize, ArrayList values) {
		Assert.hasText(hql);
		// Count查询
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getMyHibernateTemplateOracle().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1) {
			return null;
		} else {
			// 实际查询返回分页对象
			int startIndex = Page.getStartOfPage(pageNo, pageSize);
			Query query = createQuery(hql, values);
			if (pageSize != null && pageSize != 0) {
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
			}
			ScrollableResults srs = query.scroll();

			return new Page(startIndex, totalCount, pageSize, srs);
		}
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new ArrayList())
	 * </pre>
	 * 
	 * @param values
	 *            可变参数，为ArrayList.
	 */
	public Query createQuery(String hql, ArrayList values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if (values != null && values.size() > 0) {
			for (int i = 0; i < values.size(); i++) {
				query.setParameter(i, values.get(i));
			}
		}
		return query;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 对延迟加载的实体PO执行初始化
	 * 
	 * @param entity
	 */
	public void initialize(Object entity) {
		this.getMyHibernateTemplateOracle().initialize(entity);
	}

	public MyHibernateTemplateOracle getMyHibernateTemplateOracle() {
		return myHibernateTemplateOracle;
	}

	@Resource
	public void setMyHibernateTemplateOracle(MyHibernateTemplateOracle myHibernateTemplateOracle) {
		this.myHibernateTemplateOracle = myHibernateTemplateOracle;
	}

	public Session getSession() {
		return SessionFactoryUtils.getSession(myHibernateTemplateOracle.getSessionFactory(), true);
	}

}