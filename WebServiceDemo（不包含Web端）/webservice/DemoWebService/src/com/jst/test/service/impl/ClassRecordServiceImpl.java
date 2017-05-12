package com.jst.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.test.dao.IClassRecordDAO;
import com.jst.test.service.ClassRecordService;

/**
 * 
 * @author Administrator
 *
 */
@Service("classRecordServiceImpl")
public class ClassRecordServiceImpl extends BaseServiceImpl implements ClassRecordService{
	
	/**
	 * 
	 */
	@Resource(name = "classRecordDAO")
	private IClassRecordDAO dao;

	/**
	 * 
	 */
	@Override
	public BaseDAO getHibernateBaseDAO() {
		return dao;
	}

	@Override
	public String asd() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
