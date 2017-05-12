package com.jst.demo.test.dao;

import java.util.List;

import com.jst.common.hibernate.BaseDAO;
import com.jst.demo.test.model.TestModel;

public interface ITestDAO extends BaseDAO<TestModel> {

	
	public List getTableList(String sql)throws Exception;
	
	public List getList(String sql) throws Exception;
}
