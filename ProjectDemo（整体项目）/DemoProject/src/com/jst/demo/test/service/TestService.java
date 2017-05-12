package com.jst.demo.test.service;

import java.io.Serializable;
import java.util.List;

import com.jst.common.service.BaseService;
import com.jst.demo.test.model.TestModel;

public interface TestService extends BaseService{
	
	
	public List<TestModel> queryByHql() throws Exception;
	
	public List<TestModel> queryBySql() throws Exception;
	
	public boolean check(Serializable id) throws Exception;
	
}