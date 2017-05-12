package com.jst.demo.test.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
import com.jst.demo.test.dao.ITestDAO;
import com.jst.demo.test.model.TestModel;

@Repository("testDAO")
public class TestDAO extends HibernateBaseDAO<TestModel> implements ITestDAO{

	private static final String modelName = TestModel.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}

	
	public List getTableList(String sql)throws Exception {
		Session session = null;
		try {
			session = getSession();
			SQLQuery query = session.createSQLQuery(sql);
			/*if(StringUtil.isNotEmpty(modelName)){
				query.addEntity(modelName);
			}*/
			List list = query.list();
			return list;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public List getList(String sql) throws Exception{
		return this.getListByHql(sql);
	}
}
