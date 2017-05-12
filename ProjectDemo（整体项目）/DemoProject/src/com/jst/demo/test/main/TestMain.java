package com.jst.demo.test.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jst.common.model.BaseModel;
import com.jst.common.service.CacheService;
import com.jst.common.system.LogConstants;
import com.jst.common.utils.RedisUtil;
import com.jst.demo.test.model.TestModel;
import com.jst.demo.test.service.TestService;
import com.jst.platformClient.entity.OpeLog;
import com.jst.util.JsonUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class TestMain {
	
	
	public static ClassPathXmlApplicationContext ctx  = null;
	
	public static void main(String[] args) throws Exception {
		ctx = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
		
		TestService testService = (TestService) ctx.getBean("testServiceImpl");
		//testService.delete(1);
		//testService.check(22);
		//TestModel xx= (TestModel)testService.get(22);
		//testService.getAllList();
		LogConstants.put("CURRENT_START_TIME", System.currentTimeMillis());
		LogConstants.put(LogConstants.USER_CODE, "test_af");
		LogConstants.put(LogConstants.IP_STR, "localhost");
		TestModel xx = new TestModel();
		xx.setId(2);
		xx.setScription("asdas23333333333333333");
		System.out.println("start - update");
		xx.setName("aspect updatzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzze");
		testService.update(xx);
		
		
		//testService.delete(xx.getId());
	/*	CacheService testService = (CacheService) ctx.getBean("cacheService");
		testService.getMenu();*/
		
		/*Jedis jedis = new Jedis("192.168.0.188",7739);
		List<TestModel> list = new ArrayList<TestModel>();
		TestModel testModel = new TestModel();
		testModel.setName("aaa");
		testModel.setScription("bbb");
		list.add(testModel);
		TestModel testModel2 = new TestModel();
		testModel2.setName("aaaa-1");
		testModel2.setScription("bbb-2");
		list.add(testModel2);
		JSON json = JsonUtil.parse(list);
		jedis.set("test_model_list", json.toString());*/
		/*List<TestModel> o = (List<TestModel>)JSONArray.toCollection(JsonUtil.parseJSONArray(json),TestModel.class);*/
		//TestModel [] x = (TestModel[])o;
		/*System.out.println();*/
		//jedis.set("test_model_list", json.toString());
		/*System.out.println("--------------");
		
		TestModel xx= (TestModel)testService.get(1);
		System.out.println("start - update");
		xx.setName("aspect update");
		BaseModel bb = testService.update(xx);
		System.out.println("asdasd");*/
/*		testService.getMenu();*/
	}
}
