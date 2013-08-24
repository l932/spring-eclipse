package cn.com.cintel.common;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class MyHibernateTemplateOracle extends HibernateTemplate {

	public List find(final String queryString, final List<Object> values) throws DataAccessException {
		return executeWithNativeSession(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.size(); i++) {
						queryObject.setParameter(i, values.get(i));
					}
				}
				return queryObject.list();
			}
		});
	}

	// public List find(final String queryString, final ArrayList values) throws
	// DataAccessException {
	// return executeWithNativeSession(new HibernateCallback<List>() {
	// public List doInHibernate(Session session) throws HibernateException {
	// Query queryObject = session.createQuery(queryString);
	// prepareQuery(queryObject);
	// if (values != null) {
	// for (int i = 0; i < values.size(); i++) {
	// queryObject.setParameter(i, values.get(i));
	// }
	// }
	// return queryObject.list();
	// }
	// });
	// }

	public ScrollableResults findScrollableResults(final String queryString, final ArrayList values) throws DataAccessException {
		return executeWithNativeSession(new HibernateCallback<ScrollableResults>() {
			@Override
			public ScrollableResults doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				prepareQuery(queryObject);
				if (values != null) {
					for (int i = 0; i < values.size(); i++) {
						queryObject.setParameter(i, values.get(i));
					}
				}
				return queryObject.scroll();
			}
		});
	}
}
