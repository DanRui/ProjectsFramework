package com.jst.demo.test.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.demo.test.dao.ITestDAO;
import com.jst.demo.test.dao.impl.TestDAO;
import com.jst.demo.test.model.TestModel;
import com.jst.demo.test.service.TestService;

@Service("testServiceImpl")
public class TestServiceImpl extends BaseServiceImpl implements TestService {
	
	
	private String remark ;
	
	@Autowired
	private ITestDAO testDAO;
	
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		return testDAO;
	}


	@Override
	public List<TestModel> queryBySql() throws Exception{
		String sql = "select t.name,t.scription from T_TEST_AF  t where t.name like ?";
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(1);
		List<String> list = new ArrayList<String>();
		list.add("%%");
		String columns = "name,scription";
		//String columns = null;
		remark= "";
		//return testDAO.queryForSql(sql);
		return testDAO.getListBySql(sql,columns,list,page);
	}


	@Override
	public List<TestModel> queryByHql() throws Exception {
		String sql = "select t.name,t.scription from TestModel t";
		//String sql = "from TestModel t ";
		return testDAO.getList(sql);
	}


	@Override
	public boolean check(Serializable id) throws Exception {
		TestModel t = (TestModel)testDAO.get(id);
		t.setName(t.getName()+"this is update");
		return false;
	}

}
