package com.jst.demo.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.common.service.BaseService;
import com.jst.common.service.BaseServiceImpl;
import com.jst.demo.test.dao.IClassRecordDAO;
import com.jst.demo.test.dao.impl.ClassRecordDAO;
import com.jst.demo.test.service.ClassRecordService;

@Service("classRecordServiceImpl")
public class ClassRecordServiceImpl extends BaseServiceImpl implements ClassRecordService{
	
	@Resource(name = "classRecordDAO")
	private IClassRecordDAO dao;

	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return dao;
	}

}
