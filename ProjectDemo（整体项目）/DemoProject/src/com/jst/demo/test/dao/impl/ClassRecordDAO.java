package com.jst.demo.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.demo.test.dao.IClassRecordDAO;
import com.jst.demo.test.model.ClassRecord;

@Repository("classRecordDAO")
public class ClassRecordDAO extends HibernateBaseDAO<ClassRecord> implements IClassRecordDAO{
	
	private static final String modelName = ClassRecord.class.getName();

	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}
