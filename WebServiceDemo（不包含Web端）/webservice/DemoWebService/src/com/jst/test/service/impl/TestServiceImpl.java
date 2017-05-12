package com.jst.test.service.impl;



import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.model.BaseModel;
import com.jst.common.service.BaseServiceImpl;
import com.jst.test.dao.IClassRecordDAO;
import com.jst.test.dao.ITestDAO;
import com.jst.test.service.TestService;

@Service("testServiceImpl")
public class TestServiceImpl extends BaseServiceImpl implements TestService {
	
	@Autowired
	private ITestDAO testDAO;
	
	@Autowired
	private IClassRecordDAO classRecordDAO;
	
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		return testDAO;
	}


	@Override
	public BaseModel update(BaseModel baseModel,String name) throws Exception {
		testDAO.update(baseModel, name);
		return baseModel;
	}

/*
	@Override
	public void awww() throws Exception {
		
		ClassRecord cl = (ClassRecord)classRecordDAO.get(41);
		
		TestModel tm = (TestModel)testDAO.get(1);
		
		tm.setName("测试是否事物回滚");
		//cl.setCarnum("12333333333333333322222");
		
		testDAO.update(tm);
		classRecordDAO.update(cl);
		
	}*/


	/*@Override
	public Object update(Integer id, TestService object) throws Exception {
		
		return null;
	}*/

	

}
