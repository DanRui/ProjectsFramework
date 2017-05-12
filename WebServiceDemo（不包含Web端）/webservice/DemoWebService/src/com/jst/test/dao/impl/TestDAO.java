package com.jst.test.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.test.dao.ITestDAO;
import com.jst.test.model.TestModel;

import oracle.jdbc.OracleTypes;

@Repository("testDAO")
public class TestDAO extends HibernateBaseDAO<TestModel> implements ITestDAO{

	private static final String modelName = TestModel.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}
	
	
	public void callProcedure() throws Exception{
		this.getSession().doWork(new Work() {
			
			@Override
			public void execute(Connection conn) throws SQLException {
				String sql = "";
				CallableStatement cs = conn.prepareCall(sql);
				cs.registerOutParameter(1, OracleTypes.CURSOR);
			}
		});
	}
	
	
	@Override
	public Serializable save(Object model) throws Exception {
		//return super.save(model);
		throw new Exception("测试咯");
	}
}
