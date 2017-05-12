package com.jst.demo.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jst.common.model.BaseModel;

@Entity
@Table(name = "T_TEST_AF")
public class TestModel extends BaseModel implements Serializable{
	
	public TestModel(){
		
	}
	
	public TestModel(Integer id, String name, String scription) {
		this.id = id;
		this.name = name;
		this.scription = scription;
	}
	
	public TestModel(String name, String scription){
		this.name = name;
		this.scription = scription;
	}

	private Integer id;
	
	private String name;
	
	private String scription;

	@Id
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}
	
	

	
	
}
