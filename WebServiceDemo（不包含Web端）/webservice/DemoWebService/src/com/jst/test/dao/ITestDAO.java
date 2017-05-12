package com.jst.test.dao;

import com.jst.common.hibernate.BaseDAO;
import com.jst.test.model.TestModel;

public interface ITestDAO extends BaseDAO<TestModel> {

	
	public void callProcedure() throws Exception;
}
